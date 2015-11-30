package battleship;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.BoxLayout;
import java.awt.CardLayout;

public class SingleGame extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JFrame frame;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SingleGame gameWindow = new SingleGame();
			gameWindow.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			gameWindow.frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SingleGame() {
		frame = new JFrame("Battleship Game");
		frame.setBounds(100, 100, 537, 389);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.SOUTH);
		setBounds(100, 100, 1200, 600);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
	}

}
