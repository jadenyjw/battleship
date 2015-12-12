package battleship;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import battleship.Packets.Packet01Response;

public class ServerListener extends Listener {
	boolean uniqueConnection = true;

	public ServerListener() {

	}

	public void connected(Connection c) {
		System.out.println("Connected");
	}

	public void disconnected(Connection c) {
		System.out.println("Disconnected");
	}

	public void received(Connection c, Object o) {
		if (o instanceof Packets.Packet00Request) {
			Packet01Response answer = new Packets.Packet01Response();
			if (uniqueConnection) {
				answer.accepted = true;
				c.sendTCP(answer);
				MultiGameHost.textArea.append(
						((Packets.Packet00Request) o).clientName + " has joined your game.\nYou will go first.");
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
				hitPacket.isHit = true;

				MultiGameHost.buttons[p.x][p.y].setDisabledIcon(GridButton.hit);
			} else {
				hitPacket.isHit = false;
				MultiGameHost.buttons[p.x][p.y].setDisabledIcon(GridButton.miss);
			}
			c.sendTCP(hitPacket);

			MultiGameHost.reEnableButtons();

		}
		if (o instanceof Packets.Packet04Hit) {
			Packets.Packet04Hit p = (Packets.Packet04Hit) o;
			
			if (p.isHit == true) {
				MultiGameHost.enemyButtons[p.x][p.y].setDisabledIcon(GridButton.hit);
			} else {
				MultiGameHost.enemyButtons[p.x][p.y].setDisabledIcon(GridButton.miss);
			}
			int count = 17;
			for (int i = 0; i < 10; i++) {
				for (int x = 0; x < 10; x++) {
					if (MultiGameHost.enemyButtons[i][x].getDisabledIcon() == GridButton.hit) {
						count--;
					}
				}
			}
			if (count == 0) {
				Packets.Packet05Victory victoryPacket = new Packets.Packet05Victory();
				victoryPacket.victoryAchieved = true;
				c.sendTCP(victoryPacket);
				JOptionPane.showMessageDialog(null, "You won!");
				//System.out.println("You won!");
			}

		}
		if (o instanceof Packets.Packet05Victory) {
			Packets.Packet05Victory p = (Packets.Packet05Victory) o;
			if (p.victoryAchieved == true){
				JOptionPane.showMessageDialog(null, "You lost!");
				//System.out.println("You lost!");
			}
		}
	}

}
