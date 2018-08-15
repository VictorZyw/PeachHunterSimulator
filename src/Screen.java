

import java.util.ArrayList;

import java.awt.Graphics2D;

import java.awt.geom.Line2D;

import javax.swing.*;

import java.awt.*;



public class Screen extends JFrame{

    private World world;

    private int[][] coordinatePrintTimes;

    private JLabel[] players;
    
    public JLabel[] playerInLocation;

    private JLabel playersInformation;

    private JLabel realTimeInformation;



    public Screen(World world){

        super("Demo for A5");

        setSize(world.getWorld()[0].length*200+400,world.getWorld().length*200+200);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.world = world;

		

		/* can not draw the lines after calling repaint() for unknown reasons

		JPanel pane=new JPanel(){

			public void paintComponent(Graphics g) {

				for (int i = 0; i<=world.getWorld().length;i++) {

					g.drawLine(0, i*200, world.getWorld()[0].length*200, i*200);

				}

				for (int j = 0; j <= world.getWorld()[0].length;j++) {

					g.drawLine(j*200, 0, j*200, world.getWorld().length*200);

				}

				g.drawLine(world.getWorld()[0].length*200, 0, world.getWorld()[0].length*200,this.getHeight());

				}

		};

		this.getContentPane().add(pane);

		*/





        this.coordinatePrintTimes = new int[world.getPlayers().size()][3]; //[x,y,print times]

        for(int i = 0; i < this.coordinatePrintTimes.length; i++) {

            for(int j =0; j <3;j++) {

                this.coordinatePrintTimes[i][j] =-1;

            }

        }
        this.players = new JLabel[world.getPlayers().size()];

        for(int i = 0; i < players.length; i++) {

            players[i] = new JLabel(world.getPlayers().get(i).toString());

        }

        this.playersInformation = new JLabel(world.getPlayersInformation());

        this.realTimeInformation = new JLabel(world.getRealTimeInformation());

    }



    private int checkPlayerOrder(Location location,Player player) {

    	for(int i = 0;i < location.getPlayers().size(); i++) {
    		if(location.getPlayers().get(i).equals(player)) {
    			return i+1;
    		}
    	}
        return 1;

    }



    public void showScreen() {

        this.setLayout(null);

        for(int i = 0; i<world.getWorld().length*world.getWorld()[0].length; i++) {

            JLabel areas = new JLabel("<html>"+world.getLocations().get(i).getDescription()+"<br>"+world.getLocations().get(i).numberOfPeaches() + " peaches</html>");

            areas.setBounds(i%world.getWorld().length*200+50,(int)(i/world.getWorld().length)*200,100,50);

            this.add(areas);

        }


        
        for(int i = 0; i< world.getPlayers().size(); i++) {
            players[i].setText(world.getPlayers().get(i).toString());

            players[i].setBounds(world.getPlayers().get(i).getLocation().getPosition().getX()*200+65,world.getPlayers().get(i).getLocation().getPosition().getY()*200+10*this.checkPlayerOrder(world.getPlayers().get(i).getLocation(),world.getPlayers().get(i))+50,100,50);

            this.add(players[i]);

        }
        /*
        for(int i = 0; i<world.getWorld().length;i++) {
        	for(int j = 0; j <world.getWorld()[0].length;j++) {
        		playerInLocation= new JLabel[world.getWorld()[i][j].getPlayers().size()];
        		for(int k = 0 ; k<playerInLocation.length; k++){
        			playerInLocation[k] = new JLabel(world.getWorld()[i][j].getPlayers().get(k).toString());
        			playerInLocation[k].setBounds(world.getPlayers().get(i).getLocation().getPosition().getX()*200+65,world.getPlayers().get(i).getLocation().getPosition().getY()*200+10*k+50,100,50);
        		this.add(playerInLocation[k]);
        		}
        	}
        }
        */


        this.realTimeInformation.setText(world.getRealTimeInformation());

        realTimeInformation.setBounds(5,world.getWorld().length*200,world.getWorld()[0].length*200-20,100);

        this.add(realTimeInformation);

        playersInformation.setText(world.getPlayersInformation());

        playersInformation.setBounds(world.getWorld()[0].length*200+20,0,200,this.getHeight()-50);

        this.add(playersInformation);



        this.setVisible(true);

    }





    public static void main(String[] arrays) {

        World world = new World();

        Player p = new Player(world, "cat", world.home, new ArrayList<Peach>(), 50, RGB.YELLOW);

        Player q = new Player(world, "dog", world.home, new ArrayList<Peach>(), 100, RGB.BLUE);

        world.addPlayer(p).addPlayer(q);



        Screen thisScreen = new Screen(world);

        try {

            thisScreen.showScreen();

            Thread.sleep(1500);

        } catch (InterruptedException e) {

            e.printStackTrace();

        }

        for(int i =0 ; i <3; i++) {

            thisScreen.repaint();

            p.move(Directions.DOWN);

            q.move(Directions.RIGHT);

            thisScreen.showScreen();

            try {

                Thread.sleep(500);

            } catch (InterruptedException e) {

                e.printStackTrace();

            }

        }



    }

}

