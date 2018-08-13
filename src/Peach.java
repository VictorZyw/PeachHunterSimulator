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
      if (this.ripeness+5>30){
        this.ripeness=30;
        this.bad=true;
      }else {
        this.ripeness += 3;
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