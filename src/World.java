
import java.util.List;

import java.util.ArrayList;

import java.util.Arrays;



public class World{

  protected Location[][] locations;

  protected Location     home;

  protected List<Player> players; // all players in the world

  protected List<String> realTimeInformation = new ArrayList<String>();  // record the real time events





  public World(){

    locations = new Location[3][3];

    for(int r=0; r<3; r+=1){

      for(int c=0; c<3; c+=1){

        locations[r][c] = new EmptyLocation(new Position(r,c), "Empty Location.");

      }

    }

    home = locations[0][0];

    players = new ArrayList<Player>();

  }



  public List<Player> getPlayers(){ return players; }

  public Location[][] getWorld(){ return locations; }

  public Location getHome(){ return home; }



  public List<Location> getLocations(){

    List<Location> list = new ArrayList<Location>(locations.length*locations[0].length);

    for (Location[] array : locations) {

      list.addAll(Arrays.asList(array));

    }

    return list;

  }



  /* keep a list of all players in the world. Each time a helper is created be

   * sure to add them to this list

   */

  public World addPlayer(Player p){

    players.add(p);
    return this;

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



  public void updateNews(String newInfo) {

    if(this.realTimeInformation.size()>4) {

      this.realTimeInformation.remove(0);

    }

    this.realTimeInformation.add(newInfo);

  }



  public String getRealTimeInformation() {

    String text = "<html>Real-time Information:\n";

    for(int i = 0;i< this.realTimeInformation.size(); i++) {

      text += this.realTimeInformation.get(i) + "<br>";

    }

    text += "</html>";

    return text;

  }



  public String getPlayersInformation() {

    int badPeaches = 0;

    String text = "<html>Player Information:<br><br>";

    for(int i = 0;i < this.getPlayers().size(); i++) {

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

    text += "</html>";

    return text;

  }

}