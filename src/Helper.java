
import java.util.*;


public class Helper extends Player {
    protected static int helper_counter=0;
    protected Player target;
    public Helper(World w, String name,List<Peach> peaches,Player helped){
        super(w,name+"-Helper"+String.valueOf(helper_counter),w.getHome(),peaches,50,RGB.GREEN);
        this.target=helped;
        Helper.helper_counter+=1;
    }

}
