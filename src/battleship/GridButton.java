package battleship;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class GridButton extends JButton{
  ImageIcon hit,miss,neutral;
  byte value=0;
  /*
   * 0: Miss
   * 1: Hit
   * 2: Neutral
   */
  public GridButton(){
	
  }
}
