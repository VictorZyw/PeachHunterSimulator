import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PeachPit extends Location {
	private List<Player> playersHavevisited;
    public PeachPit(Position p, String description, List<Player> people, List<Peach> peaches) {
        super(p,description,people,peaches);
        this.property="PeachPit";  //the attribute for distinguishing different kinds of location.
        this.playersHavevisited = new ArrayList<Player>();
    }

    public int fallIntoPit(Player p , Home home){
        //Check if Player p has fallen into this pit
        for (Player current_p: playersHavevisited) {
            if(p.equals(current_p)){
                p.setHealth(p.getHealth()/2);  //if has fallen once before, set HP to 1/2
                System.out.println(p.getName() + " fell into the pit "+ position+" again.And He would be teleported to Home.");
                this.exit(p);                               //Update players in PeachPit location
                home.enter(p);
                return 2;                                   //if has fallen into this pit, return 2(which means he has fallen into the pit twice).
            }
        }
        //Player p hasn't been fallen into this pit
        p.setHealth(p.getHealth()/2);
        playersHavevisited.add(p);
        System.out.println(p.getName() + " just fell into the pit "+ position);
        return 1;                                           //if hasn't fallen into this pit, return 1(which means he has fallen into the pit once).
    }
}
