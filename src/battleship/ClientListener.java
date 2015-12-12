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
				MultiGameClient.textArea.append(">> You have successfully connected. Server will go first.\n");
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
		
		if (o instanceof Packets.Packet03Coords) {
			Packets.Packet03Coords p = (Packets.Packet03Coords) o;
			System.out.println(p.x + " " + p.y);
			Packets.Packet04Hit hitPacket = new Packets.Packet04Hit();
			hitPacket.x = p.x;
			hitPacket.y = p.y;
		    if (MultiGameClient.buttons[p.x][p.y].getDisabledIcon()== GridButton.shipIcon[0]){
		    	
		    	hitPacket.isHit = true;
		    }
		    else{
		    	hitPacket.isHit = false;
		    }
		    client.sendTCP(hitPacket);
		    MultiGameClient.reEnableButtons();
		    
			
		}
		
		if (o instanceof Packets.Packet04Hit) {
			Packets.Packet04Hit p = (Packets.Packet04Hit) o;
			MultiGameClient.enemyButtons[p.x][p.y].setEnabled(false);
			if (p.isHit == true){
				   
				   MultiGameClient.enemyButtons[p.x][p.y].setDisabledIcon(GridButton.hit);
			   }
			   else{
				   MultiGameClient.enemyButtons[p.x][p.y].setDisabledIcon(GridButton.miss);
			   }
			   
			
		}
	}
}
