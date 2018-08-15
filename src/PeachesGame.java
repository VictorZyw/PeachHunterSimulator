import java.util.ArrayList;

public class PeachesGame{
  public static void main(String[] args){
    World w = new World();
    w.getWorld()[0][0]=new Home(w,new Position(0,0),"My home",new ArrayList<>(),new ArrayList<>());
    System.out.println("Home is " + w.getWorld()[0][0]+".");
    w.getWorld()[1][1]=new PeachGrove(new Position(1,1),"PeachGrove1",new ArrayList<Player>(),new ArrayList<Peach>());
    w.getWorld()[1][2]=new PeachGrove(new Position(1,2),"PeachGrove2",new ArrayList<Player>(),new ArrayList<Peach>());
    w.getWorld()[2][0]=new PeachGrove(new Position(1,1),"PeachGrove3",new ArrayList<Player>(),new ArrayList<Peach>());
    PeachHunter p = new PeachHunter(w, "cat", w.home, new ArrayList<Peach>());
    PeachHunter q = new PeachHunter(w, "dog", w.home, new ArrayList<Peach>());
    PeachHunter r = new PeachHunter(w, "owl", w.home, new ArrayList<Peach>());

    w.addPlayer(p).addPlayer(q).addPlayer(r);
    //System.out.println(w.getPlayers()+"////////////////////////////////////////////");
    //System.out.println(w.home.getPlayers()+"////////////////////////////////////////////");
    System.out.println("Home : " + w.getHome());
    System.out.println("  Players at Home : " + w.getHome().getPlayers());
    System.out.println("Location of all players in world");
    for(Player pp: w.getPlayers()){
       System.out.println(pp.getLocation());
       System.out.println(pp.getLocation().getPlayers());
    }
    /*
    System.out.println("Move some players in world");
    p.move(Directions.DOWN);
    q.move(Directions.RIGHT);
    System.out.println("Location of all players in world");
    */
    for(Player pp: w.getPlayers()){
       System.out.println(pp.getLocation());
       System.out.println(pp.getLocation().getPlayers());
    }
    
    
    // what the game might look like...
    int turn_counter=0;
    System.out.println(w.getLocations());
    while( w.getHome().numberOfPeaches() <= 100&&turn_counter<=50){

      // iterate over all locations in the world
      System.out.println("##############Turn "+turn_counter+" Starts.##############");

      for (Player player:w.getPlayers()){
          System.out.println();
          System.out.println("&&"+player.getName() +"'s turn!");
          System.out.println(player.getName() + " is at " + player.getLocation()+ " at the beginning of this turn.");
          player.play();
      }
      System.out.println(w.home);
      System.out.println("##############Turn "+turn_counter+" ends.##############");
      turn_counter++;
    }
    System.out.println("Yeah!"+w.home+" has "+String.valueOf(((Home)(w.home)).peaches_track.get("SUM"))+" peaches now!");

  }
}