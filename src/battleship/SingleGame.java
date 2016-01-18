package battleship;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.JCheckBox;

public class SingleGame {
	
	public static GridButton buttons[][] = new GridButton[10][10];
	public static GridButton enemyButtons[][] = new GridButton[10][10];
	public static DefaultListModel<String> listModel;//#Variable
	public static JList<String> list = new JList<String>();
	private static int firstHit[] = {-1,-1};
	private static int pointHit[] = {-1,-1};
	private static int checkRound[] = new int[4];
	private static int shotDirect = GridSetup.rng(4);
	private static String aiMode = "search";//#string
	private static int shotX, shotY;
	private static boolean letLive = true;
	// Variables, public and private
	
	
	static JFrame frame;
	private JTextField textField;
	public static boolean hasNewText;
	//initialize frame and other required info

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					SingleGame window = new SingleGame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}//End Main

	public SingleGame() {

		initialize();//initialize the board
		
		

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		System.out.println("Check 1");
		frame = new JFrame();
		frame.setBounds(100, 100, 970, 450);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setIconImage(Battleship.img.getImage());
		frame.getContentPane().setLayout(null);
		// New frame, set the bounds and made it unresizable. Various customizations occur

		JPanel panel = new JPanel();
		panel.setLocation(30, 27);
		frame.getContentPane().add(panel);
		panel.setLayout(new GridLayout(10, 10));
		panel.setSize(370, 370);
		System.out.println("Check 2");
		// Sets the playing boards

		for (int i = 0; i < 10; i++) {
			for (int x = 0; x < 10; x++) {

				buttons[i][x] = new GridButton();
				buttons[i][x].setIcon(GridButton.water);
				buttons[i][x].setDisabledIcon(GridButton.water);
				buttons[i][x].setEnabled(false);
				panel.add(buttons[i][x]);
			}
		}// Colors in the squares of the grid
		System.out.println("Check 3");

		int shipLen;
		for (int i = 0; i < 5; i++) {//#loop
			switch (i) {
			case 0:
				shipLen = 5;
				break;
			case 1:
				shipLen = 4;
				break;
			case 2:
				shipLen = 3;
				break;
			case 3:
				shipLen = 3;
				break;
			case 4:
				shipLen = 2;
				break;
			default:
				shipLen = 0;

			}// Finds the switched ints
			if (GridSetup.shipArray[i][2] == 0) {
				int last = shipLen + GridSetup.shipArray[i][0];
				for (int x = GridSetup.shipArray[i][0]; x < last; x++) {
					buttons[GridSetup.shipArray[i][1]][x].setIcon(GridButton.shipIcon);
					buttons[GridSetup.shipArray[i][1]][x].setDisabledIcon(GridButton.shipIcon);
				}
			} else if (GridSetup.shipArray[i][2] == 1) {
				int last = shipLen + GridSetup.shipArray[i][1];
				for (int y = GridSetup.shipArray[i][1]; y < last; y++) {
					buttons[y][GridSetup.shipArray[i][0]].setIcon(GridButton.shipIcon);
					buttons[y][GridSetup.shipArray[i][0]].setDisabledIcon(GridButton.shipIcon);
				}

			}
		}// places the preset ships
		
		System.out.println("Check 4");

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(555, 27, 370, 370);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(new GridLayout(10, 10));

		listModel = new DefaultListModel<String>();
		list.setModel(listModel);
		Border listBorder = BorderFactory.createLineBorder(Color.BLACK);
		list.setBorder(BorderFactory.createCompoundBorder(listBorder, null));
		JScrollPane scrollLog = new JScrollPane(list);
		scrollLog.setBounds(410, 27, 134, 370);
		frame.getContentPane().add(scrollLog);
		// Sets the event log

		JLabel lblEventLog = new JLabel("Event Log");
		lblEventLog.setBounds(450, 10, 57, 14);
		frame.getContentPane().add(lblEventLog);

		JLabel lblABC = new JLabel(
				"A         B         C           D          E          F         G          H           I           J");
		lblABC.setBounds(45, 10, 367, 14);
		frame.getContentPane().add(lblABC);

		JLabel label = new JLabel(
				"A          B          C           D          E          F         G          H           I           J");
		label.setBounds(565, 10, 367, 14);
		frame.getContentPane().add(label);

		JLabel label_1 = new JLabel(
				"<html>1<br><br>2<br><br>3<br><br>4<br><br>5<br><br>6<br><br>7<br><br>8<br><br>9<br><br>10</html>");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		label_1.setVerticalAlignment(SwingConstants.TOP);
		label_1.setBounds(10, 28, 46, 370);
		frame.getContentPane().add(label_1);

		JLabel label_2 = new JLabel(
				"<html>1<br><br>2<br><br>3<br><br>4<br><br>5<br><br>6<br><br>7<br><br>8<br><br>9<br><br>10</html>");
		label_2.setVerticalAlignment(SwingConstants.TOP);
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		label_2.setBounds(935, 27, 46, 370);
		frame.getContentPane().add(label_2);
		// Labels
		
		System.out.println("Check 5");
		
		for (int i = 0; i < 10; i++) {
			for (int x = 0; x < 10; x++) {

				enemyButtons[i][x] = new GridButton();
				enemyButtons[i][x].setIcon(GridButton.water);
				
				enemyButtons[i][x].setEnabled(true);
				panel_1.add(enemyButtons[i][x]);
				final int tempX = i;
				final int tempY = x;
				enemyButtons[i][x].addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						shotOut(tempX, tempY);
						disableButtons();
						int enemyCount = 0, playerCount = 0;
						for (int r = 0; r <= 9; r++){
							for (int c = 0; c <= 9; c++){
								if(enemyButtons[r][c].getIcon()== GridButton.hit)
									enemyCount++;
							}
						}
						if(enemyCount >= 17){
							endGame(true);
						}
						else{
							aiRun();
							for (int r = 0; r <= 9; r++){
								for (int c = 0; c <= 9; c++){
									if(buttons[r][c].getIcon()== GridButton.hit)
										playerCount++;
								}
							}
							listModel.addElement("");
							int lastIndex = list.getModel().getSize() - 1;
							if (lastIndex >= 0) {
								list.ensureIndexIsVisible(lastIndex);
							}
							if(playerCount >= 17){
								endGame(false);
							}
							else{
								enableButtons();
							}
						}
					}
				});
			}
		}
		// What happens when you click
		if(GridSetup.cheatTog){//#cheat
			GridButton.hiddenShip = new ImageIcon(Battleship.class.getResource("/ship.png"));
		}
		else{
			GridButton.hiddenShip = new ImageIcon(Battleship.class.getResource("/water.png"));
		}
		// Refreshes the board
		for (int i = 0; i < 5; i++) {
			switch (i) {
				case 0:
					shipLen = 5;
					break;
				case 1:
					shipLen = 4;
					break;
				case 2:
					shipLen = 3;
					break;
				case 3:
					shipLen = 3;
					break;
				case 4:
					shipLen = 2;
					break;
				default:
					shipLen = 0;
			

			}
			System.out.println("Check 5.1");
			System.out.println(GridSetup.aiArray[i][2]);
			if (GridSetup.aiArray[i][2] == 0) {
				int last = shipLen + GridSetup.aiArray[i][0];
				for (int x = GridSetup.aiArray[i][0]; x < last; x++) {

					enemyButtons[GridSetup.aiArray[i][1]][x].setIcon(GridButton.hiddenShip);
				}
			} else if (GridSetup.aiArray[i][2] == 1) {
				int last = shipLen + GridSetup.aiArray[i][1];
				for (int y = GridSetup.aiArray[i][1]; y < last; y++) {
					enemyButtons[y][GridSetup.aiArray[i][0]].setIcon(GridButton.hiddenShip);
				}

			}
		}
		
	}
	// refreshes the AI board
	private static void aiRun(){ // The AI searching algorithm
		for (int i = 0; i < 10; i++) {
			for (int x = 0; x < 10; x++) {
				if(buttons[i][x].getIcon() != GridButton.hit && buttons[i][x].getIcon() != GridButton.miss){
					buttons[i][x].setEnabled(true);
				}
			}
		}//refreshes the screen
		do{
			System.out.println("check");
			if(firstHit[0] != -1 && !aiMode.equals("back")){
				System.out.println("check1");
				do{
					if(aiMode.equals("pinpoint")){//We hit something, so we see which direction we go.
						do{
							shotDirect = GridSetup.rng(4);
						}while(checkRound[shotDirect] != 0);
						checkRound[shotDirect] = 1;
					}
					switch(shotDirect){
						case 0: 
							shotX = pointHit[0]-1; shotY = pointHit[1];
							break;
						case 1:
							shotX = pointHit[0]; shotY = pointHit[1]+1;
							break;
						case 2:
							shotX = pointHit[0]+1; shotY = pointHit[1];
							break;
						case 3:
							shotX = pointHit[0]; shotY = pointHit[1]-1;
							break;
						default:
							System.out.println("Error");
							System.out.println("Shotdirect is " + shotDirect);
							shotX = 0; shotY = 0;
					}
					System.out.println("check2");
					System.out.println(shotX + "" + shotY);
					System.out.println(shotDirect);
					if(!check(shotX,shotY) && !aiMode.equals("pinpoint")){
						aiMode = "back";
						pointHit = firstHit;
						switch(shotDirect){
							case 0: 
								shotX = pointHit[0]+1; shotY = pointHit[1];
								break;
							case 1:
								shotX = pointHit[0]; shotY = pointHit[1]-1;
								break;
							case 2:
								shotX = pointHit[0]-1; shotY = pointHit[1];
								break;
							case 3:
								shotX = pointHit[0]; shotY = pointHit[1]+1;
								break;
							default:
								System.out.println("Error");
								System.out.println("Shotdirect is " + shotDirect);
								shotX = 0; shotY = 0;
						}
						System.out.println("check3");
						if(!check(shotX,shotY)){
							aiMode = "search";
							firstHit = new int[] {-1,-1};
							shotX = GridSetup.rng(10); shotY = GridSetup.rng(10);
						}
						System.out.println("check4");
					
					}
					System.out.println("CHECKKKK");
					System.out.println(shotX);
					System.out.println(shotY);
				}while(!check(shotX,shotY));
				System.out.println("MAKE SURE");
				checkRound = new int[4];
			}
			else if(firstHit[0] != -1 && aiMode.equals("back")){//Check if there is anything behind what we just hit
				switch(shotDirect){
					case 0: 
						shotX = pointHit[0]+1; shotY = pointHit[1];
						break;
					case 1:
						shotX = pointHit[0]; shotY = pointHit[1]-1;
						break;
					case 2:
						shotX = pointHit[0]-1; shotY = pointHit[1];
						break;
					case 3:
						shotX = pointHit[0]; shotY = pointHit[1]+1;
						break;
					default:
						System.out.println("Error");
						System.out.println("Shotdirect is " + shotDirect);
						shotX = 0; shotY = 0;
				}
				System.out.println("check5");
				if(!check(shotX,shotY)){
					System.out.println("check6");
					aiMode = "search";
					firstHit = new int[] {-1,-1};
					do{
						shotX = GridSetup.rng(10); shotY = GridSetup.rng(10);
					}while(checkRound(shotX,shotY));
				}
			}
			else{
				do{
					System.out.println("check7");
					shotX = GridSetup.rng(10); shotY = GridSetup.rng(10);
				}while(checkRound(shotX,shotY));
				firstHit = new int[] {-1,-1};
			}
		}while(buttons[shotX][shotY].getIcon() == GridButton.hit || buttons[shotX][shotY].getIcon() == GridButton.miss);
		System.out.println("check8");
		System.out.println(shotX +" "+ shotY);
		if(buttons[shotX][shotY].getIcon() != GridButton.water){
			buttons[shotX][shotY].setIcon(GridButton.hit);
			buttons[shotX][shotY].setDisabledIcon(GridButton.hit);
			output(true, shotX+1, shotY+1, "The AI");
			if(firstHit[0] == -1){
				firstHit = new int[] {shotX,shotY};
				pointHit = firstHit;
			}
			else{
				pointHit = new int[] {shotX,shotY};
			}
			if(aiMode.equals("search"))
				aiMode = "pinpoint";
			else if(aiMode.equals("pinpoint"))
				aiMode = "combo";
			System.out.println("Hit");
		}
		else{
			buttons[shotX][shotY].setIcon(GridButton.miss);
			buttons[shotX][shotY].setDisabledIcon(GridButton.miss);
			output(false, shotX+1, shotY+1, "The AI");
			if(aiMode.equals("back")){
				aiMode = "search";
				letLive = true;
				firstHit = new int[] {-1,-1};
			}
			else if(aiMode.equals("combo")){
				aiMode = "back";
				pointHit = firstHit;
			}
			System.out.println("Miss");
		}
		for (int i = 0; i < 10; i++) {
			for (int x = 0; x < 10; x++) {
				buttons[i][x].setEnabled(false);
			}
		}
	}
	
	
	private static void shotOut(int x, int y){//changes the square clicked
		if(enemyButtons[x][y].getIcon() == GridButton.hiddenShip){
			enemyButtons[x][y].setIcon(GridButton.hit);
			output(true, x+1, y+1, "You");
			
		}
		else{
			enemyButtons[x][y].setIcon(GridButton.miss);
			output(false, x+1, y+1, "You");
		}
	}
	
	private static void disableButtons(){//Disables all buttons
		for (int i = 0; i < 10; i++) {
			for (int x = 0; x < 10; x++) {
				enemyButtons[i][x].setDisabledIcon(enemyButtons[i][x].getIcon());
				enemyButtons[i][x].setEnabled(false);
			}
		}
	}
	private static void enableButtons(){//Enables all buttons
		for (int i = 0; i < 10; i++) {
			for (int x = 0; x < 10; x++) {
				if(enemyButtons[i][x].getIcon() != GridButton.hit && enemyButtons[i][x].getIcon() != GridButton.miss){
					enemyButtons[i][x].setEnabled(true);
				}
			}
		}
	}
	private static boolean checkRound(int xVal, int yVal){//Make sure there isn't a hit beside the shot about to be taken. RNG help make it easier
		System.out.println(buttons[xVal][yVal].getIcon());
		if(buttons[xVal][yVal].getIcon() == GridButton.shipIcon){
			if(GridSetup.rng(2) == 1)// Makes it more fair.
				return true;
			return false;
		}
		if(xVal <= 8){
			if(buttons[xVal+1][yVal].getIcon() == GridButton.miss){
				return true;
			}
		}
		if(yVal <= 8){
			if(buttons[xVal][yVal+1].getIcon() == GridButton.miss){
				return true;
			}
		}
		if(xVal >= 1){
			if(buttons[xVal-1][yVal].getIcon() == GridButton.miss){
				return true;
			}
		}
		if(yVal >= 1){
			if(buttons[xVal][yVal-1].getIcon() == GridButton.miss){
				return true;
			}
		}
		return false;
	}
	private static boolean check(int xVal, int yVal){//Checks if the coordinates are valid
		if(xVal > 9 || yVal > 9 || xVal < 0 || yVal < 0){
			return false;
		}
		else if(buttons[xVal][yVal].getIcon() != GridButton.hit && buttons[xVal][yVal].getIcon() != GridButton.miss){
			if((buttons[shotX][shotY].getIcon() == GridButton.shipIcon && letLive)){
				letLive = false;
				return false;
			}
			return true;
		}
		else{
			return false;
		}
	}
	private static void output(boolean hit, int xVar, int yVar, String whoShot){// outputs to the event log
		char yCord = 'A';
		switch(yVar){
		case 1: yCord = 'A';
				break;
		case 2: yCord = 'B';
				break;
		case 3: yCord = 'C';
				break;
		case 4: yCord = 'D';
				break;
		case 5: yCord = 'E';
				break;
		case 6: yCord = 'F';
				break;
		case 7: yCord = 'G';
				break;
		case 8: yCord = 'H';
				break;
		case 9: yCord = 'I';
				break;
		case 10: yCord = 'J';
				break;
		}
		if(hit){
			listModel.addElement(whoShot + " hit:    " + xVar + "," + yCord);
		}
		else{
			listModel.addElement(whoShot + " missed: " + xVar + "," + yCord);
		}
	}
	
	private static void endGame(boolean win){//#method
		int choice;
		for(int x = 0; x<10; x++){
			for(int y = 0; y<10; y++){
				if(enemyButtons[x][y].getDisabledIcon() == GridButton.hiddenShip){
					enemyButtons[x][y].setDisabledIcon(GridButton.shipIcon);
				} 	 	
			}
		}
		
		if(win){
			choice = JOptionPane.showConfirmDialog(null, "You Win\nPlay Again?", null, 0);
		}
		else{
			choice = JOptionPane.showConfirmDialog(null, "You Lose\nPlay Again?", null, 0);
		}
		if(choice == 0){//#condition
			Battleship.referer = "single";
			buttons = new GridButton[10][10];
			enemyButtons = new GridButton[10][10];
			firstHit = new int[] {-1,-1};
			pointHit = new int[] {-1,-1};
			shotDirect = GridSetup.rng(4);
			aiMode = "search";
			shotX = 0; shotY = 0;
			GridSetup newClient = new GridSetup();
			newClient.frame.setVisible(true);
			frame.dispose();
			//Reset the variables for a new game
		}
		else{
			JOptionPane.showMessageDialog(null, "Thank you for playing!");
			frame.dispose();
		}
	}
}