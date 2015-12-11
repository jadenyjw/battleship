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

public class AISetup extends JDialog {

	JFrame frame;
	JLabel image;
	
	public static SetupButton aiButtons[][] = new SetupButton[10][10];
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AISetup window = new AISetup();
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
	public AISetup() {
		initialize();
		System.out.println(Battleship.referer);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

	}


	public static boolean check(byte shipLen, String shipOrient, int yCoord, int xCoord, int shipArray[][],
			int shipNum) {

		if (shipOrient.equals("Horizontal")) {

			if (xCoord < 11 - shipLen) {
				int last = shipLen + xCoord;

				for (int t = xCoord; t < last; t++) {
					if (aiButtons[yCoord][t].getIcon() != SetupButton.shipIcon[shipNum] && aiButtons[yCoord][t].getIcon() != SetupButton.water) {
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
					if (aiButtons[t][xCoord].getIcon() != SetupButton.shipIcon[shipNum] && aiButtons[t][xCoord].getIcon() != SetupButton.water) {
						return false;
					}
				}

				return true;
			}

			else
				return false;
		}
	}

}