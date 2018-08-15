import java.util.List;

public class Location {
  protected Position position;
  
  protected String description = " Default location.";
  
  protected List<Player> peopleAtLocation = null;
  protected List<Peach>  peachesAtLocation = null;
  protected String property;
  
  public Location(Position p, String description, List<Player> people, List<Peach> peaches){
    this.position = p;
    this.description = description;
    this.peopleAtLocation = people;
    this.peachesAtLocation = peaches;
    this.property="Location";
  }
  
  /** getter for position */
  public Position getPosition(){ return position; }
  
  /** getter for description */
  public String getDescription(){ return description; }
  
  /** getter for players */
  public List<Player> getPlayers(){ return peopleAtLocation; }
  
  /** getter for a Peach */
  public Peach getPeach(){return peachesAtLocation.remove(0); } 
  
  /** check number pf peaches in location */
  public int numberOfPeaches(){
    return peachesAtLocation == null ? 0 : peachesAtLocation.size();
  }
  
  
  /** adding a peach to the location */
  public void addPeach(Peach p){ peachesAtLocation.add(p); }
  
  /** allows the location to do something to a player when entering the location */
  public void enter(Player p){
    p.setLocation(this);
    //System.out.println(p.getLocation());
    peopleAtLocation.add(p);
    System.out.println(p.getName() + " just entered location " + position);
  }
  
  /** remove a player from a room */
  public void exit(Player p){
    peopleAtLocation.remove(p);
    System.out.println(p.getName() + " just left location " + position);
  }

  /** calculate the absolute distance of two locations*/
  public int distanceOf(Location loc){
    return Math.abs(this.getPosition().getX()-loc.getPosition().getX())+Math.abs(this.getPosition().getY()-loc.getPosition().getY());
  }


  /* ONLY for Home subclass */
  public void callForHelp(Player p, Location location){ //forfeited method.
  }
  
  @Override
  public String toString(){
    return description + position.toString();
  }


  public boolean equals(Location o){
    return (this.getPosition().getX()== o.getPosition().getX())&& (this.getPosition().getY()==o.getPosition().getY());
  }
  
  
}