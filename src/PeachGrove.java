import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PeachGrove extends Location {
    private List<Peach> peachesAtTree;
    private List<PlayerVisit> playerVisit;


    public PeachGrove(Position p, String description, List<Player> people, List<Peach> peaches) {

        super(p,description,people,peaches);

        this.peachesAtTree = new ArrayList<Peach>();
        this.property="PeachGrove";

        Random random = new Random();

        for(int i = 0; i < random.nextInt(50)+100;i++ ) {
            Peach peach = new Peach(random.nextInt(10),false);
            this.peachesAtTree.add(peach);
        }

        this.playerVisit = new ArrayList<PlayerVisit>();
        System.out.println("There are "+String.valueOf(this.peachesAtTree.size())+" peaches on the tree here.");

    }


    private class PlayerVisit{

        protected String playerName;

        protected int times = 0;

        public PlayerVisit(Player player) {

            this.playerName = player.toString();

        }

    }



    public void bitByBee(Player p, int times) {

        Random roll = new Random();

        for(int i = 0; i < times; i++) {

            if (roll.nextDouble()>0.5) {

                p.setHealth(p.getHealth()-5);

            }

        }

    }

    public void playerVisitGrove(Player player) {

        int visitTimes = 0;

        boolean newVisitor = true;

        for(int i = 0; i < this.playerVisit.size(); i++) {

            if (player.getName().equals(playerVisit.get(i).playerName)) {

                visitTimes = playerVisit.get(i).times + 1;
                playerVisit.get(i).times=visitTimes;
                newVisitor = false;

            }

        }
        if (newVisitor) {
            PlayerVisit newPlayer = new PlayerVisit(player);
            newPlayer.times = 1;
            playerVisit.add(newPlayer);
            visitTimes = 1;
        }

        bitByBee(player,visitTimes);

    }

    public Peach getPeachesAtTree(){return peachesAtTree.remove(0); }
    public List<Peach> getterOfPeachesAtTree(){return this.peachesAtTree;}

}
