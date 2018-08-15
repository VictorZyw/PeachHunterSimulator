import java.util.*;

public class Demo {
    public static void main(String[] arrays){
        int choice = Integer.parseInt(arrays[0]);
        World world = new World();
        world.getWorld()[0][0] = new Home(world,new Position(0,0),"My home",new ArrayList<>(),new ArrayList<>());
        PeachHunter peachHunter = new PeachHunter(world, "Dog", world.home, new ArrayList<Peach>());
        world.addPlayer(peachHunter);
        world.getWorld()[2][2]=new PeachGrove(new Position(2,2),"PeachGrove",new ArrayList<Player>(),new ArrayList<Peach>());
        Random randNum = new Random();
        switch(choice){
        	case 3:
        		PeachThief peachThief = new PeachThief(world, "Cat", world.getWorld()[2][1], new ArrayList<Peach>());
        		world.addPlayer(peachThief);
            case 2:
                List<Peach> PeachesInPit= new ArrayList<Peach>();
                boolean bad = false;
                for(int i =0; i < randNum.nextInt(10);i++){
                    if(randNum.nextDouble()>0.5){
                        bad = true;
                    }
                    Peach peach = new Peach(randNum.nextInt(10),bad);
                    PeachesInPit.add(peach);
                }
                world.getWorld()[1][1]=new PeachPit(new Position(1,1),"PeachPit",new ArrayList<Player>(),PeachesInPit);
            case 1:
                break;
            default:
                break;
        };
        Screen thisScreen = new Screen(world);
        thisScreen.showScreen();

        int turns = 0;
        try{
            Thread.sleep(1500);
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        while (turns<50 && world.getHome().numberOfPeaches() <= 100){
            thisScreen.repaint();
           /* for(int i = 0; i<world.getPlayers().size(); i++){
                world.getPlayers().get(i).play();
            }
            */

            // iterate over all locations in the world
            System.out.println("##############Turn "+turns+" Starts.##############");

            for (Player player:world.getPlayers()){
                System.out.println();
                System.out.println("&&"+player.getName() +"'s turn!");
                System.out.println(player.getName() + " is at " + player.getLocation()+ " at the beginning of this turn.");
                player.play();
                System.out.println("&&"+player.getName() +" now has "+player.getHealth()+"HP.");

            }
            System.out.println(world.home);
            System.out.println("##############Turn "+turns+" ends.##############");
            thisScreen.showScreen();
            try{
                thisScreen.showScreen();
                Thread.sleep(1000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            turns ++;
        }
    }

}
