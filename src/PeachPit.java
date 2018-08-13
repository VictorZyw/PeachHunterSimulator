import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PeachPit extends Location {
    public PeachPit(Position p, String description, List<Player> people, List<Peach> peaches) {
        super(p,description,people,peaches);
        this.property="PeachPit";
    }

    public void fallIntoPit(Player p , Home home){
        /*Check if Player p has fallen into this pit
        for (Player current_p: peopleAtLocation) {
            if(p.equals(current_p)){
                p.setHealth(p.getHealth()/2);
                p.setLocation(home);
            }
        }
        //Player p hasn't been fallen into this pit
        p.setHealth(p.getHealth()/2);
        peopleAtLocation.add(p);
        System.out.println(p.getName() + "just falls into the pit "+ position);
        //Get peaches from the pit
        if(peachesAtLocation.size()>0){
            for (int i = 0; i <peachesAtLocation.size() ; i++) {
                p.peaches.add(peachesAtLocation.get(i));
                peachesAtLocation.remove(i);
            }*/
    }
}
