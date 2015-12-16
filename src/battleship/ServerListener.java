package battleship;

import java.awt.Color;

import javax.swing.JOptionPane;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import battleship.Packets.Packet01Response;

public class ServerListener extends Listener {
	public static boolean uniqueConnection = true;
	public static boolean gameDone = false;

	public ServerListener() {

	}

	public void connected(Connection c) {

	}

	public void disconnected(Connection c) {
		MultiGameHost.textArea.append(">> The other player has disconnected.\n");
		MultiGameHost.disableButtons();
	}

	public void received(Connection c, Object o) {
		if (o instanceof Packets.Packet00Request) {
			Packet01Response answer = new Packets.Packet01Response();
			if (uniqueConnection) {
				answer.accepted = true;
				c.sendTCP(answer);
				MultiGameHost.textArea.append((">> " + ((Packets.Packet00Request) o).clientName
						+ " has joined your game.\n>> You will go first.\n"));
				MultiGameHost.lblYourTurn.setText("Your Turn");
				MultiGameHost.frame.toFront();
				uniqueConnection = false;
				for (int i = 0; i < 10; i++) {
					for (int x = 0; x < 10; x++) {
						MultiGameHost.enemyButtons[i][x].setEnabled(true);
					}
				}
			} else {
				answer.accepted = false;
				c.sendTCP(answer);
			}

		}
		if (o instanceof Packets.Packet02Message) {
			Packets.Packet02Message p = (Packets.Packet02Message) o;
			MultiGameHost.textArea.append(p.userName + ": " + p.message + "\n");

		}
		if (o instanceof Packets.Packet03Coords) {

			Packets.Packet03Coords p = (Packets.Packet03Coords) o;
			Packets.Packet04Hit hitPacket = new Packets.Packet04Hit();
			hitPacket.x = p.x;
			hitPacket.y = p.y;
			if (MultiGameHost.buttons[p.x][p.y].getDisabledIcon() == GridButton.shipIcon[0]) {
				MultiGameHost.buttons[p.x][p.y].setDisabledIcon(GridButton.hit);
				MultiGameHost.listModel
						.addElement("Enemy hit: " + Character.toString((char) ('A' + p.y)) + "" + (p.x + 1));
				MultiGameHost.scrollList();
				hitPacket.isHit = true;
			} else {
				MultiGameHost.buttons[p.x][p.y].setDisabledIcon(GridButton.miss);
				MultiGameHost.listModel
						.addElement("Enemy missed: " + Character.toString((char) ('A' + p.y)) + "" + (p.x + 1));
				MultiGameHost.scrollList();
				hitPacket.isHit = false;

			}
			MultiGameHost.lblYourTurn.setText("Your Turn");
			MultiGameHost.lblYourTurn.setForeground(Color.decode("#40df7b"));
			c.sendTCP(hitPacket);
			int count = 0;
			for (int i = 0; i < 10; i++) {
				for (int x = 0; x < 10; x++) {
					if (MultiGameHost.buttons[i][x].getDisabledIcon() == GridButton.hit) {
						count++;
					}

				}
			}
			if (count == 17) {
				Packets.Packet05Victory victoryPacket = new Packets.Packet05Victory();
				victoryPacket.victory = true;
				c.sendTCP(victoryPacket);
				gameDone = true;
				MultiGameHost.lblYourTurn.setText("");
				JOptionPane.showMessageDialog(null, "You lost!");

			}
			if (gameDone == false) {
				MultiGameHost.reEnableButtons();
			}

		}
		if (o instanceof Packets.Packet04Hit) {
			Packets.Packet04Hit p = (Packets.Packet04Hit) o;
			MultiGameHost.enemyButtons[p.x][p.y].setEnabled(false);

			if (p.isHit == true) {
				MultiGameHost.enemyButtons[p.x][p.y].setDisabledIcon(GridButton.hit);
				MultiGameHost.listModel
						.addElement("You hit: " + Character.toString((char) ('A' + p.y)) + "" + (p.x + 1));
				MultiGameHost.scrollList();

			} else {
				MultiGameHost.enemyButtons[p.x][p.y].setDisabledIcon(GridButton.miss);
				MultiGameHost.listModel
						.addElement("You missed: " + Character.toString((char) ('A' + p.y)) + "" + (p.x + 1));
				MultiGameHost.scrollList();
			}

		}
		if (o instanceof Packets.Packet05Victory) {
			Packets.Packet05Victory p = (Packets.Packet05Victory) o;
			if (p.victory) {
				gameDone = true;
				MultiGameHost.disableButtons();
				for (int i = 0; i < 10; i++) {
					for (int x = 0; x < 10; x++) {
						if (MultiGameHost.buttons[i][x].getDisabledIcon() == GridButton.shipIcon[0]) {
							Packets.Packet06Missed coordPacket = new Packets.Packet06Missed();
							coordPacket.x = i;
							coordPacket.y = x;

							c.sendTCP(coordPacket);
						}

					}
				}
				MultiGameHost.lblYourTurn.setText("");
				JOptionPane.showMessageDialog(null, "You won!");

			}

		}
		if (o instanceof Packets.Packet06Missed) {
			Packets.Packet06Missed p = (Packets.Packet06Missed) o;
			MultiGameHost.enemyButtons[p.x][p.y].setDisabledIcon(GridButton.shipIcon[0]);
		}
	}

}
