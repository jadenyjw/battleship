package battleship;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class GridButton extends JButton{
  ImageIcon hit,miss,neutral;
  byte value=0;
  /*
   * 0: Not Shot
   * 1: Miss
   * 2: Hit
   */
  public GridButton(){
	
  }
}
