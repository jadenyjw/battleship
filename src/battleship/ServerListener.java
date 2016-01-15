package battleship;

import java.awt.Color;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.apache.http.client.ClientProtocolException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import battleship.Packets.Packet01Response;

public class ServerListener extends Listener {
	public static boolean uniqueConnection = true; // allows only 1 connection
													// per host
	public static boolean gameDone = false; // determines if the game has been
											// finished
	public static String opponentName;

	public ServerListener() {

	}

	public void connected(Connection c) {

	}

	// Disconnection handling
	public void disconnected(Connection c) {
		MultiGameHost.textArea.append(">> The other player has disconnected.\n");
		MultiGameHost.disableButtons();
	}

	public void received(Connection c, Object o) {
		//When server receives a request to join
		if (o instanceof Packets.Packet00Request) {
			Packet01Response answer = new Packets.Packet01Response();
			//Determine if the server already has an existing connection
			if (uniqueConnection) {
				answer.accepted = true;
				answer.name = MultiMenu.userName;
				//accept the connection
				c.sendTCP(answer);
				MultiGameHost.textArea.append((">> " + ((Packets.Packet00Request) o).clientName
						+ " has joined your game.\n>> You will go first.\n"));
				opponentName = ((Packets.Packet00Request) o).clientName;
				MultiGameHost.lblYourTurn.setText("Your Turn");
				MultiGameHost.frame.toFront();
				uniqueConnection = false;
				//enable input for server
				for (int i = 0; i < 10; i++) {
					for (int x = 0; x < 10; x++) {
						MultiGameHost.enemyButtons[i][x].setEnabled(true);
					}
				}
			} else {
				//deny the connection
				answer.accepted = false;
				c.sendTCP(answer);
			}

		}
		//when server receives a chat message
		if (o instanceof Packets.Packet02Message) {
			//display it on screen
			Packets.Packet02Message p = (Packets.Packet02Message) o;
			MultiGameHost.textArea.append(p.userName + ": " + p.message + "\n");

		}
		//when server receives a coordinate to hit
		if (o instanceof Packets.Packet03Coords) {

			Packets.Packet03Coords p = (Packets.Packet03Coords) o;
			Packets.Packet04Hit hitPacket = new Packets.Packet04Hit();
			hitPacket.x = p.x;
			hitPacket.y = p.y;
			//check whether if there is an existing ship on that coordinate
			if (MultiGameHost.buttons[p.x][p.y].getDisabledIcon() == GridButton.shipIcon) {
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
			//update server gui
			MultiGameHost.lblYourTurn.setText("Your Turn");
			MultiGameHost.lblYourTurn.setForeground(Color.decode("#40df7b"));
			c.sendTCP(hitPacket);
			int count = 0;
			//checks to see if all ships are destroyed
			for (int i = 0; i < 10; i++) {
				for (int x = 0; x < 10; x++) {
					if (MultiGameHost.buttons[i][x].getDisabledIcon() == GridButton.hit) {
						count++;
					}

				}
			}
			//if yes, finish the game
			if (count == 17) {
				Packets.Packet05Victory victoryPacket = new Packets.Packet05Victory();
				victoryPacket.victory = true;
				c.sendTCP(victoryPacket);
				gameDone = true;
				MultiGameHost.lblYourTurn.setText("");
				JOptionPane.showMessageDialog(null, "You lost!");
				try {
					HTTPHandler.UpdateSite(opponentName + " won against " + MultiMenu.userName);
				} catch (ClientProtocolException e) {
					JOptionPane.showMessageDialog(null, "An error occurred updating the website.");
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "An error occurred updating the website.");
				}

			}//if not, continue
			if (gameDone == false) {
				MultiGameHost.reEnableButtons();
			}

		}
		//if server receives a response for the corresponding coordinate
		if (o instanceof Packets.Packet04Hit) {
			Packets.Packet04Hit p = (Packets.Packet04Hit) o;
			MultiGameHost.enemyButtons[p.x][p.y].setEnabled(false);
            //update gui according to the response
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
		//if server has won
		if (o instanceof Packets.Packet05Victory) {
			Packets.Packet05Victory p = (Packets.Packet05Victory) o;
			if (p.victory) {
				gameDone = true;
				MultiGameHost.disableButtons();
				//send all the ships that opponent missed
				for (int i = 0; i < 10; i++) {
					for (int x = 0; x < 10; x++) {
						if (MultiGameHost.buttons[i][x].getDisabledIcon() == GridButton.shipIcon) {
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
		//when received, update current board to show missed ships
		if (o instanceof Packets.Packet06Missed) {
			Packets.Packet06Missed p = (Packets.Packet06Missed) o;
			MultiGameHost.enemyButtons[p.x][p.y].setDisabledIcon(GridButton.shipIcon);
		}
	}

}
