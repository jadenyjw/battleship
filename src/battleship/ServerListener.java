package battleship;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import battleship.Packets.Packet01Response;

public class ServerListener extends Listener {
	boolean uniqueConnection = true;
	boolean gameDone = false;

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
				MultiGameHost.textArea.append(
						(">> " +((Packets.Packet00Request) o).clientName + " has joined your game.\n>> You will go first.\n"));
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
			System.out.println(p.x + " " + p.y);
			Packets.Packet04Hit hitPacket = new Packets.Packet04Hit();
			hitPacket.x = p.x;
			hitPacket.y = p.y;
			if (MultiGameHost.buttons[p.x][p.y].getDisabledIcon() == GridButton.shipIcon[0]) {

				MultiGameHost.buttons[p.x][p.y].setDisabledIcon(GridButton.hit);
				hitPacket.isHit = true;
			} else {
				MultiGameHost.buttons[p.x][p.y].setDisabledIcon(GridButton.miss);
				hitPacket.isHit = false;
			}
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
				JOptionPane.showMessageDialog(null, "You lost!");

			}
			if (gameDone == false){
			MultiGameHost.reEnableButtons();
			}

		}
		if (o instanceof Packets.Packet04Hit) {
			Packets.Packet04Hit p = (Packets.Packet04Hit) o;
			MultiGameHost.enemyButtons[p.x][p.y].setEnabled(false);
			if (p.isHit == true) {
				MultiGameHost.enemyButtons[p.x][p.y].setDisabledIcon(GridButton.hit);

			} else {
				MultiGameHost.enemyButtons[p.x][p.y].setDisabledIcon(GridButton.miss);
			}

		}
		if (o instanceof Packets.Packet05Victory) {
			Packets.Packet05Victory p = (Packets.Packet05Victory) o;
			if (p.victory) {
				gameDone = true;
				MultiGameHost.disableButtons();
				JOptionPane.showMessageDialog(null, "You won!");
				
			}

		}
	}

}
