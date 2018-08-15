import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class Home extends Location {

    public  HashMap<String,Integer> peaches_track;

    public Home(World w,Position p, String description, List<Player> people, List<Peach> peaches){
        super(p,description,people,peaches);
        this.property="Home";  //the attribute for distinguishing different kinds of location.
        System.out.println("There are "+String.valueOf(people.size())+" Players here.");
        this.peaches_track=new HashMap<>();  // A hashmap we used to store individual contribution and sum of the peaches carried to home
        peaches_track.put("SUM",0);  //initialize the sum to 0
        w.home=this;
    }

    @Override
    public void callForHelp(Player p, Location location){// forfeited method
    }

    @Override
    public String toString(){
        String out="PeachesRecord-";
        for(Entry<String, Integer> entry : peaches_track.entrySet()) {
            out+=(entry.getKey() + ": " + entry.getValue()+"; ");
        }
        return description +":"+ position.toString()+"("+out+")";
    }


}
