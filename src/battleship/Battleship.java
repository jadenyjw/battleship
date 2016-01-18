package battleship;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Battleship {
 // #variable
 public static String referer = ""; // Used to determine which button was
          // clicked
 private JFrame frame; // Swing frame for the GUI
 public static ImageIcon img = new ImageIcon(Battleship.class.getResource("/icon.png")); // Imports
                       // background
                       // image

 /**
  * Launch the application.
  */
 // #main
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
 }// End Main

 /**
  * Create the application.
  */
 public Battleship() {
  initMain();
 }// End Battleship

 /**
  * Initialize the contents of the main menu.
  */
 private void initMain() {
  // Initialize and set settings for frame
  frame = new JFrame("Battleship X Pro Limited Edition 2 | Main Menu");
  frame.setBounds(100, 100, 625, 400);
  frame.setResizable(false);
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  frame.setIconImage(img.getImage());
  JPanel panel_1 = new JPanel();
  frame.getContentPane().add(panel_1, BorderLayout.SOUTH);
  // Singleplayer button
  JButton btnQuick = new JButton("Quick Match");
  btnQuick.addActionListener(new ActionListener() {
   public void actionPerformed(ActionEvent e) {
    referer = "single";
    GridSetup newClient = new GridSetup();
    newClient.frame.setVisible(true);
    frame.dispose();
   }
  });
  panel_1.add(btnQuick);
  // Multiplayer Button
  JButton btnNewButton_1 = new JButton("Multiplayer");
  btnNewButton_1.addActionListener(new ActionListener() {
   public void actionPerformed(ActionEvent e) {
    // Initialize Multiplayer
    MultiMenu newWindow = new MultiMenu();

    newWindow.setVisible(true);
    frame.dispose();
   }
  });
  panel_1.add(btnNewButton_1);
  // Help button
  JButton btnNewButton_2 = new JButton("Help");
  btnNewButton_2.addActionListener(new ActionListener() {
   public void actionPerformed(ActionEvent e) {
    JOptionPane.showMessageDialog(frame,
      "Rules can be found documented online.\nIf you are experiencing errors with "
        + "network mode, try allowing this program through your firewall.\n"
        + "If you cannot find other servers, make sure you can establish a TCP/UDP"
        + " connection with the other computer on port 1337.\n"
        + "If you would like to host a server online, contact Jaden Wang.");
   }
  });
  panel_1.add(btnNewButton_2);
  // About button
  JButton btnNewButton_3 = new JButton("About");
  btnNewButton_3.addActionListener(new ActionListener() {
   public void actionPerformed(ActionEvent e) {
    JOptionPane.showMessageDialog(frame,
      "This is the standard Battleship game. Multiplayer scores can be viewed at bit.ly/Dmj9.\nCreated by Jaden Wang and Yuting Liu "
        + "for ICS3U1.\nThis project can be tracked and bugs can be reported at "
        + "https://github.com/jaden71/battleship");
   }
  });
  panel_1.add(btnNewButton_3);
  // Exit button
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
  // Background image
  JLabel lblNewLabel = new JLabel("");
  lblNewLabel.setBounds(0, 0, 629, 349);
  ImageIcon image = new ImageIcon(getClass().getResource("/battleship.jpg"));
  lblNewLabel.setIcon(image);
  layeredPane.add(lblNewLabel);

 }// End initMain
}// End Class
