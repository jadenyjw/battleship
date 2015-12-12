package battleship;

import javax.swing.JOptionPane;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import battleship.Packets.Packet01Response;

public class ServerListener extends Listener {

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
			answer.accepted = true;
			c.sendTCP(answer);
		}
		if (o instanceof Packets.Packet02Message) {
			Packets.Packet02Message p = (Packets.Packet02Message) o;

			JOptionPane.showMessageDialog(null, p.message);
		}
	}
}
