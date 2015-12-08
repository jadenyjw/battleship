package battleship;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class GridButton extends JButton{
  ImageIcon hit,miss,neutral,own;
  byte value=0;
  /*
   * 0: Empty
   * 1: Miss
   * 2: Hit
   * 3: Own Ship 
   */
  public GridButton(){
	
  }
}
