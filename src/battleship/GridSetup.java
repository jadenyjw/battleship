package battleship;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;

public class GridSetup extends JDialog {

	JFrame frame;
	static JLabel image;
	
	public static SetupButton buttons[][] = new SetupButton[10][10];

	private static String shipName = "Aircraft Carrier";
	private static String shipOrient = "Horizontal";
	public static int[][] shipArray = { { -1, -1, 0 }, { -1, -1, 0 }, { -1, -1, 0 }, { -1, -1, 0 }, { -1, -1, 0 } };
	public static int[][] aiArray = { { -1, -1, 0 }, { -1, -1, 0 }, { -1, -1, 0 }, { -1, -1, 0 }, { -1, -1, 0 } };
	private static int shipNum = -1;
	private static boolean error = false;
	public static boolean cheatTog = false;

	public static int clientShip[][] = new int[5][3] ;
	public static int hostShip[][] = new int[5][3] ;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GridSetup window = new GridSetup();
					window.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GridSetup() {
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
        
		SetupButton ship[] = new SetupButton[6];

		frame = new JFrame("Battleship X Pro Limited Edition 2 | Grid Setup");
		frame.setBounds(100, 100, 555, 450);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setLocation(30, 27);
		frame.getContentPane().add(panel);
		panel.setLayout(new GridLayout(10, 10));
		panel.setSize(370, 370);
		
		image = new JLabel();
		image.setBounds(445,210,64,90);
		image.setIcon(null);
		frame.getContentPane().add(image);
		
		for (int i = 0; i < 10; i++) {
			for (int x = 0; x < 10; x++) {

				buttons[i][x] = new SetupButton();
				buttons[i][x].setEnabled(true);
				buttons[i][x].setIcon(SetupButton.water);
				final int tempX = i;
				final int tempY = x;
				buttons[i][x].addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						image.setIcon(null);
						refresh(tempX, tempY);
						if(error)
							image.setIcon(new ImageIcon(Battleship.class.getResource("/invalid.png")));
					}
				});
				panel.add(buttons[i][x]);
			}
		}

		JLabel lblABC = new JLabel(
				"A         B          C          D          E          F         G          H           I           J");
		lblABC.setBounds(45, 10, 367, 14);
		frame.getContentPane().add(lblABC);

		JLabel label_1 = new JLabel(
				"<html>1<br><br>2<br><br>3<br><br>4<br><br>5<br><br>6<br><br>7<br><br>8<br><br>9<br><br>10</html>");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		label_1.setVerticalAlignment(SwingConstants.TOP);
		label_1.setBounds(10, 28, 46, 370);
		frame.getContentPane().add(label_1);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(412, 27, 126, 370);
		frame.getContentPane().add(panel_1);

		JComboBox shipNames = new JComboBox();
		TitledBorder title;//used for titles around combo boxes
		title = BorderFactory.createTitledBorder("Ships");
		shipNames.setBorder(title);	
		shipNames.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shipName = (String) shipNames.getSelectedItem();
			}
		});
		shipNames.setModel(new DefaultComboBoxModel(
				new String[] { "Aircraft Carrier", "Battleship", "Cruiser", "Submarine", "Patrol Boat" }));
		panel_1.add(shipNames);

		JComboBox orient = new JComboBox();
		TitledBorder orientation;//used for titles around combo boxes
		orientation = BorderFactory.createTitledBorder("Orientation");
		orient.setBorder(orientation);
		orient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shipOrient = (String) orient.getSelectedItem();
			}
		});
		orient.setModel(new DefaultComboBoxModel(new String[] { "Horizontal", "Vertical" }));
		panel_1.add(orient);

		JButton btnFinish = new JButton("Done");
		btnFinish.setVerticalAlignment(SwingConstants.BOTTOM);
		btnFinish.setHorizontalAlignment(SwingConstants.RIGHT);
		btnFinish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int count = 0;
				for (int r = 0; r <= 9; r++){
					for (int c = 0; c <= 9; c++){
						if(buttons[r][c].getIcon()!= SetupButton.water)
							count++;
					}
					
					
				}
			
				if (count == 17){
				if (Battleship.referer.equals("single")){
					frame.dispose();
					int tempShipArray[][] = new int[5][3];
					for(int x = 0; x<5; x++){
						for(int y = 0; y<3; y++){
							System.out.println("Hey");
							tempShipArray[x][y] = shipArray[x][y];
						}
					}
					randomDeploy();
					for(int x = 0; x<5; x++){
						for(int y = 0; y<3; y++){
							System.out.println("There");
							aiArray[x][y] = shipArray[x][y];
						}
					}
					for(int x = 0; x<5; x++){
						for(int y = 0; y<3; y++){
							System.out.println("Error not here");
							shipArray[x][y] = tempShipArray[x][y];
						}
					}
					
					SingleGame newClient = new SingleGame();
					newClient.frame.setVisible(true);
				}
				else if (Battleship.referer.equals("host")){
					MultiGameHost newClient = new MultiGameHost();
					newClient.frame.setVisible(true);
					frame.dispose();
					
				}
				else if (Battleship.referer.equals("client")){
					MultiGameClient newClient = new MultiGameClient();
					newClient.frame.setVisible(true);
					frame.dispose();
				}
				}
				else
					JOptionPane.showMessageDialog(null, "Please place all your ships before deploying.");
			
			}
		});
		
		JButton btnRandomDeploy = new JButton("Random Deploy");
		btnRandomDeploy.setVerticalAlignment(SwingConstants.BOTTOM);
		btnRandomDeploy.setHorizontalAlignment(SwingConstants.RIGHT);
		btnRandomDeploy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				randomDeploy();
				shipName = (String) shipNames.getSelectedItem();
			
			}
		});
		panel_1.add(btnRandomDeploy);
		panel_1.add(btnFinish);
		
		if(Battleship.referer.equals("single")){
			JCheckBox cheatMode = new JCheckBox("Cheat Mode");
			cheatMode.setBounds(422, 391, 97, 23);
			frame.getContentPane().add(cheatMode);
			cheatTog = cheatMode.isSelected();
		}

	}


	private static boolean check(byte shipLen, String shipOrient, int yCoord, int xCoord, int shipArray[][],int shipNum){

		if (shipOrient.equals("Horizontal")) {

			if (xCoord < 11 - shipLen) {
				int last = shipLen + xCoord;

				for (int t = xCoord; t < last; t++) {
					if (buttons[yCoord][t].getIcon() != SetupButton.shipIcon[shipNum] && buttons[yCoord][t].getIcon() != SetupButton.water) {
						return false;
					}
				}
				return true;
			} else
				return false;
		} else {
			if (yCoord < 11 - shipLen) {
				int last = shipLen + yCoord;
				for (int t = yCoord; t < last; t++) {
					if (buttons[t][xCoord].getIcon() != SetupButton.shipIcon[shipNum] && buttons[t][xCoord].getIcon() != SetupButton.water) {
						return false;
					}
				}

				return true;
			}

			else
				return false;
		}
	}
	
	public static void randomDeploy(){
		reset();
		
		byte shipLen[] = {5,4,3,3,2};
		for(shipNum = 0; shipNum<5; shipNum++){
			
			switch(shipNum){
				case 0: shipName = "Aircraft Carrier";
						break;
				case 1: shipName = "Battleship";
						break;
				case 2: shipName = "Cruiser";
						break;
				case 3: shipName = "Submarine";
						break;
				default: shipName = "Patrol Boat";
						break;
			}
			
			shipOrient = "Horizontal";
			int x,y,o;
			do{
				x = rng(9);
				y = rng(9);
				o = rng(2);
				
				if(o == 1)
					shipOrient = "Vertical";
				else
					shipOrient = "Horizontal";
				
				refresh(x,y);
			}while(error);
			
			
		}
		
	}
	
	public static int rng(int range){
		return (int) (Math.random() * range);
	}
	
	private static void refresh(int tempX, int tempY){

		byte shipLen;
		if (shipName.equals("Aircraft Carrier")) {
			shipLen = 5;
			shipNum = 0;
		} else if (shipName.equals("Battleship")) {
			shipLen = 4;
			shipNum = 1;
		} else if (shipName.equals("Patrol Boat")) {
			shipLen = 2;
			shipNum = 4;
		} else if (shipName.equals("Cruiser")) {
			shipLen = 3;
			shipNum = 2;
		} else {
			shipLen = 3;
			shipNum = 3;
		}
		
		if (check(shipLen, shipOrient, tempX, tempY, shipArray, shipNum)) {
			
			error = false;
			if (shipOrient.equals("Horizontal")) {
				int orientation = 0;
				if (shipArray[shipNum][0] == -1) {
					shipArray[shipNum][0] = tempY;
					shipArray[shipNum][1] = tempX;
					shipArray[shipNum][2] = orientation;
				} else {

					if (shipArray[shipNum][2] == 0) {
						int last = shipLen + shipArray[shipNum][0];
						for (int d = shipArray[shipNum][0]; d < last; d++) {

							buttons[shipArray[shipNum][1]][d].setIcon(SetupButton.water);

						}
					} else if (shipArray[shipNum][2] == 1) {
						int last = shipLen + shipArray[shipNum][1];
						for (int d = shipArray[shipNum][1]; d < last; d++) {

							buttons[d][shipArray[shipNum][0]].setIcon(SetupButton.water);
						}
					}
					shipArray[shipNum][0] = tempY;
					shipArray[shipNum][1] = tempX;
					shipArray[shipNum][2] = orientation;
				}

				int last = tempY + shipLen;
				for (int a = tempY; a < last; a++) {
					buttons[tempX][a].setIcon(SetupButton.shipIcon[shipNum]);
				}
			}
			if (shipOrient.equals("Vertical")) {
				int orientation = 1;
				if (shipArray[shipNum][0] == -1) {
					shipArray[shipNum][0] = tempY;
					shipArray[shipNum][1] = tempX;
					shipArray[shipNum][2] = orientation;
				} else {

					if (shipArray[shipNum][2] == 0) {
						int last = shipLen + shipArray[shipNum][0];
						for (int d = shipArray[shipNum][0]; d < last; d++) {

							buttons[shipArray[shipNum][1]][d].setIcon(SetupButton.water);

						}
					} else if (shipArray[shipNum][2] == 1) {
						int last = shipLen + shipArray[shipNum][1];
						for (int d = shipArray[shipNum][1]; d < last; d++) {

							buttons[d][shipArray[shipNum][0]].setIcon(SetupButton.water);
						}
					}
					shipArray[shipNum][0] = tempY;
					shipArray[shipNum][1] = tempX;
					shipArray[shipNum][2] = orientation;
				}
				int last = tempX + shipLen;
				for (int a = tempX; a < last; a++) {
					buttons[a][tempY].setIcon(SetupButton.shipIcon[shipNum]);
				}
			}
		}
		else
			error = true;
	
	}
	private static void reset(){
		
		for(int i = 0; i<10; i++){
			for(int x = 0; x<10; x++){
				buttons[i][x].setIcon(SetupButton.water);
			}
		}
		shipArray = new int[][] { { -1, -1, 0 }, { -1, -1, 0 }, { -1, -1, 0 }, { -1, -1, 0 }, { -1, -1, 0 } };
		
	}
}