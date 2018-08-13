import java.util.ArrayList;

public class PeachesGame{
  public static void main(String[] args){
    World w = new World();
    w.getWorld()[0][0]=new Home(w,new Position(0,0),"My home",new ArrayList<>(),new ArrayList<>());
    System.out.println("Home : " + w.getWorld()[0][0]+"???");
    w.getWorld()[1][1]=new PeachGrove(new Position(1,1),"PeachGrove1",new ArrayList<Player>(),new ArrayList<Peach>());
    w.getWorld()[1][2]=new PeachGrove(new Position(1,2),"PeachGrove2",new ArrayList<Player>(),new ArrayList<Peach>());
    w.getWorld()[2][0]=new PeachGrove(new Position(1,1),"PeachGrove3",new ArrayList<Player>(),new ArrayList<Peach>());
    PeachHunter p = new PeachHunter(w, "cat", w.home, new ArrayList<Peach>());
    PeachHunter q = new PeachHunter(w, "dog", w.home, new ArrayList<Peach>());
    PeachHunter r = new PeachHunter(w, "owl", w.home, new ArrayList<Peach>());

    w.addPlayer(p).addPlayer(q).addPlayer(r);
    
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
    while( w.getHome().numberOfPeaches() <= 100 ){

      // iterate over all locations in the world
      System.out.println("##############Turn "+turn_counter+" Starts.##############");

      for (Player player:w.getPlayers()){

          System.out.println(player.getName() + ", " + player.getLocation());
          player.play();
          //for (Peach peach:player.peaches){   //age all peaches at the player.
           // peach.age();
          //}

        //for (Peach peachAtLoc:location.peachesAtLocation){
          //peachAtLoc.age();    ////age all peaches at location
        //}

      }
      System.out.println("##############Turn "+turn_counter+" ends.##############");
      turn_counter++;
    }
    
  }
}