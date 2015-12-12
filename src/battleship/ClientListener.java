package battleship;

import javax.swing.JOptionPane;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class ClientListener extends Listener {
	private Client client;

	public void init(Client client) {
		this.client = client;
	}

	public void connected(Connection c) {
		Packets.Packet00Request packet = new Packets.Packet00Request();
		packet.clientName = MultiMenu.userName;
		client.sendTCP(packet);
	}

	public void disconnected(Connection c) {

	}

	public void received(Connection c, Object o) {
		if (o instanceof Packets.Packet01Response) {
			boolean serverAnswer = ((Packets.Packet01Response) o).accepted;
			
			if (serverAnswer == true){
				MultiGameClient.textArea.append(">> You have successfully connected. Server is now rolling to decide whos go first.\n");
			}
			
	
			else{
				c.close();
				 JOptionPane.showMessageDialog(null, "That host already has an active game. Please choose a different one.");
			}
			
		}

		if (o instanceof Packets.Packet02Message) {
			Packets.Packet02Message p = (Packets.Packet02Message) o;
			MultiGameClient.textArea.append(p.userName + ": " + p.message + "\n");
			
		}
	}
}
