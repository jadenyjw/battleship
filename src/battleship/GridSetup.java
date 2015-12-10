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
    ImageIcon shipIcon = new ImageIcon("img/ship.png");
	public SetupButton buttons[][] = new SetupButton[10][10];
	private String shipName = "Aircraft Carrier", shipOrient = "Horizontal";
	private int[][] shipArray = {{-1,-1,0}
	,{-1,-1,0}
	,{-1,-1,0}
	,{-1,-1,0}
	,{-1,-1,0}};

	private int shipNum = -1;

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
		
		frame = new JFrame();
		frame.setBounds(100, 100, 550, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLocation(30, 27);
		frame.getContentPane().add(panel);
		panel.setLayout(new GridLayout(10, 10));
		panel.setSize(370,370);
		
		for (int i = 0; i<10; i++){
			for (int x = 0; x<10; x++){
				
			    buttons[i][x] = new SetupButton();
				buttons[i][x].setEnabled(true);
				final int tempX = i;
				final int tempY = x;
				buttons[i][x].addActionListener(new ActionListener(){
                
				        public void actionPerformed(ActionEvent e) {
				        	byte shipLen;
				        	if(shipName.equals("Aircraft Carrier")){
				    			shipLen = 5;
				    			shipNum = 0;
				        	}
				    		else if(shipName.equals("Battleship")){
				    			shipLen = 4;
				        		shipNum = 1;
				    		}
				    		else if(shipName.equals("Patrol Boat")){
				    			shipLen = 2;
				    			shipNum = 4;
				    		}
				    		else if(shipName.equals("Cruiser")){
				    			shipLen = 3;
				    			shipNum = 2;
				    		}
				    		else{
				    			shipLen = 3;
				    			shipNum = 3;
				    		}
				        	if (check(shipLen,shipOrient, tempX, tempY, shipArray, shipNum) == true){
				        		System.out.println("Valid");
				        		if (shipOrient.equals("Horizontal")){
				                    int orientation = 0;
				        			if (shipArray[shipNum][0] == -1){
				        				shipArray[shipNum][0] = tempY;
				        				shipArray[shipNum][1] = tempX;
				        				shipArray[shipNum][2] = orientation;
				        				System.out.println(shipArray[shipNum][0]);
				        				System.out.println(shipArray[shipNum][1]);
				        				System.out.println(shipArray[shipNum][2]);
				        			}
				        			else{
				        				int last = shipLen + shipArray[shipNum][0];
				        				for (int d = shipArray[shipNum][0]; d < last; d++){
				        					System.out.println(d);
					        				buttons[tempX][d].setIcon(null);
					       
					        			}
				        				shipArray[shipNum][0] = tempY;
				        				shipArray[shipNum][1] = tempX;
				        				shipArray[shipNum][2] = orientation;
				        			}
				        			
				        			int last = tempY + shipLen;
				        			for (int a = tempY; a < last; a++){
				        				buttons[tempX][a].setIcon(shipIcon);
				        			}
				        		}
				        		if (shipOrient.equals("Vertical")){
				        			
				        			int last = tempX + shipLen;
				        			for (int a = tempX; a < last; a++){
				        				buttons[a][tempY].setIcon(shipIcon);				        				
				        			}				        				
				        		}
				        	}
				        	else 
				        		JOptionPane.showMessageDialog(null, "Invalid placement.");				        	
				          } 
				   });
				panel.add(buttons[i][x]);
			}
		}
		
		JLabel lblEventLog = new JLabel("Ships");
		lblEventLog.setBounds(450, 10, 57, 14);
		frame.getContentPane().add(lblEventLog);
		
		JLabel lblABC = new JLabel("A         B          C          D          E          F         G          H           I           J");
		lblABC.setBounds(45, 10, 367, 14);
		frame.getContentPane().add(lblABC);
		
		JLabel label_1 = new JLabel("<html>1<br><br>2<br><br>3<br><br>4<br><br>5<br><br>6<br><br>7<br><br>8<br><br>9<br><br>10</html>");
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
		shipNames.setModel(new DefaultComboBoxModel(new String[] {"Aircraft Carrier", "Battleship", "Cruiser", "Submarine", "Patrol Boat"}));
		panel_1.add(shipNames);
		
		JComboBox orient = new JComboBox();
		orient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shipOrient = (String) orient.getSelectedItem();
			}
		});
		orient.setModel(new DefaultComboBoxModel(new String[] {"Horizontal", "Vertical"}));
		panel_1.add(orient);
		
		JButton btnFinish = new JButton("Done");
		panel_1.add(btnFinish);
		
		
	}
	private static boolean check(byte shipLen,String shipOrient, int yCoord, int xCoord, int shipArray[][], int shipNum){
		
		if(shipArray[shipNum][0] == -1){
			
		}
		
		if(shipOrient.equals("Horizontal")){
			if (xCoord < 11 - shipLen)
				return true;
			else				
				return false;
		}
		else{
			if (yCoord < 11 - shipLen)
				return true;
			else
				return false;
		}
	}
}