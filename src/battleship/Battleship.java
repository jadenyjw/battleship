package battleship;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.ImageIcon;
<<<<<<< HEAD
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
=======
>>>>>>> origin/master
import javax.swing.JLayeredPane;
import java.awt.Color;


public class Battleship {

 private JFrame frame;

 /**
  * Launch the application.
  */
 public static void main(String[] args) {
  EventQueue.invokeLater(new Runnable() {
   public void run() {
    try {
     Battleship window = new Battleship();
     window.frame.setVisible(true);
    } catch (Exception e) {
     e.printStackTrace();
    }
   }
  });
 }

 /**
  * Create the application.
  */
 public Battleship() {
  initMain();
 }//End Battleship

 /**
  * Initialize the contents of the main menu.
  */
 private void initMain() {
  frame = new JFrame("Battleship X");
  frame.setBounds(100, 100, 537, 389);
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  
  JPanel panel_1 = new JPanel();
  frame.getContentPane().add(panel_1, BorderLayout.SOUTH);
  
  JButton btnQuick = new JButton("Quick Match");
  btnQuick.addActionListener(new ActionListener() {
   public void actionPerformed(ActionEvent e) {
	   initQuick();
   }
  });
  panel_1.add(btnQuick);
  
  JButton btnNewButton_1 = new JButton("Multiplayer");
  btnNewButton_1.addActionListener(new ActionListener() {
   public void actionPerformed(ActionEvent e) {
     //Initialize Multiplayer
	   Multimenu newWindow = new Multimenu();
	   newWindow.setVisible(true);
   }
  });
  panel_1.add(btnNewButton_1);
  
  JButton btnNewButton_2 = new JButton("Help");
  btnNewButton_2.addActionListener(new ActionListener() {
   public void actionPerformed(ActionEvent e) {
    JOptionPane.showMessageDialog(frame, "Rules can be found documented online.\nIf you are experiencing errors with "
                                    + "network mode, try allowing this program through your firewall.");
   }
  });
  panel_1.add(btnNewButton_2);
  
  JButton btnNewButton_3 = new JButton("About");
  btnNewButton_3.addActionListener(new ActionListener() {
   public void actionPerformed(ActionEvent e) {
    JOptionPane.showMessageDialog(frame, "This is the standard Battleship game.\nCreated by Jaden Wang and Yuting Liu "
                                    + "for ICS3U1.\nThis project can be tracked and bugs can be reported at "
                                    + "https://github.com/yuting9/battleship");
   }
  });
  panel_1.add(btnNewButton_3);
  
  JButton btnExit = new JButton("Exit");
  btnExit.addActionListener(new ActionListener() {
	  public void actionPerformed(ActionEvent e) {
		  frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	  }
  });
  panel_1.add(btnExit);
  
  JLayeredPane layeredPane = new JLayeredPane();
  frame.getContentPane().add(layeredPane, BorderLayout.CENTER);
  layeredPane.setLayout(null);
  
  
  JLabel lblNewJgoodiesTitle = DefaultComponentFactory.getInstance().createTitle("Battleship X Pro Limited Edition 2");
  lblNewJgoodiesTitle.setForeground(Color.CYAN);
  lblNewJgoodiesTitle.setFont(new Font("Impact", Font.PLAIN, 25));
  lblNewJgoodiesTitle.setBounds(88, 11, 364, 55);
  layeredPane.setLayer(lblNewJgoodiesTitle, 1);
  layeredPane.add(lblNewJgoodiesTitle);
  
  JLabel lblNewLabel = new JLabel("");
  lblNewLabel.setBounds(-92, 0, 620, 349);
  lblNewLabel.setIcon(new ImageIcon("img\\battleship.jpg"));
  layeredPane.add(lblNewLabel);
  
 }//End initMain
 
 private void initQuick(){	  
	 
 }//End initQuick()
}//End Class
