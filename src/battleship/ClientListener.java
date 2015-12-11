package battleship;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class ClientListener extends Listener {
	private Client client;

	public void init(Client client) {
		this.client = client;
	}

	public void connected(Connection c) {
		System.out.println("connected");
		Packets.Packet01Message firstMessage = new Packets.Packet01Message();
		firstMessage.message = "Hello server";
		client.sendTCP(firstMessage);
	}

	public void disconnected(Connection c) {
		System.out.println("disconnected");
	}

	public void received(Connection c, Object o) {
		if (o instanceof Packets.Packet01Message) {
			Packets.Packet01Message p = (Packets.Packet01Message) o;
			System.out.println(p.message);
		}
	}
}
