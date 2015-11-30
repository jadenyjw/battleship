package battleship;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.esotericsoftware.kryonet.Client;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;

import java.awt.CardLayout;
import javax.swing.JList;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.util.List;
import java.awt.event.ActionEvent;

public class Multimenu extends JDialog {
	private JPanel panel;
	private JButton btnHostGame;
	private JButton btnJoinGame;
	private JPanel panel_1;
	private JTextField txtName;
	private JList list;
	private JButton btnRefresh;
	private JLabel lblEnterUsername;

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
		setBounds(100, 100, 300, 300);
		getContentPane().setLayout(new BorderLayout());
		{
			panel = new JPanel();
			getContentPane().add(panel, BorderLayout.SOUTH);
			{
				btnRefresh = new JButton("Refresh");
				btnRefresh.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					}
				});
				panel.add(btnRefresh);
			}
			{
				btnHostGame = new JButton("Host Game");
				btnHostGame.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {

					}
				});
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
				lblEnterUsername = new JLabel("Enter Username:");
				panel_1.add(lblEnterUsername);
			}
			{
				txtName = new JTextField();
				txtName.setHorizontalAlignment(SwingConstants.LEFT);
				panel_1.add(txtName);
				txtName.setColumns(10);
			}
		}
		{

			list = new JList();
			getContentPane().add(list, BorderLayout.CENTER);
            refresh();
		}
	}

	private void refresh() {
		Client client = new Client();
		List<InetAddress> address = client.discoverHosts(1337, 1337);
		System.out.println(address);
		DefaultListModel listModel;
		listModel = new DefaultListModel();
		for (int i = 0; i < address.size(); i++) {
			listModel.addElement(address.get(i));
		}
        list.setModel(listModel);
	}
}
