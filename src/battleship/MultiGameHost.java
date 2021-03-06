package battleship;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.text.DefaultCaret;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

public class MultiGameHost {
	// #variable
	public static JFrame frame; // Swing Jframe object
	private JTextField textField; // Text field for chat

	public static String message; // Chat message

	public static javax.swing.JTextArea textArea; // Text area for chat
	public static GridButton buttons[][] = new GridButton[10][10]; // Own
																	// buttons
	public static GridButton enemyButtons[][] = new GridButton[10][10]; // Enemy
																		// buttons
	public static DefaultListModel<String> listModel; // List model for event
														// log
	public static JList<String> list = new JList<String>(); // List for event
															// log

	public static JLabel lblYourTurn; // Label for turn indication

	int port = 1337; // Port used to host server
	Server server; // Kryonet server
	ServerListener sl; // Kryonet server listener

	public static JPanel ownPanel; // Panel for own buttons
	public static JPanel enemyPanel; // Panel for enemy buttons

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					MultiGameHost window = new MultiGameHost();
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
	public MultiGameHost() {

		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// frame initialization
		frame = new JFrame("Battleship X Pro Limited Edition 2 | Multiplayer");
		frame.setBounds(100, 100, 970, 600);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setIconImage(Battleship.img.getImage());
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			// close msgbox
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (ServerListener.gameDone == false) {
					if (JOptionPane.showConfirmDialog(frame, "Closing this window will end the game. Are you sure?",
							"Really Closing?", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
						ServerListener.gameDone = false;
						ServerListener.uniqueConnection = true;
						server.close();
						frame.dispose();
						MultiMenu newWindow = new MultiMenu();
						newWindow.setVisible(true);

					}
				} else if (ServerListener.gameDone == true) {
					ServerListener.gameDone = false;
					ServerListener.uniqueConnection = true;
					server.close();
					frame.dispose();
					MultiMenu newWindow = new MultiMenu();
					newWindow.setVisible(true);

				}
			}
		});
		frame.getContentPane().setLayout(null);

		JPanel ownPanel = new JPanel();
		ownPanel.setLocation(30, 27);
		frame.getContentPane().add(ownPanel);
		ownPanel.setLayout(new GridLayout(10, 10));
		ownPanel.setSize(370, 370);

		for (int i = 0; i < 10; i++) {
			for (int x = 0; x < 10; x++) {

				buttons[i][x] = new GridButton();
				buttons[i][x].setIcon(GridButton.water);
				buttons[i][x].setDisabledIcon(GridButton.water);
				buttons[i][x].setEnabled(false);
				ownPanel.add(buttons[i][x]);
			}
		}
		// Determine ship length
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

					buttons[GridSetup.shipArray[i][1]][x].setDisabledIcon(GridButton.shipIcon);
				}
			} else if (GridSetup.shipArray[i][2] == 1) {
				int last = shipLen + GridSetup.shipArray[i][1];
				for (int y = GridSetup.shipArray[i][1]; y < last; y++) {
					buttons[y][GridSetup.shipArray[i][0]].setDisabledIcon(GridButton.shipIcon);
				}

			}
		}

		JPanel enemyPanel = new JPanel();
		enemyPanel.setBounds(555, 27, 370, 370);
		frame.getContentPane().add(enemyPanel);
		enemyPanel.setLayout(new GridLayout(10, 10));

		listModel = new DefaultListModel<String>();
		list.setModel(listModel);
		Border listBorder = BorderFactory.createLineBorder(Color.BLACK);
		list.setBorder(BorderFactory.createCompoundBorder(listBorder, null));
		JScrollPane scrollLog = new JScrollPane(list);
		scrollLog.setBounds(410, 27, 134, 345);
		frame.getContentPane().add(scrollLog);

		JLabel lblEventLog = new JLabel("Event Log");
		lblEventLog.setBounds(450, 10, 57, 14);
		frame.getContentPane().add(lblEventLog);

		textArea = new JTextArea();

		JScrollPane scroll = new JScrollPane(textArea);
		DefaultCaret caret = (DefaultCaret) textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		textArea.setEditable(false);
		scroll.setBounds(10, 411, 934, 108);
		frame.getContentPane().add(scroll);

		textField = new JTextField();

		textField.setBounds(113, 530, 732, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JLabel lblEnterAMesage = new JLabel("Enter a message:");
		lblEnterAMesage.setBounds(10, 533, 93, 14);
		frame.getContentPane().add(lblEnterAMesage);

		final JButton btnNewButton = new JButton("Send");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!(textField.getText().trim().equals(""))) {
					textArea.append(MultiMenu.userName + ": " + textField.getText() + "\n");
					Packets.Packet02Message messagePacket = new Packets.Packet02Message();
					messagePacket.userName = MultiMenu.userName;
					messagePacket.message = textField.getText();
					server.sendToAllTCP(messagePacket);
					textField.setText("");

				}
			}
		});
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnNewButton.doClick();

			}
		});
		btnNewButton.setBounds(855, 529, 89, 23);
		frame.getContentPane().add(btnNewButton);

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

		for (int i = 0; i < 10; i++) {
			for (int x = 0; x < 10; x++) {

				enemyButtons[i][x] = new GridButton();
				enemyButtons[i][x].setIcon(GridButton.water);
				enemyButtons[i][x].setDisabledIcon(GridButton.water);
				enemyButtons[i][x].setEnabled(false);
				final int tempX = i;
				final int tempY = x;
				enemyButtons[i][x].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Packets.Packet03Coords coordPacket = new Packets.Packet03Coords();
						coordPacket.x = tempX;
						coordPacket.y = tempY;
						disableButtons();
						lblYourTurn.setText("Enemy's Turn");
						lblYourTurn.setForeground(Color.decode("#df7b40"));
						server.sendToAllTCP(coordPacket);

					}
				});
				enemyPanel.add(enemyButtons[i][x]);
			}
		}

		listModel = new DefaultListModel<String>();
		list.setModel(listModel);
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		Border borderwhite = BorderFactory.createLineBorder(Color.lightGray);
		scroll.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(0, 0, 0, 0)));
		textArea.setBorder(
				BorderFactory.createCompoundBorder(borderwhite, BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		textArea.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		lblYourTurn = new JLabel("");
		lblYourTurn.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblYourTurn.setForeground(Color.decode("#40df7b"));
		lblYourTurn.setHorizontalAlignment(SwingConstants.CENTER);
		lblYourTurn.setBounds(410, 383, 135, 14);
		frame.getContentPane().add(lblYourTurn);
		textArea.append(">> Welcome " + MultiMenu.userName + ".\n");
		hostServer();
		textArea.append(">> Now waiting for a connection from another player. " + "\n");

	}

	// #method
	public void hostServer() {
		// hosts a server
		server = new Server();
		sl = new ServerListener();
		server.addListener(sl);

		try {
			server.bind(port, 1337);

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"There was an error with starting the server. Please restart the program. If the problem persists, change your network rules.");
		}
		registerPackets();
		server.start();
		System.out.println("Started");
		Log.set(Log.LEVEL_TRACE);
	}

	private void registerPackets() {
		// register packets
		Kryo kryo = server.getKryo();
		kryo.register(Packets.Packet00Request.class);
		kryo.register(Packets.Packet01Response.class);
		kryo.register(Packets.Packet02Message.class);
		kryo.register(Packets.Packet03Coords.class);
		kryo.register(Packets.Packet04Hit.class);
		kryo.register(Packets.Packet05Victory.class);
		kryo.register(Packets.Packet06Missed.class);
	}

	public static void reEnableButtons() {
		// enables buttons
		// #conditional #loop
		for (int i = 0; i < 10; i++) {
			for (int x = 0; x < 10; x++) {
				if (MultiGameHost.enemyButtons[i][x].getDisabledIcon() != GridButton.hit
						&& MultiGameHost.enemyButtons[i][x].getDisabledIcon() != GridButton.miss)
					MultiGameHost.enemyButtons[i][x].setEnabled(true);
			}
		}
	}

	public static void disableButtons() {
		// disables buttons
		for (int i = 0; i < 10; i++) {
			for (int x = 0; x < 10; x++) {
				MultiGameHost.enemyButtons[i][x].setEnabled(false);
			}
		}
	}

	public static void scrollList() {
		// scrolls the event log
		int lastIndex = list.getModel().getSize() - 1;
		if (lastIndex >= 0) {
			list.ensureIndexIsVisible(lastIndex);
		}
	}

}
