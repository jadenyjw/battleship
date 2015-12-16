package battleship;

import java.awt.Color;

import javax.swing.JOptionPane;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class ClientListener extends Listener {
	private Client client;
	public static boolean gameDone = false;

	public void init(Client client) {
		this.client = client;
	}

	public void connected(Connection c) {
		Packets.Packet00Request packet = new Packets.Packet00Request();
		packet.clientName = MultiMenu.userName;
		client.sendTCP(packet);
	}

	public void disconnected(Connection c) {
		MultiGameClient.textArea.append(">> The other player has disconnected.\n");
		MultiGameClient.disableButtons();
	}

	public void received(Connection c, Object o) {
		if (o instanceof Packets.Packet01Response) {
			boolean serverAnswer = ((Packets.Packet01Response) o).accepted;

			if (serverAnswer == true) {
				MultiGameClient.textArea.append(">> You have successfully connected. Server will go first.\n");
			}

			else {
				c.close();
				JOptionPane.showMessageDialog(null,
						"That host already has an active game. Please choose a different one.");
			}

		}

		if (o instanceof Packets.Packet02Message) {
			Packets.Packet02Message p = (Packets.Packet02Message) o;
			MultiGameClient.textArea.append(p.userName + ": " + p.message + "\n");

		}

		if (o instanceof Packets.Packet03Coords) {
			Packets.Packet03Coords p = (Packets.Packet03Coords) o;
			System.out.println(p.x + " " + p.y);
			Packets.Packet04Hit hitPacket = new Packets.Packet04Hit();
			hitPacket.x = p.x;
			hitPacket.y = p.y;
			if (MultiGameClient.buttons[p.x][p.y].getDisabledIcon() == GridButton.shipIcon[0]) {
				MultiGameClient.buttons[p.x][p.y].setDisabledIcon(GridButton.hit);
				MultiGameClient.listModel
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
			}
			if (gameDone == false) {
				MultiGameClient.reEnableButtons();
			}

		}

		if (o instanceof Packets.Packet04Hit) {
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
			Packets.Packet05Victory p = (Packets.Packet05Victory) o;
			if (p.victory) {
				gameDone = true;
				MultiGameClient.disableButtons();

				for (int i = 0; i < 10; i++) {
					for (int x = 0; x < 10; x++) {
						if (MultiGameClient.buttons[i][x].getDisabledIcon() == GridButton.shipIcon[0]) {
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
			Packets.Packet06Missed p = (Packets.Packet06Missed) o;
			MultiGameClient.enemyButtons[p.x][p.y].setDisabledIcon(GridButton.shipIcon[0]);
		}
	}
}
