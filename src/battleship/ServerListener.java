package battleship;

import javax.swing.JOptionPane;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;


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
    
		if (o instanceof Packets.Packet01Message){
			Packets.Packet01Message p = (Packets.Packet01Message) o;
			
			JOptionPane.showMessageDialog(null,  p.message);
		}
	}
}
