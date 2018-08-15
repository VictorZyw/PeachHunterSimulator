import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PeachPit extends Location {
	private List<Player> playersHavevisited;
    public PeachPit(Position p, String description, List<Player> people, List<Peach> peaches) {
        super(p,description,people,peaches);
        this.property="PeachPit";
        this.playersHavevisited = new ArrayList<Player>();
    }

    public void fallIntoPit(Player p , Home home){
        //Check if Player p has fallen into this pit
        for (Player current_p: playersHavevisited) {
            if(p.equals(current_p)){
                p.setHealth(p.getHealth()/2);
                p.setLocation(home);
                System.out.println(p.getName() + " fell into the pit "+ position+" again.And He's been teleported to Home.");
                return;  //if has fallen into this pit, return void.
            }
        }
        //Player p hasn't been fallen into this pit
        p.setHealth(p.getHealth()/2);
        playersHavevisited.add(p);
        System.out.println(p.getName() + " just fell into the pit "+ position);
    }
}
