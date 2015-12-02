package battleship;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Font;

public class GridSetup extends JDialog {

	JFrame frame;
	private JPanel panel;
	private JPanel ships;
	private JTextField textField;
	private JButton battleship;
	private JButton carrier;
	private JButton sub;
	private JButton frigate;
	private JButton patrol;

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
		GridButton buttons[] = new GridButton[100];
		GridButton ship[] = new GridButton[5];
		frame = new JFrame();
		frame.setBounds(100, 100, 550, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLocation(30, 27);
		frame.getContentPane().add(panel);
		panel.setLayout(new GridLayout(10, 10));
		panel.setSize(370,370);
		
		for (int i = 0; i<100; i++){
		
		
			buttons[i] = new GridButton();
			buttons[i].setEnabled(true);
			panel.add(buttons[i]);
		}
		//String[] gridNames = ["Battleship","Carrier","Destroyer","Submarine","PT Boat"]
		JPanel ships = new JPanel();
		ships.setLocation(425,27);
		frame.getContentPane().add(ships);
		ships.setLayout(new GridLayout(5,1));
		ships.setSize(100,350);
		
		for (int i = 0; i<5;i++){
			ship[i] = new GridButton();
			buttons[i].setEnabled(true);
			ships.add(ship[i]);
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
	}
}