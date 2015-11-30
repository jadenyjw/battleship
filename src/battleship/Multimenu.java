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
import javax.swing.JList;

public class Multimenu extends JDialog {
	private JPanel panel;
	private JButton btnHostGame;
	private JButton btnJoinGame;
	private JPanel panel_1;
	private JTextField txtName;
	private JList list;

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
		setBounds(100, 100, 237, 229);
		getContentPane().setLayout(new BorderLayout());
		{
			panel = new JPanel();
			getContentPane().add(panel, BorderLayout.SOUTH);
			{
				btnHostGame = new JButton("Host Game");
				panel.add(btnHostGame);
			}
			{
				btnJoinGame = new JButton("Join Game");
				btnJoinGame.setEnabled(false);
				panel.add(btnJoinGame);
			}
		}
		{
			panel_1 = new JPanel();
			getContentPane().add(panel_1, BorderLayout.NORTH);
			{
				txtName = new JTextField();
				txtName.setText("Name");
				panel_1.add(txtName);
				txtName.setColumns(10);
			}
		}
		{
			list = new JList();
			getContentPane().add(list, BorderLayout.CENTER);
		}
	}

}
