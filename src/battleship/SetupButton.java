package battleship;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import sun.applet.Main;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SetupButton extends JButton{
  
  static ImageIcon shipIcon[] = {new ImageIcon(Battleship.class.getResource("/shipA.png"))
		  						, new ImageIcon(Battleship.class.getResource("/shipB.png"))
		  						, new ImageIcon(Battleship.class.getResource("/shipC.png"))
		  						, new ImageIcon(Battleship.class.getResource("/shipS.png"))
		  						, new ImageIcon(Battleship.class.getResource("/shipP.png"))};
  static ImageIcon water = new ImageIcon(Battleship.class.getResource("/water.png"));
 
	
}
