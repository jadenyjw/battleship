package battleship;

import java.awt.Color;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.apache.http.client.ClientProtocolException;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class ClientListener extends Listener {
	// #variable
	private Client client; // Kryonet client object
	public static boolean gameDone = false; // Used to determine if game is finished
	public static String opponentName;
	
	public void init(Client client) {
		this.client = client;
	}

	public void connected(Connection c) {
		// Handshake to server, acknowledging the connection
		Packets.Packet00Request packet = new Packets.Packet00Request();
		packet.clientName = MultiMenu.userName;
		client.sendTCP(packet);
	}

	public void disconnected(Connection c) {
		// Used for disconnection handling
		MultiGameClient.textArea.append(">> The other player has disconnected.\n");
		MultiGameClient.disableButtons();
	}

	public void received(Connection c, Object o) {
		// Handshake packet
		if (o instanceof Packets.Packet01Response) {
			boolean serverAnswer = ((Packets.Packet01Response) o).accepted;

			if (serverAnswer == true) {
				// Determine if server has accepted the connection
				MultiGameClient.textArea.append(">> You have successfully connected. " + ((Packets.Packet01Response) o).name +" will go first.\n");
				opponentName = ((Packets.Packet01Response) o).name;
			}

			else {
				c.close();
				JOptionPane.showMessageDialog(null,
						"That host already has an active game. Please choose a different one.");
			}

		}

		if (o instanceof Packets.Packet02Message) {
			// Chat message packet
			Packets.Packet02Message p = (Packets.Packet02Message) o;
			MultiGameClient.textArea.append(p.userName + ": " + p.message + "\n");

		}

		if (o instanceof Packets.Packet03Coords) {
			// Coordinate packet
			Packets.Packet03Coords p = (Packets.Packet03Coords) o;
			System.out.println(p.x + " " + p.y);
			Packets.Packet04Hit hitPacket = new Packets.Packet04Hit();
			hitPacket.x = p.x;
			hitPacket.y = p.y;
			//Determines whether it was a hit or not
			if (MultiGameClient.buttons[p.x][p.y].getDisabledIcon() == GridButton.shipIcon) {
				MultiGameClient.buttons[p.x][p.y].setDisabledIcon(GridButton.hit);
				MultiGameClient.listModel
				// #string
						.addElement("Enemy hit: " + Character.toString((char) ('A' + p.y)) + "" + (p.x + 1));
				MultiGameClient.scrollList();

				hitPacket.isHit = true;
			} else {
				MultiGameClient.buttons[p.x][p.y].setDisabledIcon(GridButton.miss);
				MultiGameClient.listModel
						.addElement("Enemy missed: " + Character.toString((char) ('A' + p.y)) + "" + (p.x + 1));
				MultiGameClient.scrollList();
				hitPacket.isHit = false;
			}
			MultiGameClient.lblEnemysTurn.setText("Your Turn");
			MultiGameClient.lblEnemysTurn.setForeground(Color.decode("#40df7b"));
			client.sendTCP(hitPacket);
			//Determine amount of ship blocks destroyed
			int count = 0;
			for (int i = 0; i < 10; i++) {
				for (int x = 0; x < 10; x++) {
					if (MultiGameClient.buttons[i][x].getDisabledIcon() == GridButton.hit) {
						count++;
					}

				}
			}
			if (count == 17) {
				Packets.Packet05Victory victoryPacket = new Packets.Packet05Victory();
				victoryPacket.victory = true;
				c.sendTCP(victoryPacket);
				gameDone = true;
				MultiGameClient.lblEnemysTurn.setText("");
				JOptionPane.showMessageDialog(null, "You lost!");
				try {
					HTTPHandler.UpdateSite(opponentName + " won against " + MultiMenu.userName);
				} catch (ClientProtocolException e) {
					JOptionPane.showMessageDialog(null, "An error occurred updating the website.");
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "An error occurred updating the website.");
				}
			}
			if (gameDone == false) {
				MultiGameClient.reEnableButtons();
			}

		}

		if (o instanceof Packets.Packet04Hit) {
			//Hit packet
			Packets.Packet04Hit p = (Packets.Packet04Hit) o;
			MultiGameClient.enemyButtons[p.x][p.y].setEnabled(false);
			if (p.isHit == true) {

				MultiGameClient.enemyButtons[p.x][p.y].setDisabledIcon(GridButton.hit);
				MultiGameClient.listModel
						.addElement("You hit: " + Character.toString((char) ('A' + p.y)) + "" + (p.x + 1));
				MultiGameClient.scrollList();
			} else {
				MultiGameClient.enemyButtons[p.x][p.y].setDisabledIcon(GridButton.miss);
				MultiGameClient.listModel
						.addElement("You missed: " + Character.toString((char) ('A' + p.y)) + "" + (p.x + 1));
				MultiGameClient.scrollList();
			}

		}
		if (o instanceof Packets.Packet05Victory) {
			//Victory Packet
			Packets.Packet05Victory p = (Packets.Packet05Victory) o;
			if (p.victory) {
				gameDone = true;
				MultiGameClient.disableButtons();

				for (int i = 0; i < 10; i++) {
					for (int x = 0; x < 10; x++) {
						if (MultiGameClient.buttons[i][x].getDisabledIcon() == GridButton.shipIcon) {
							Packets.Packet06Missed coordPacket = new Packets.Packet06Missed();
							coordPacket.x = i;
							coordPacket.y = x;

							c.sendTCP(coordPacket);
						}

					}
				}
				MultiGameClient.lblEnemysTurn.setText("");
				JOptionPane.showMessageDialog(null, "You won!");

			}

		}
		if (o instanceof Packets.Packet06Missed) {
			//Used to reveal the board to loser
			Packets.Packet06Missed p = (Packets.Packet06Missed) o;
			MultiGameClient.enemyButtons[p.x][p.y].setDisabledIcon(GridButton.shipIcon);
		}
	}
}
