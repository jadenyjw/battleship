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

		client.sendTCP(new Packets.Packet00Request());
	}

	public void disconnected(Connection c) {

	}

	public void received(Connection c, Object o) {
		if (o instanceof Packets.Packet01Response) {
			boolean serverAnswer = ((Packets.Packet01Response) o).accepted;
			
			if (serverAnswer == true){
				
				System.out.println("Accepted");
			}
			
	
			else{
				c.close();
				 JOptionPane.showMessageDialog(null, "That host already has a connected user. Please choose a different one.");
			}
			
		}

		if (o instanceof Packets.Packet02Message) {
			Packets.Packet02Message p = (Packets.Packet02Message) o;
			MultiGameClient.textArea.append(p.userName + ": " + p.message + "\n");
			
		}
	}
}
