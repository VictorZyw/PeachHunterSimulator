import java.util.*;

public class Demo {
    public static void main(String[] arrays){
        int choice = Integer.parseInt(arrays[0]);
        World world = new World();
        world.getWorld()[0][0] = new Home(world,new Position(0,0),"My home",new ArrayList<>(),new ArrayList<>());
        PeachHunter peachHunter = new PeachHunter(world, Player.names[new Random().nextInt(Player.names.length)], world.home, new ArrayList<Peach>());
        world.addPlayer(peachHunter);
        world.getWorld()[2][2]=new PeachGrove(new Position(2,2),"Grove1",new ArrayList<Player>(),new ArrayList<Peach>());
        Random randNum = new Random();
        /*Choose your Demo mode using Command Line Prompt.
        5) Hunter:2  ; Grove:2 ; PeachPit 2; PeachThief 2;
        4) Hunter:2  ; Grove:1 ; PeachPit 2; PeachThief 1;
        3) Hunter:1  ; Grove:1 ; PeachPit 2; PeachThief 1;
        2) Hunter:1  ; Grove:1 ; PeachPit 2;
        1) Hunter:1  ; Grove:1
          * */
        switch(choice){
            case 5:
                PeachThief peachThief2 = new PeachThief(world, "Dog", world.getWorld()[1][1], new ArrayList<Peach>());
                world.addPlayer(peachThief2);
                world.getWorld()[0][2]=new PeachGrove(new Position(0,2),"Grove2",new ArrayList<Player>(),new ArrayList<Peach>());
            case 4:
                PeachHunter peachHunter2 = new PeachHunter(world, Player.names[new Random().nextInt(Player.names.length)], world.home, new ArrayList<Peach>());
                world.addPlayer(peachHunter2);
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
                world.getWorld()[1][1]=new PeachPit(new Position(1,1),"PeachPit1",new ArrayList<Player>(),PeachesInPit);
                world.getWorld()[2][1]=new PeachPit(new Position(2,1),"PeachPit2",new ArrayList<Player>(),PeachesInPit);
            case 1:
                break;
            default:
                break;
        };
        world.helperConstructor();   //construct all helpers ,equals to the number of non helpers. All not activated
        Screen thisScreen = new Screen(world);  //create Screen
        thisScreen.showScreen();

        int turns = 0;
        try{
            Thread.sleep(1500);
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        while (turns<50 && world.getHome().numberOfPeaches() <= 100){
            thisScreen.repaint();

            System.out.println("##############Turn "+turns+" Starts.##############");
            world.helperGenerator();  //set all helper needed for this turn to alive(activated);
            for (Player player:world.getPlayers()){
                if(player.alive==1){
                    System.out.println();
                    System.out.println("&&"+player.getName() +"'s turn!");
                    System.out.println(player.getName() + " is at " + player.getLocation()+ " at the beginning of this turn.");
                    player.play();               //let each player play for this turn.
                    for (Peach peach: player.peaches){
                        peach.age();        //age all peaches along with each player
                    }
                    System.out.println("&&"+player.getName() +" now has "+player.getHealth()+"HP.");
                }
            }
            world.deadPlayersHandler();
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
