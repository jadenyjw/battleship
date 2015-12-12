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
			if (uniqueConnection){
			answer.accepted = true;
			c.sendTCP(answer);
			uniqueConnection = false;
			}
			else{
				answer.accepted = false;
				c.sendTCP(answer);
			}
			
		}
		if (o instanceof Packets.Packet02Message) {
			Packets.Packet02Message p = (Packets.Packet02Message) o;
			MultiGameHost.textArea.append(p.userName + ": " + p.message + "\n");
			
		}
	}
	
}
