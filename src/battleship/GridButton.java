package battleship;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class GridButton extends JButton {

	static ImageIcon shipIcon[] = { new ImageIcon(Battleship.class.getResource("/ship.png")),
			new ImageIcon(Battleship.class.getResource("/ship.png")),
			new ImageIcon(Battleship.class.getResource("/ship.png")),
			new ImageIcon(Battleship.class.getResource("/ship.png")),
			new ImageIcon(Battleship.class.getResource("/ship.png")) };
	static ImageIcon hiddenShip = new ImageIcon(Battleship.class.getResource("/water.png"));
	static ImageIcon water = new ImageIcon(Battleship.class.getResource("/water.png"));
	static ImageIcon hit = new ImageIcon(Battleship.class.getResource("/hit.png"));
	static ImageIcon miss = new ImageIcon(Battleship.class.getResource("/miss.png"));

}


