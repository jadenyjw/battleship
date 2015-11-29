package battleship;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.BoxLayout;
import java.awt.CardLayout;

public class Multimenu extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JButton btnNewButton;
	private JButton btnNewButton_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Multimenu dialog = new Multimenu();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Multimenu() {
		setBounds(100, 100, 229, 83);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			btnNewButton = new JButton("Host Game");
			btnNewButton.setVerticalAlignment(SwingConstants.BOTTOM);
		}
		{
			btnNewButton_1 = new JButton("New Game");
			btnNewButton_1.setVerticalAlignment(SwingConstants.BOTTOM);
		}
		contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		contentPanel.add(btnNewButton);
		contentPanel.add(btnNewButton_1);
	}

}
