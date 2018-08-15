
import java.util.List;

import java.util.ArrayList;

import java.util.Arrays;



public class World {

  protected Location[][] locations;

  protected Location home;
  protected List<Player> players; // all players in the world
  protected List<Player> dead_players; // all players in the world
  protected List<String> realTimeInformation = new ArrayList<String>();  // record the real time events
  protected List<Helper> helpers;  //store default helper info.(but we didn't use that)


  public World() {

    locations = new Location[3][3];
    for (int r = 0; r < 3; r += 1) {
      for (int c = 0; c < 3; c += 1) {
        locations[r][c] = new EmptyLocation(new Position(r, c), "Empty Location.");
      }
    }
    home = locations[0][0];

    players = new ArrayList<Player>();
    helpers=new ArrayList<Helper>();
    this.dead_players=new ArrayList<Player>();

  }


  public List<Player> getPlayers() {
    return players;
  }

  public Location[][] getWorld() {
    return locations;
  }

  public Location getHome() {
    return home;
  }


  public List<Location> getLocations() {

    List<Location> list = new ArrayList<Location>(locations.length * locations[0].length);

    for (Location[] array : locations) {

      list.addAll(Arrays.asList(array));

    }

    return list;

  }


  /* keep a list of all players in the world. Each time a helper is created be

   * sure to add them to this list

   */

  public World addPlayer(Player p) {
    players.add(p);
    return this;
  }

  /*
  A constructor for corresponding helpers after all non-helper player were created,

   */
  public void helperConstructor(){
    List<Helper> helpers1 = new ArrayList<Helper>();
    for(Player p:this.getPlayers()) {
        helpers1.add(new Helper(this, "Mercy", new ArrayList<Peach>()));   //store a helper into a temp helper list
    }
    this.helpers=helpers1;
    this.getPlayers().addAll(helpers1);  //add all helpers into world.players.
  }

  public void helperGenerator(){
    int angels=0;
    for(Player p:this.getPlayers()) {
      System.out.println(p.getName()+p.job+","+String.valueOf(p.need_help)+","+String.valueOf(p.helper_sent));
      if ((!p.job.equals("Helper"))&&p.need_help&& (!p.helper_sent)) {   //if a player not helper AND calling for help AND helper not sent
        System.out.println(p.getName()+" is dying,and calling for help.");
        for(Player h:this.getPlayers()){
          if(h.job.equals("Helper")){
            if(h.alive!=1){  // if the helper not activated
              ((Helper)h).target=p;           //set the helper's target to the player who needs help
              h.alive=1;                       //activate the helper
              for (int i = 0; i < 5; i++) {
                h.peaches.add(new Peach(15));   //give this helper 5 peaches
              }
              p.helper_sent=true;            //notify that the this player's helper is on his way
              angels++;                     //the helper sent for this turn ++
              break;
            }
          }
        }
      }
    }
    if(angels>0){
      System.out.println("@ "+String.valueOf(angels)+" angels have arrived at this world to rescue you mortals!!");
    }
  }

  public void deadPlayersHandler(){
    for(Player d:dead_players){
      for(Player p:this.getPlayers()) {
        if(p.getName().equals(d.getName())){
          p.alive = -1;  //find the player to be not alive  from the world.player
        }
      }
    };
    this.dead_players=new ArrayList<Player>();//clear the dead player list
  }


  public boolean move(Player p, int direction){
    Location loc = p.getLocation(); // player's current location
    int x = loc.getPosition().getX();
    int y = loc.getPosition().getY();
    Location newLocation = null;

    //
    switch(direction){
      case Directions.UP:
        newLocation = locations[x-1][y];
        break;
      case Directions.DOWN:
        newLocation = locations[x+1][y];
        break;
      case Directions.LEFT:
        newLocation = locations[x][y-1];
        break;
      case Directions.RIGHT:
        newLocation = locations[x][y+1];
        break;
      default: break;
    }
    loc.exit(p);
    newLocation.enter(p);
    return true;
  }

  /*
   * gather the events' information from whole world
   * and change the list of real time information
   * @param String; events' news
   */
  public void updateNews(String newInfo) {

    if(this.realTimeInformation.size()>7) {
      this.realTimeInformation.remove(0);
    }
    this.realTimeInformation.add(newInfo);

  }

  /*
   * Transform the list of real time information into String in html
   * @param null
   */
  public String getRealTimeInformation() {
    String text = "<html>Real-time Information:<br>";
    for(int i = 0;i< this.realTimeInformation.size(); i++) {
      text += this.realTimeInformation.get(i) + "<br>";
    }
    text += "</html>";
    return text;
  }

  /*
   * Transform some information of players into String in html
   * @param null
   */
  public String getPlayersInformation() {
    int badPeaches = 0;
    String text = "<html>Player Information:<br><br>";
    for(int i = 0;i < this.getPlayers().size(); i++) {
      if (this.getPlayers().get(i).alive == 1) {
        text += this.getPlayers().get(i).toString() + "    " +
                this.getPlayers().get(i).getLocation().getPosition().toString() +
                "  health: "+this.getPlayers().get(i).getHealth()+"<br>";
        for(int j = 0; j <this.getPlayers().get(i).getPeaches().size(); j++) {
          if (this.getPlayers().get(i).getPeaches().get(j).isBad()) {
            badPeaches ++;
          }
        }
        text += "Good peaches: "+(this.getPlayers().get(i).getPeaches().size()-badPeaches)+
                "  Bad peaches: "+badPeaches+"<br><br>";
      }
    }
    text += "</html>";
    return text;
  }

}