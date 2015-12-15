package battleship;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;

import java.awt.CardLayout;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.awt.event.ActionEvent;

public class MultiMenu extends JDialog {
	private JPanel panel;
	private JButton btnHostGame;
	private JButton btnJoinGame;
	private JPanel panel_1;
	private JTextField txtName;
	private JList<InetAddress> list;
	private JButton btnRefresh;
	private JLabel lblEnterUsername;
	public static String userName;
	public static String ipAddress;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			MultiMenu dialog = new MultiMenu();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Create the dialog.
	 */
	public MultiMenu() {
		setBounds(100, 100, 400, 300);
		getContentPane().setLayout(new BorderLayout());
		{
			panel = new JPanel();
			getContentPane().add(panel, BorderLayout.SOUTH);
			{
				btnRefresh = new JButton("Refresh");
				btnRefresh.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						refresh();
					}
				});
				panel.add(btnRefresh);
			}
			{
				btnHostGame = new JButton("Host Game");
				btnHostGame.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						userName = txtName.getText();
						if (!(userName.trim().length() > 15 || userName.trim().length() < 4)) {

							Battleship.referer = "host";

							serve();

						} else
							JOptionPane.showMessageDialog(null, "Please enter a username between 4 to 15 characters");
					}
				});
				panel.add(btnHostGame);
			}
			{
				btnJoinGame = new JButton("Join Game");
				btnJoinGame.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						userName = txtName.getText();
						if (!(userName.trim().length() > 15 || userName.trim().length() < 4)) {

							Battleship.referer = "client";
							join();

						} else
							JOptionPane.showMessageDialog(null, "Please enter a username between 4 to 15 characters");

					}
				});
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
				txtName.setColumns(15);
			}
		}
		{

			list = new JList<InetAddress>();
			getContentPane().add(list, BorderLayout.CENTER);
			refresh();
		}
	}

	public void refresh() {
		Client client = new Client();
		List<InetAddress> address = client.discoverHosts(1337, 1337);

		DefaultListModel<InetAddress> listModel;
		listModel = new DefaultListModel<InetAddress>();

		for (int i = 0; i < address.size(); i++) {
			listModel.addElement(address.get(i));
		}
		list.setModel(listModel);
		String jaden = "204.197.182.51";
		try {
			listModel.addElement(InetAddress.getByName(jaden));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

	}

	public void serve() {
		GridSetup newWindow = new GridSetup();
		newWindow.frame.setVisible(true);
		dispose();
	}

	public void join() {
		if (list.isSelectionEmpty() == false) {
			ipAddress = String.valueOf(list.getSelectedValue()).substring(1);
			GridSetup newClient = new GridSetup();
			newClient.frame.setVisible(true);
			dispose();
		} else {
			JOptionPane.showMessageDialog(null, "Please select a host to join.");
		}

	}
}
