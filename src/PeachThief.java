import java.util.List;
import java.util.Random;

public class PeachThief extends Player {
	public PeachThief(World w, String name, Location location, List<Peach> peaches, int health, RGB rgb) {
		super(w, name, location, peaches, health, rgb);
		this.job = "PeachThief";
	}


	// each turn make a decision first then check the result of this decision
	public void play() {
		String decision = makeDecision();
		checkResult(decision);
	}
	
	private String makeDecision() {

		// when health is less than 0, the player will die
		if(this.getHealth()<=0){
			this.die();
		}
		// eat a good peach if the thief has at least one and his health is less than 50
		if(this.getHealth()<=50 && this.getPeaches().size() > 0) {
			for(int i = 0; i < this.getPeaches().size(); i++) {
				if(!this.getPeaches().get(i).isBad()) {
					this.eatPeach(this.getPeaches().get(i));
					return "stay";
				}
			}
		}

		// if there are any other players in this world and they are peachhunter or pitfinder
		// the thief will try to get closer to them
		if(this.getWorld().getPlayers().size()>1) {
			for (int i = 0;i < this.getWorld().getPlayers().size(); i++) {
				if(this.getWorld().getPlayers().get(i).job == "PeachHunter"
						/* || this.getWorld().getPlayers().get(i).job == Pitfinder*/) {
					Player target = this.getWorld().getPlayers().get(i);
					if (target.getLocation().getPosition().getX() > this.getLocation().getPosition().getX()) {
						this.move(Directions.RIGHT);
						this.getWorld().updateNews(this.getName()+" entered ["+
								(this.getLocation().getPosition().getX()+1)+","+
								this.getLocation().getPosition().getY()+"]");
						return "move";
					}else if (target.getLocation().getPosition().getX() < this.getLocation().getPosition().getX()) {
						this.move(Directions.LEFT);
						this.getWorld().updateNews(this.getName()+" entered ["+
								(this.getLocation().getPosition().getX()-1)+","+
								this.getLocation().getPosition().getY()+"]");
						return "move";
					}else {
						if(target.getLocation().getPosition().getY() < this.getLocation().getPosition().getY()) {
							this.move(Directions.UP);
							this.getWorld().updateNews(this.getName()+" entered ["+
									this.getLocation().getPosition().getX()+","+
									(this.getLocation().getPosition().getY()-1)+"]");
							return "move";
						}else if(target.getLocation().getPosition().getY() > this.getLocation().getPosition().getY()) {
							this.move(Directions.DOWN);
							this.getWorld().updateNews(this.getName()+" entered ["+
									this.getLocation().getPosition().getX()+","+
									(this.getLocation().getPosition().getY()+1)+"]");
							return "move";
						}
					}
				}
			}
		}
		// if there are none other players, the thief has no targets
		// he will hang out widdershins
		if (this.getWorld().getPlayers().size()==1) {
			if (this.getLocation().getPosition().getY()>0 && (this.getLocation().getPosition().getX() != 0 || 
					this.getLocation().getPosition().getX() != this.getWorld().getWorld()[0].length-1)){
				this.move(Directions.UP);
				this.getWorld().updateNews(this.getName()+" entered ["+
						this.getLocation().getPosition().getX()+","+
						(this.getLocation().getPosition().getY()-1)+"]");
				return "move";
			}
			if (this.getLocation().getPosition().getY() == 0 && this.getLocation().getPosition().getX() > 0) {
				this.move(Directions.LEFT);
				this.getWorld().updateNews(this.getName()+" entered ["+
						(this.getLocation().getPosition().getX()-1)+","+
						this.getLocation().getPosition().getY()+"]");
				return "move";
			}
			if (this.getLocation().getPosition().getX() == 0 && this.getLocation().getPosition().getY() < this.getWorld().getWorld().length-1) {
				this.move(Directions.DOWN);
				this.getWorld().updateNews(this.getName()+" entered ["+
						this.getLocation().getPosition().getX()+","+
						(this.getLocation().getPosition().getY()+1)+"]");
				return "move";
			}
			if (this.getLocation().getPosition().getY() == this.getWorld().getWorld().length-1 && this.getLocation().getPosition().getX() < this.getWorld().getWorld()[0].length-1) {
				this.move(Directions.RIGHT);
				this.getWorld().updateNews(this.getName()+" entered ["+
						(this.getLocation().getPosition().getX()+1)+","+
						this.getLocation().getPosition().getY()+"]");
				return "move";
			}
			if (this.getLocation().getPosition().getX() == this.getWorld().getWorld()[0].length-1 && this.getLocation().getPosition().getY()>0) {
				this.move(Directions.UP);
				this.getWorld().updateNews(this.getName()+" entered ["+
						(this.getLocation().getPosition().getX())+","+
						(this.getLocation().getPosition().getY()-1)+"]");
				return "move";
			}
		}
		return "";
	}



	// check other players in this location
	private void checkResult(String decision) {

		// find the peatchhunters and pitfinders, then interact with them
		for(int i = 0;i <this.getLocation().getPlayers().size();i++) {
			if(this.getWorld().getPlayers().get(i).job == "PeachHunter"
					/* || this.getWorld().getPlayers().get(i).job == "Pitfinder"*/) {
				this.interact(this.getLocation().getPlayers().get(i));
			}
		}
		
		// take all the peaches on the ground
		while(this.getLocation().peachesAtLocation.size() > 0) {
			this.peaches.add(this.getLocation().peachesAtLocation.remove(0));
		}
		
		// if the thief has just moved into this place, check the interaction with this location
		if (decision.equals("move")) {
			if (this.getLocation().property == "PeachGrove") {
				((PeachGrove)this.getLocation()).playerVisitGrove(this);
			}
			if (this.getLocation().property == "PeachPit") {
				((PeachPit)this.getLocation()).fallIntoPit(this,(Home)(this.getWorld().home));
			}
			/*
			if (this.getLocation().property == "BearsDen") {
				((PeachGrove)this.getLocation()).fallIntoPit(this);
			}
			 */
		}
	}
	

	// thief steals peaches from other players
	public void interact(Player other) {
		Random roll = new Random();
		boolean success = true;

		// eat the first peach stealed
		if (roll.nextDouble() <= 0.75) {
			this.eatPeach(other.getPeach());
		}else {
			success = false;
		}
		// try to steal more peaches
		while(success && other.getPeaches().size() > 0) {
			if (roll.nextDouble() <= 0.75) {
				this.peaches.add(other.getPeach());
			}else {
				success = false;
			}
		}
	}
	
	
	
	
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
