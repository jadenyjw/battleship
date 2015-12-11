package battleship;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

public class GridSetup extends JDialog {

	JFrame frame;
	static JLabel image;
	
	public static SetupButton buttons[][] = new SetupButton[10][10];

	private static String shipName = "Aircraft Carrier";
	private static String shipOrient = "Horizontal";
	public static int[][] shipArray = { { -1, -1, 0 }, { -1, -1, 0 }, { -1, -1, 0 }, { -1, -1, 0 }, { -1, -1, 0 } };
	private static int shipNum = -1;
	private static boolean error = false;

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
		System.out.println(Battleship.referer);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
        
		SetupButton ship[] = new SetupButton[6];

		frame = new JFrame();
		frame.setBounds(100, 100, 550, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setLocation(30, 27);
		frame.getContentPane().add(panel);
		panel.setLayout(new GridLayout(10, 10));
		panel.setSize(370, 370);
		
		image = new JLabel();
		image.setBounds(425,120,95,88);
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
							image.setIcon(new ImageIcon(GridSetup.class.getResource("/img/invalid.png")));
					}
				});
				panel.add(buttons[i][x]);
			}
		}

		JLabel lblEventLog = new JLabel("Ships");
		lblEventLog.setBounds(450, 10, 57, 14);
		frame.getContentPane().add(lblEventLog);

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
		shipNames.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shipName = (String) shipNames.getSelectedItem();
			}
		});
		shipNames.setModel(new DefaultComboBoxModel(
				new String[] { "Aircraft Carrier", "Battleship", "Cruiser", "Submarine", "Patrol Boat" }));
		panel_1.add(shipNames);

		JComboBox orient = new JComboBox();
		orient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shipOrient = (String) orient.getSelectedItem();
			}
		});
		orient.setModel(new DefaultComboBoxModel(new String[] { "Horizontal", "Vertical" }));
		panel_1.add(orient);

		JButton btnFinish = new JButton("Done");
		btnFinish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int count = 0;
				for (int r = 0; r <= 9; r++){
					for (int c = 0; c <= 9; c++){
						if(buttons[r][c].getIcon()!= SetupButton.water)
							count++;
					}
					
					
				}
				System.out.println(count);
				if (count == 17){
				if (Battleship.referer.equals("single")){
					SingleGame newClient = new SingleGame();
					newClient.frame.setVisible(true);
					frame.dispose();
				}
				else if (Battleship.referer.equals("host")){
					MultiGame newClient = new MultiGame();
					newClient.frame.setVisible(true);
					frame.dispose();
					
				}
				else if (Battleship.referer.equals("client")){
					MultigameClient newClient = new MultigameClient();
					newClient.frame.setVisible(true);
					frame.dispose();
				}
				}
				else
					JOptionPane.showMessageDialog(null, "Please place all your ships before deploying.");
			
			}
		});
		
		JButton btnRandomDeploy = new JButton("Random Deploy");
		btnRandomDeploy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				shipArray = randomDeploy(shipArray);
				System.out.println(shipArray[0][0]);
			}
		});
		panel_1.add(btnRandomDeploy);
		panel_1.add(btnFinish);
		
		JButton btnDebugButton = new JButton("Debug Button");
		btnDebugButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		panel_1.add(btnDebugButton);
		

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
	
	public static int[][] randomDeploy(int shipArray[][]){
		reset();
		
		byte shipLen[] = {5,4,3,3,2};
		for(int i = 0; i<5; i++){
			shipOrient = "Horizontal";
			int x,y,o;
			refresh(-1,-1);
			do{
				x = rng(9);
				y = rng(9);
				o = rng(1);
				if(o == 1)
					shipOrient = "Vertical";
				else
					shipOrient = "Horizontal";
				if(check(shipLen[i], shipOrient, y, x, shipArray, i)){
					System.out.println(x + " " + y + " " + o);
					shipArray[i][0] = x;
					shipArray[i][1] = y;
					shipArray[1][2] = o;
				}
				refresh(x,y);
			}while(check(shipLen[i], shipOrient, y, x, shipArray, i));
		}
		
		return shipArray;
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
		if (check(shipLen, shipOrient, tempX, tempY, shipArray, shipNum) == true) {
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
		System.out.println("This is a reset function. This is supposed to reset the board");
		for(int i = 0; i<10; i++){
			for(int x = 0; x<10; x++){
				System.out.println(i + " " + x);
				buttons[i][x].setIcon(SetupButton.water);
			}
		}
		shipArray = new int[][] { { -1, -1, 0 }, { -1, -1, 0 }, { -1, -1, 0 }, { -1, -1, 0 }, { -1, -1, 0 } };
		System.out.println("done");
	}
}