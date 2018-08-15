import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JLabel;
public class Screen extends JFrame{

    private World world;
    private JLabel[] players;
    private JLabel[] locationInfo;
    private JLabel playersInformation;
    private JLabel realTimeInformation;

    public Screen(World world){
        super("Demo for A5");
        setSize(world.getWorld()[0].length*200+400,world.getWorld().length*200+200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.world = world;

        /* can not draw lines after each repaint() for unknown reasons
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
		this.locationInfo = new JLabel[world.getWorld().length*world.getWorld()[0].length];
        for(int i = 0; i<this.locationInfo.length; i++) {
        	this.locationInfo[i] = new JLabel(this.world.getLocations().get(i).getDescription().toString());
        }

        this.players = new JLabel[world.getPlayers().size()];
        for(int i = 0; i < world.getPlayers().size(); i++) {
            players[i] = new JLabel(world.getPlayers().get(i).toString());
        }

        this.playersInformation = new JLabel(world.getPlayersInformation());
        this.realTimeInformation = new JLabel(world.getRealTimeInformation());
    }


    // check the order of player in a location to avoid printing different texts at the same place
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
            this.locationInfo[i].setText("<html>"+world.getLocations().get(i).getDescription()+"<br>"+world.getLocations().get(i).numberOfPeaches() + " peaches</html>");
            this.locationInfo[i].setBounds(i%world.getWorld().length*200+50,(int)(i/world.getWorld().length)*200,100,50);
            this.add(this.locationInfo[i]);
        }

        for(int i = 0; i< this.world.getPlayers().size(); i++) {
        	if (this.world.getPlayers().get(i).alive == 1) {
	            players[i].setText(world.getPlayers().get(i).toString());
	            players[i].setBounds(world.getPlayers().get(i).getLocation().getPosition().getY()*200+65,world.getPlayers().get(i).getLocation().getPosition().getX()*200+10*this.checkPlayerOrder(world.getPlayers().get(i).getLocation(),world.getPlayers().get(i))+50,100,50);
	            RGB color = world.getPlayers().get(i).colour;
	            players[i].setForeground(new Color(color.red,color.green,color.blue));
	            this.add(players[i]);
        	} else{
                players[i].setText("");
            }
        }


        this.realTimeInformation.setText(world.getRealTimeInformation());
        realTimeInformation.setBounds(5,world.getWorld().length*200,world.getWorld()[0].length*200-20,100);
        this.add(realTimeInformation);
        playersInformation.setText(world.getPlayersInformation());
        playersInformation.setBounds(world.getWorld()[0].length*200+20,0,200,this.getHeight()-50);
        this.add(playersInformation);
        this.setVisible(true);

    }
}

