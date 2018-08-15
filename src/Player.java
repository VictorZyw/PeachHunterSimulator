import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/** A Player in the game 
  * 
  * Each member of your team should implement their own
  * unique Player subtype. Your group should also have a human player.
  */

public class Player{
 protected World        world;    // world that player lives in
 protected String       name;     // name of player
 protected Location     location; // where in the world the player is
 protected List<Peach>  peaches;  // peaches 
 protected int          health;   // health of player
 protected RGB          colour;   // colour of player (if graphics is used)
 protected boolean need_help;   //(New attribute) whether this player needs to be helped.default: false
 protected boolean helper_sent;//(New attribute) whether a helper needs to be created to help this player.default: false
 public static String[]     names={"Chen","Zhang","Zhou","Jason","Robert","Smid","Mengchi"};  //(New attribute) store the random name
 protected String job;
 
 /** Creates a player in the game
  * 
  * @param world is the world that the player lives in
  * @param name is the name of the player
  * @param location is where in the world the player is
  * @param peaches is a list of peaches the player starts with
  * @param health is the health of the player (which may or may not be relevant in your game)
  * @param RGB is the colour of the player 
 */
 public Player(World w, String name, Location location, List<Peach> peaches, int health, RGB rgb){
  this.world = w;
  this.name = name;
  this.location = location;
  location.getPlayers().add(this);
  this.peaches = peaches;
  this.health = health;
  this.colour = rgb;
  this.need_help=false;  //store if he needs to be helped.
  this.helper_sent=false;  //store if a helper has been send.
  this.job="Player";
 }
 
 /** Getter for a player's world */
 public World        getWorld(){ return world; }

 /** Getter for a player's name */
 public String       getName(){ return name; }
 
 /** Getter for a player's location */
 public Location     getLocation(){ return location; }
 
 /** Getter for a player's peach */
 public Peach  getPeach(){ return peaches.size()<=0 ? null : peaches.remove(0); }


 /** Getter for a player's all peaches */

 public List<Peach> getPeaches(){return this.peaches;}
 
 /** Getter for a player's health */
 public int          getHealth(){ return health; }
 
 
 /** This is the logic of the player. 
   * It defines what they should do when given a chance to do something
   */
 public void play(){
   if( health < 10 ){
     getHelp();
     return;
   }
 }
 /**NEW METHOD
  * define the death of a player, del this player both from world and the current location.
  * @param no param
  * @return void
  */
 public void die(){
  int sizeofpeaches0 = this.peaches.size();
  System.out.println(this.name + " is DEAD at " + this.getLocation().getDescription() + "!!RIP!!");
  dropPeaches(sizeofpeaches0);
  this.world.dead_players.add(this);
  this.getLocation().exit(this);
  return;
 }

 /**NEW METHOD
  * Drop peaches on the location
  * @param int size_dropped_peaches
  */
 public void dropPeaches(int size_dropped_peaches){
  for (int i = 0; i < size_dropped_peaches; i++) {
   this.getLocation().addPeach(this.getPeach());
  }
  System.out.println(this.name+" dropped "+String.valueOf(size_dropped_peaches)+" peaches at "+this.getLocation().getDescription()+".");
 }


 /** Moves a player from one location to a new location
   * 
   * @param newLocation is the new location that the player will be moved to
   * @return true if the move was successful and false otherwise (e.g. when trying to move from one 
   *         location to another that are not connected)
   */
 public boolean move(int direction){
   // move from current location to new location (if possible)
   world.move(this, direction);
   return false;
 }
 /** Given a Location target.Find out where to move for this turn
  *
  * @param target is the new location that the player will be moved to
  * @return void
  *
  */
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
   }
 }


 /** sets a player's current location
  * 
  * @param location is the Location to set for the player
  */
 public void setLocation(Location location){
  this.location = location;
 }

 /** Setter for a player's health
   * 
   * @param h is the new health of the player
   */
 public void setHealth(int h){
  this.health = h;
 }

 /** eat the first peach carried with him  //new mothod
  *
  * @param null
  */
 public void eatPeach(){
  Peach eaten_peach=this.getPeach();
  if (eaten_peach==null){
   return;
  } else{
   this.setHealth(eaten_peach.isBad()?(this.getHealth()-eaten_peach.ripeness):(this.getHealth()+eaten_peach.ripeness));
  }
 }
 
 
 /** Allows for interaction with this player and another player
   * (presumably called from within the play method)
   * 
   * @param p is a player that is interacting with this player
   */
 public void interact(Player p){
   // allows for some interaction with a player
 }

 /**get all peaches at location, without consideration of the player's capacity
  * @param  no param
  * @return void
  */
 public void pickPeachesAtLocation(){
 //Get all peaches from the location
  while(this.getLocation().peachesAtLocation.size()>0){
     this.peaches.add(this.getLocation().getPeach());
  }
 }

 /** ask for help when they need it */
 public void getHelp(){ 
/*  world.getHome().callForHelp(this, location);
  List<Peach> peaches = new ArrayList<>(Arrays.asList(new Peach(50),new Peach(50),new Peach(50),new Peach(50)));
  // Create a new Peach list with 4 peaches of 50 ripeness.
  Random ran=new Random();
  Helper temp_helper=new Helper(world,Player.names[ran.nextInt(Player.names.length)],peaches,this);//Create a new helper at home
  this.world.addPlayer(temp_helper);
  this.helped=true;
  */
 }
 
 @Override
 public String toString(){
   return name;
 }
 
 /** Two players are the same if they have the same name, location and health. */
 @Override
 public boolean equals(Object o){
   if( o instanceof Player){
     return this.name.equals( ((Player)o).name )  
            && this.location.equals( ((Player)o).location ) 
            && this.health == ((Player)o).health;
                                  
   }else{
     return false;
   }
 }
}