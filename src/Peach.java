public class Peach implements Comparable<Peach>{
  protected int     ripeness;
  protected boolean bad;

  public Peach(int ripeness, boolean bad){
    this.ripeness = ripeness;
    this.bad = bad;
  }
  
  public Peach(int ripeness){
    this(ripeness, false);
  }
  
  public int getRipeness(){ return ripeness; }
  
  /** ages a peach in some way */
  public void age(){
    if(!bad) {
      if (this.ripeness+2>30){
        this.ripeness=30;
        this.bad=true;
        System.out.println("     A peach has become rotten.");
      }else {
        this.ripeness += 2;
      }
    }
  }

  public boolean isBad() {
    return bad;
  }

  @Override
  public int compareTo(Peach other){
    return 0;
  }
  
}