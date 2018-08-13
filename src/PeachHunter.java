import java.util.ArrayList;
import java.util.List;

public class PeachHunter extends Player {
    protected static int hunter_counter=0;//counter for the Player
    private List<GroveVisit> grove_visits;//(New attribute) records of the groves visited
    private List<Location> location_visits;
    private Location target;
    protected int maxcarry;  //(New attribute)max capacity a player can carry
    public PeachHunter(World w, String name, Location location, List<Peach> peaches){
        super(w,name+"-PeachHunter"+String.valueOf(hunter_counter),location,peaches,100,RGB.RED);
        this.grove_visits=new ArrayList<GroveVisit>();
        this.location_visits=new ArrayList<>();
        location_visits.add(location);
        this.target=null;
        this.maxcarry=100;
        this.job="PeachHunter";
        PeachHunter.hunter_counter+=1;
    }

    private void pickPeachesAtTree(){
        while(this.peaches.size()<maxcarry) {
            if(((PeachGrove)(this.getLocation())).getterOfPeachesAtTree().size()<=0){
                for(GroveVisit visit:this.grove_visits){
                    if(this.getLocation().equals((Location)(visit.grove))){
                        visit.runout=true;
                        System.out.println(this.getLocation().description+" runs out of peaches on the tree.");
                    }
                }
                return;
            }
            this.peaches.add(((PeachGrove)this.getLocation()).getPeachesAtTree());
        }

    }

    private Location goGrove(){
        int min=10000;
        boolean all_runout=true;
        Location target=this.getLocation();
        for (GroveVisit visit:this.grove_visits) {
            if (!visit.runout) {
                all_runout = false;
                if (this.getLocation().distanceOf(visit.grove) < min) {
                    min = this.getLocation().distanceOf(visit.grove);
                    target = visit.grove;
                }
            }
        }
        if (all_runout){        //no available grove found.
            List<Location> all_locations=world.getLocations();
            all_locations.removeAll(location_visits);
            for (Location loc:all_locations){
                if(this.getLocation().distanceOf(loc)<min){
                    min=this.getLocation().distanceOf(loc);
                    target=loc;        //find a nearest cell to search
                }
            }
        }
        return target;
    }
    private Location goHome(){
        return this.world.home;
    }


    @Override
    public void moveOneStep(Location target){
        if (this.getLocation().getPosition().equals(target)){  //if this is the target
            return;
        }else {
            if (target.getPosition().getX() < this.getLocation().getPosition().getX()) {
                move(Directions.UP);
            } else if (target.getPosition().getX() > this.getLocation().getPosition().getX()) {
                move(Directions.DOWN);
            } else if (target.getPosition().getY() > this.getLocation().getPosition().getY()) {
                move(Directions.RIGHT);
            } else {
                move(Directions.LEFT);
            }
            location_visits.add(this.getLocation());
            // new Location passive debuff check
            if(this.getLocation().property.equals("PeachGrove")) {   //if it is a Grove
                grove_visits.add(new GroveVisit((PeachGrove)(this.getLocation())));
                ((PeachGrove)(this.getLocation())).playerVisitGrove(this);//((PeachGrove)(this.getLocation()))if at PeachGrove
            } else if (this.getLocation().property.equals("PeachPit")){    //if it is a Pit
            }
        }
    }


    private class GroveVisit{

        protected PeachGrove grove;
        protected boolean runout;
        public GroveVisit(PeachGrove grove) {
            this.runout = false;
            this.grove=grove;
        }
    }



    @Override
    /** This is the logic of the player.
     * It defines what they should do when given a chance to do something
     */
    public void play(){
        // HP Physical status check
        if (this.getHealth()<= 0) {            // if a player' hp is below or equal to 0, he is dead and drop all peaches on the ground .
            die();
            return;
        }else if (this.getHealth() < 10) {   //when  hp<10
            if(!this.helped){                     //and no helper sent yet , get help ,create new helper.
                getHelp();                        //*REMEMBER to set the boolean value to false when interacted with helper. *
                return;
            }else {                               //else this player remains disabled
                return;
            }
        } else if (this.getHealth() < 40) {
            eatPeach();
        }
            // HP Capacity status check
        if (this.getHealth() < 50) {         //if a player' hp is below 50, set the max number he can carry to 25
            this.maxcarry=25;
            if (this.peaches.size() > this.maxcarry) {
                int sizeofpeaches1=this.peaches.size();
                dropPeaches(sizeofpeaches1 - this.maxcarry);  //drop peaches to the max available number.
            }
        } else{
            this.maxcarry=100;
            if (this.getHealth()<70){
                eatPeach();
            }
        }
        // Location active action check
        if(this.getLocation().property.equals("Home")){                 //if at Home
            int sizeofpeaches2=this.peaches.size();
            if (sizeofpeaches2==0){                                       //if no peaches ,then go to find grove
                moveOneStep(goGrove());
                return;
            }else {
                dropPeaches(sizeofpeaches2);                              //else drop all peaches
                ((Home)(this.getLocation())).peaches_track.put(this.getName(),sizeofpeaches2);
                return;
            }
        } else if(this.getLocation().property.equals("PeachGrove")) {    //if at PeachGrove
            if (this.peaches.size() >= 50) {
                moveOneStep(goHome());
                return;
            } else {
                pickPeachesAtTree();                                  //if not enough peaches carried with the hunter //if have peaches,pick peaches}
                return;
            }
        } else if (this.getLocation().property.equals("PeachPit")){
            moveOneStep(goGrove());
            return;
        } else {             //if elsewhere
            if (this.peaches.size() >= 50) {
                moveOneStep(goHome());
                return;
            } else {
                moveOneStep(goGrove());
                return;
            }
        }

    }

}
