import java.util.ArrayList;
import java.util.HashMap;
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
    @Override

    /**get all peaches at location, with consideration of the player's capacity. only for the PeachPit Location.
     * @param  no param
     * @return void
     */
    public void pickPeachesAtLocation() {
        int pick_counter=0;
        while(this.peaches.size()<maxcarry) {
            if(this.getLocation().peachesAtLocation.size()<=0){    //if the PeachesAtLocation less than 0;
                System.out.println("  Action:"+this.name+" picked "+String.valueOf(pick_counter)+" peaches at "+this.getLocation()+"." );
                return;
            }
            this.peaches.add(this.getLocation().getPeach());
            pick_counter++;
        }
        System.out.println("##Action:"+this.name+" picked "+String.valueOf(pick_counter)+" peaches at "+this.getLocation()+"." );

    }

    /**
     *
     */
    private void pickPeachesAtTree(){
        int pick_counter=0;
        while(this.peaches.size()<maxcarry) {
            if(((PeachGrove)(this.getLocation())).getterOfPeachesAtTree().size()<=0){    //if the PeachesAtTree less than 0;
                for(GroveVisit visit:this.grove_visits){    // Traverse all grovevisit records of this hunter
                    if(this.getLocation().equals((Location)(visit.grove))){                 //if the hunter's position ==this grove' position
                        visit.runout=true;                //set the grove's runout value to true
                        System.out.println("  Action:"+this.name+" picked "+String.valueOf(pick_counter)+" peaches at "+this.getLocation()+"." );
                        System.out.println("  Action:"+this.name+" knew "+this.getLocation().description+" ran out of peaches on the tree.");
                        return;
                    }
                }
            }
            this.peaches.add(((PeachGrove)this.getLocation()).getPeachesAtTree());
            pick_counter++;
        }
        System.out.println("##Action:"+this.name+" picked "+String.valueOf(pick_counter)+" peaches at "+this.getLocation()+"." );

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
            if (all_locations.size()<=0){
                System.out.println("&&"+this.name+" found all groves run out, and no elsewhere to go.No choice but stay.");
                return world.getHome();  // if all traversed and all run out.The hunter has to stay.
            }
            for (Location loc:all_locations){
                if(this.getLocation().distanceOf(loc)<min){   //try to locate the nearest uncharted cell
                    min=this.getLocation().distanceOf(loc);
                    target=loc;        //find a nearest cell to go
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
            // new Location passive passive status check
            if(this.getLocation().property.equals("PeachGrove")) {   //if it is a Grove
                grove_visits.add(new GroveVisit((PeachGrove)(this.getLocation())));  //add this grove to the record of this hunter
                ((PeachGrove)(this.getLocation())).playerVisitGrove(this);// deal with the hp deduction
            } else if (this.getLocation().property.equals("PeachPit")){    //if it is a Pit
                ((PeachPit)this.getLocation()).fallIntoPit(this,(Home)(this.getWorld().home));  //fall into a pit. check the hp deduction
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
                System.out.println("##Action:"+this.name+" dropped "+String.valueOf(sizeofpeaches1 - this.maxcarry)+" peaches at "+this.getLocation()+" due to low HP." );
            }
        } else{
            this.maxcarry=100;
            if (this.getHealth()<70){
                eatPeach();
            }
        }
        // Location active action check
        System.out.println(" "+this.getName()+" has "+this.peaches.size()+" peaches." );//if at PeachGrove
        if(this.getLocation().property.equals("Home")){                 //if at Home
            int sizeofpeaches2=this.peaches.size();
            if (sizeofpeaches2==0){                                       //if no peaches ,then go to find grove
                moveOneStep(goGrove());
                return;
            }else {
                dropPeaches(sizeofpeaches2); //else drop all peaches
                if (((Home)(this.getLocation())).peaches_track.containsKey(this.getName())==false){
                    ((Home)(this.getLocation())).peaches_track.put(this.getName(),sizeofpeaches2);    //set a new record
                } else { int pre_record=((Home)(this.getLocation())).peaches_track.get(this.getName());  //get the previous stored peaches record of this hunter
                    ((Home)(this.getLocation())).peaches_track.put(this.getName(),pre_record+sizeofpeaches2);  //add new peaches number to this record.
                }
                int sum=((Home)(this.getLocation())).peaches_track.get("SUM");
                ((Home)(this.getLocation())).peaches_track.put("SUM",sum+sizeofpeaches2);
                return;
            }
        } else if(this.getLocation().property.equals("PeachGrove")) {
            if (this.peaches.size() >= 50 ||this.peaches.size() >= maxcarry) { //if carrying enough peaches or cannot carry any more
                moveOneStep(goHome());
                return;
            } else if(((PeachGrove)(this.getLocation())).getterOfPeachesAtTree().size()<=0){//if the Grove runs out,and hunter can carry more
                moveOneStep(goGrove());
                return;
            }else{
                pickPeachesAtTree();                                  //if not enough peaches carried with the hunter //if have peaches,pick peaches}
                return;
            }
        } else if (this.getLocation().property.equals("PeachPit")){
            pickPeachesAtLocation();
            if (this.peaches.size() >= 50 ||this.peaches.size() >= maxcarry) {//if carrying enough peaches or cannot carry any more
                moveOneStep(goHome());
                return;
            } else {
                moveOneStep(goGrove());
                return;
            }
        } else {             //if elsewhere
            if (this.peaches.size() >= 50 ||this.peaches.size() >= maxcarry) {
                moveOneStep(goHome());
                return;
            } else {
                moveOneStep(goGrove());
                return;
            }
        }

    }

}
