package battleship;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.SwingConstants;

public class Battleship {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Battleship window = new Battleship();
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
	public Battleship() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblBattleship = DefaultComponentFactory.getInstance().createTitle("Battleship X");
		lblBattleship.setFont(new Font("Impact", Font.BOLD, 25));
		lblBattleship.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblBattleship, BorderLayout.NORTH);
		
	}

}
