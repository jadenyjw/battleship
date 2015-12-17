package battleship;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class SingleGame {
	
	
	
	JFrame frame;
	private JTextField textField;
	public static boolean hasNewText;

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
	}

	public SingleGame() {

		initialize();
		
		

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		GridButton buttons[][] = new GridButton[10][10];
		GridButton enemyButtons[][] = new GridButton[10][10];
		
		System.out.println("Check 1");
		frame = new JFrame();
		frame.setBounds(100, 100, 970, 450);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setLocation(30, 27);
		frame.getContentPane().add(panel);
		panel.setLayout(new GridLayout(10, 10));
		panel.setSize(370, 370);
		System.out.println("Check 2");

		for (int i = 0; i < 10; i++) {
			for (int x = 0; x < 10; x++) {

				buttons[i][x] = new GridButton();
				buttons[i][x].setIcon(GridButton.water);
				buttons[i][x].setDisabledIcon(GridButton.water);
				buttons[i][x].setEnabled(false);
				panel.add(buttons[i][x]);
			}
		}
		System.out.println("Check 3");

		int shipLen;
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
			if (GridSetup.shipArray[i][2] == 0) {
				int last = shipLen + GridSetup.shipArray[i][0];
				for (int x = GridSetup.shipArray[i][0]; x < last; x++) {

					buttons[GridSetup.shipArray[i][1]][x].setDisabledIcon(GridButton.shipIcon[0]);
				}
			} else if (GridSetup.shipArray[i][2] == 1) {
				int last = shipLen + GridSetup.shipArray[i][1];
				for (int y = GridSetup.shipArray[i][1]; y < last; y++) {
					buttons[y][GridSetup.shipArray[i][0]].setDisabledIcon(GridButton.shipIcon[0]);
				}

			}
		}
		
		System.out.println("Check 4");

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(555, 27, 370, 370);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(new GridLayout(10, 10));

		JList list = new JList();
		list.setBounds(410, 27, 134, 329);
		Border listBorder = BorderFactory.createLineBorder(Color.BLACK);
		list.setBorder(BorderFactory.createCompoundBorder(listBorder, null));
		frame.getContentPane().add(list);

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
		
		System.out.println("Check 5");
		
		for (int i = 0; i < 10; i++) {
			for (int x = 0; x < 10; x++) {

				enemyButtons[i][x] = new GridButton();
				enemyButtons[i][x].setIcon(GridButton.water);
				enemyButtons[i][x].setEnabled(true);
				panel_1.add(enemyButtons[i][x]);
			}
		}
		
		if(GridSetup.cheatTog){
			GridButton.hiddenShip = new ImageIcon(Battleship.class.getResource("/ship.png"));
		}
		
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
	
	public static int[] aiRun(){
		int[] shot = new int[] {0,0};
		
		return shot;
	}
}