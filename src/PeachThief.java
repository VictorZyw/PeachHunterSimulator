import java.util.List;
import java.util.Random;

public class PeachThief extends Player {
	protected static int thief_counter=0;//counter for the Player
	public PeachThief(World w, String name, Location location, List<Peach> peaches) {
		super(w, name+"-Thief"+thief_counter, location, peaches, 70, RGB.BLUE);
		this.job = "PeachThief";
		thief_counter++;
	}


	// each turn make a decision first then check the result of this decision
	public void play() {
		if (this.getHealth()<= 0) {            
			// if a player' hp is below or equal to 0, he is dead and drop all peaches on the ground .
			die();
			return;
		}
		String decision = makeDecision();
		checkResult(decision);
	}
	
	private String makeDecision() {
		// eat a good peach if the thief has at least one and his health is less than 50
		if(this.getHealth()<50 && this.getPeaches().size() > 0) {
			for(int i = 0; i < this.getPeaches().size(); i++) {
				if(!this.getPeaches().get(i).isBad()) {
					this.eatPeach(this.getPeaches().get(i));
					return "stay";
				}
			}
		}

		// if there are any other players in this world who are peachhunter or pitfinder
		// and who have any peaches, the thief will try to get closer to them
		for (int i = 0;i < this.getWorld().getPlayers().size(); i++) {
			if(this.getWorld().getPlayers().get(i).job == "PeachHunter"
					/* || this.getWorld().getPlayers().get(i).job == Pitfinder*/
					&& this.getWorld().getPlayers().get(i).getPeaches().size()>0) {
				Player target = this.getWorld().getPlayers().get(i);
				if (target.getLocation().getPosition().getX() > this.getLocation().getPosition().getX()) {
					this.move(Directions.DOWN);
					this.getWorld().updateNews(this.getName()+" entered ["+
							(this.getLocation().getPosition().getX())+","+
							this.getLocation().getPosition().getY()+"]");
					return "move";
				}else if (target.getLocation().getPosition().getX() < this.getLocation().getPosition().getX()) {
					this.move(Directions.UP);
					this.getWorld().updateNews(this.getName()+" entered ["+
							(this.getLocation().getPosition().getX())+","+
							this.getLocation().getPosition().getY()+"]");
					return "move";
				}else {
					if(target.getLocation().getPosition().getY() < this.getLocation().getPosition().getY()) {
						this.move(Directions.LEFT);
						this.getWorld().updateNews(this.getName()+" entered ["+
								this.getLocation().getPosition().getX()+","+
								(this.getLocation().getPosition().getY())+"]");
						return "move";
					}else if(target.getLocation().getPosition().getY() > this.getLocation().getPosition().getY()) {
						this.move(Directions.RIGHT);
						this.getWorld().updateNews(this.getName()+" entered ["+
								this.getLocation().getPosition().getX()+","+
								(this.getLocation().getPosition().getY())+"]");
						return "move";
					}
				}
			}
		}
		// if the thief has no targets, he will go to and hide in corner
		int x = this.getLocation().getPosition().getY();
		int y = this.getLocation().getPosition().getX();
		if ((x == 0 && y == 0) || (x == 0 && y == this.world.getWorld()[0].length) || 
			(x == this.world.getWorld().length && y ==0 ) || (x == this.world.getWorld().length && y ==this.world.getWorld()[0].length )) {
			return "stay";
		}
		if (x == 0 || x == this.world.getWorld().length) {
			this.move(Directions.UP);
			return "move";
		}
		if (y == 0 || y == this.world.getWorld()[0].length) {
			this.move(Directions.LEFT);
			return "move";
		}
		this.move(Directions.UP);
		return "move";
	}

	private void checkResult(String decision) {
		
		if (decision.equals("stay")) {
			this.world.updateNews(this.getName()+" stay in "+this.getLocation().getPosition().toString());
		}
		// check other players in this location
		// find the peatchhunters and pitfinders, then interact with them
		for(int i = 0;i <this.getLocation().getPlayers().size();i++) {
			if(this.getLocation().getPlayers().get(i).job == "PeachHunter"
					/* || this.getWorld().getPlayers().get(i).job == "Pitfinder"*/) {
				this.interact(this.getLocation().getPlayers().get(i));
			}
		}
		
		// take all the peaches on the ground
		while((!this.getLocation().equals(world.getHome()))&&this.getLocation().peachesAtLocation.size() > 0) {
			this.peaches.add(this.getLocation().peachesAtLocation.remove(0));
		}
		
		// if the thief has just moved into this place, check the interaction with this location
		if (decision.equals("move")) {
			if (this.getLocation().property == "PeachGrove") {
				((PeachGrove)this.getLocation()).playerVisitGrove(this);
			}
			if (this.getLocation().property == "PeachPit") {
				int fall_times=((PeachPit)this.getLocation()).fallIntoPit(this,(Home)(this.getWorld().home));
				if(fall_times==1){
					pickPeachesAtLocation();
				}
			}
		}
	}
	

	// thief steals peaches from other players
	public void interact(Player other) {
		Random roll = new Random();
		boolean success = true;

		// eat the first peach stealed
		if (roll.nextDouble() <= 0.75 && other.getPeaches().size() > 0) {
				String text = other.getName()+"was stolen";
				System.out.println(text);
				this.getWorld().updateNews(text);
				this.eatPeach(other.getPeach());
		}else {
			success = false;
		}
		// try to steal more peaches
		while(success && other.getPeaches().size() > 0) {
			Random roll_new = new Random();
			if (roll_new.nextDouble() <= 0.75) {
				this.peaches.add(other.getPeach());
			}else {
				success = false;
			}
		}
	}
	
	// eat a special peach
	private void eatPeach(Peach peach) {
		if(peach.isBad()){
			this.health -= peach.getRipeness();
			this.peaches.remove(peach);
			this.world.updateNews(this.getName()+" has eaten a peach and redecues "+ peach.getRipeness() +" health.");
		}else {
			this.health += peach.getRipeness();
			this.peaches.remove(peach);
			this.world.updateNews(this.getName() + " has eaten a peach and gains " + peach.getRipeness() + " health.");
		}
	}
}
