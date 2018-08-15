
import java.util.*;


public class Helper extends Player {
    protected static int helper_counter = 0;
    protected Player target;

    public Helper(World w, String name, List<Peach> peaches) {
        super(w, name + "-Helper" + String.valueOf(helper_counter), w.getHome(), peaches, 50, RGB.GREEN);
        this.target = this;  //set the player who needs help as the helper itself
        this.alive=0;  //default to be not created.
        this.job="Helper";
        Helper.helper_counter += 1;
    }



    public Helper(World w, String name, List<Peach> peaches, Player to_be_helped) {
        super(w, name + "-Helper" + String.valueOf(helper_counter), w.getHome(), peaches, 50, RGB.GREEN);
        this.target = to_be_helped;  //set the player who needs help as the target
        this.alive=0;  //default to be not created.
        this.job="Helper";
        Helper.helper_counter += 1;
    }

    @Override
    public void play() {
        if (target.need_help) {
            if (!this.getLocation().equals(target.location)) {
                moveOneStep(target.getLocation());
            } else {
                int temp = peaches.size();
                int pre_hp = target.getHealth();
                for (int i = 0; i < temp; i++) {
                    target.peaches.add(this.getPeach());
                    target.eatPeach();
                }
                int new_hp = target.getHealth();
                System.out.println("##Action:" + this.getName() + " gave " + target.getName() + " " + String.valueOf(temp) + " peaches.");
                System.out.println("##Action:" + target.getName() + " was fed " + String.valueOf(temp) + " peaches, and his hp restored from " + pre_hp + "HP to " + new_hp + "HP.");
                target.need_help = false;
            }
        } else {
            if (this.getLocation().equals(world.getHome())) {
                System.out.println("##Action:" + this.getName() + " finished his job and just got home.He would leave this world.");
                target.helper_sent=false;
                die();
            } else {
                moveOneStep(world.home);
            }
        }
    }
}

