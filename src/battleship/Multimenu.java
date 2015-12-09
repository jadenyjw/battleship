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
import java.util.List;
import java.awt.event.ActionEvent;

public class Multimenu extends JDialog {
	private JPanel panel;
	private JButton btnHostGame;
	private JButton btnJoinGame;
	private JPanel panel_1;
	private JTextField txtName;
	private JList<InetAddress> list;
	private JButton btnRefresh;
	private JLabel lblEnterUsername;
	private JButton btnLocal;

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
						try {
							serve();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				panel.add(btnHostGame);
			}
			{
				btnJoinGame = new JButton("Join Game");
				btnJoinGame.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							join();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				panel.add(btnJoinGame);
			}
			{
				btnLocal = new JButton("Local");
				btnLocal.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						GridSetup p1Window = new GridSetup();
						p1Window.frame.setVisible(true);
						dispose();
						JOptionPane.showMessageDialog(null, "Player 1 picks first.");
						GridSetup p2Window = new GridSetup();
						p2Window.frame.setVisible(true);
					}
				});
				panel.add(btnLocal);
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

			list = new JList<InetAddress>();
			getContentPane().add(list, BorderLayout.CENTER);
			refresh();
		}
	}

	private void refresh() {
		Client client = new Client();
		List<InetAddress> address = client.discoverHosts(1337, 1337);
		System.out.println(address);
		DefaultListModel<InetAddress> listModel;
		listModel = new DefaultListModel<InetAddress>();
		for (int i = 0; i < address.size(); i++) {
			listModel.addElement(address.get(i));
		}
		list.setModel(listModel);
		
	}

	private void serve() throws IOException {
		Server server = new Server();
		server.start();
		server.bind(1337, 1337);
		//insert ship placing gui and wait for connection.
		GridSetup newWindow = new GridSetup();
		newWindow.frame.setVisible(true);
		dispose();
	}

	private void join() throws IOException {
		if (list.isSelectionEmpty() == false){
		Client client = new Client();
		client.start();
		client.connect(5000, String.valueOf(list.getSelectedValue()).substring(1), 1337, 1337);
		//insert ship placing gui
		GridSetup newClient = new GridSetup();
		newClient.frame.setVisible(true);
		dispose();
		}
		else {
			JOptionPane.showMessageDialog(null, "Please select a host to join.");
			
		}
		
		
		
	}
}
