package battleship;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.text.DefaultCaret;

import java.awt.Font;

public class MultiGame {

	JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MultiGame window = new MultiGame();
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
	public MultiGame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		GridButton buttons[][] = new GridButton[10][10];
		frame = new JFrame();
		frame.setBounds(100, 100, 970, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setLocation(30, 27);
		frame.getContentPane().add(panel);
		panel.setLayout(new GridLayout(10, 10));
		panel.setSize(370, 370);

		for (int i = 0; i < 10; i++) {
			for (int x = 0; x < 10; x++) {

				buttons[i][x] = new GridButton();
				buttons[i][x].setIcon(GridButton.water);
				buttons[i][x].setDisabledIcon(GridButton.water);
				buttons[i][x].setEnabled(false);
				panel.add(buttons[i][x]);
			}
		}

		int shipLen;
		for (int i = 0; i < 5; i++) {
			switch (i) {
			case 0:
				shipLen = 5;
				break;
			case 1:
				shipLen = 4;
				break;
			case 2:
				shipLen = 3;
				break;
			case 3:
				shipLen = 3;
				break;
			case 4:
				shipLen = 2;
				break;
			default:
				shipLen = 0;

			}
			if (GridSetup.shipArray[i][2] == 0) {
				int last = shipLen + GridSetup.shipArray[i][0];
				for (int x = GridSetup.shipArray[i][0]; x < last; x++) {

					buttons[GridSetup.shipArray[i][1]][x].setDisabledIcon(GridButton.shipIcon[0]);
				}
			} else if (GridSetup.shipArray[i][2] == 1) {
				int last = shipLen + GridSetup.shipArray[i][1];
				for (int y = GridSetup.shipArray[i][1]; y < last; y++) {
					buttons[y][GridSetup.shipArray[i][0]].setDisabledIcon(GridButton.shipIcon[0]);
				}

			}
		}

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(555, 27, 370, 370);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(new GridLayout(10, 10));

		JList list = new JList();
		list.setBounds(410, 27, 134, 373);
		Border listBorder = BorderFactory.createLineBorder(Color.BLACK);
		list.setBorder(BorderFactory.createCompoundBorder(listBorder, null));
		frame.getContentPane().add(list);

		JLabel lblEventLog = new JLabel("Event Log");
		lblEventLog.setBounds(450, 10, 57, 14);
		frame.getContentPane().add(lblEventLog);
		
		JTextArea textArea = new JTextArea();
		JScrollPane scroll = new JScrollPane (textArea);
		DefaultCaret caret = (DefaultCaret)textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		textArea.setEditable(false);
		scroll.setBounds(10, 411, 934, 108);
		frame.getContentPane().add(scroll);
	    
		
		textField = new JTextField();
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!(textField.getText().trim().equals(""))){
				textArea.append(Multimenu.userName + ": " + textField.getText() + "\n");
				textField.setText("");
				}
				
			}
		});
		textField.setBounds(113, 530, 732, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JLabel lblEnterAMesage = new JLabel("Enter a mesage:");
		lblEnterAMesage.setBounds(10, 533, 93, 14);
		frame.getContentPane().add(lblEnterAMesage);

		

		JButton btnNewButton = new JButton("Send");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton.setBounds(855, 529, 89, 23);
		frame.getContentPane().add(btnNewButton);

		JLabel lblABC = new JLabel(
				"A         B         C           D          E          F         G          H           I           J");
		lblABC.setBounds(45, 10, 367, 14);
		frame.getContentPane().add(lblABC);

		JLabel label = new JLabel(
				"A          B          C           D          E          F         G          H           I           J");
		label.setBounds(565, 10, 367, 14);
		frame.getContentPane().add(label);

		JLabel label_1 = new JLabel(
				"<html>1<br><br>2<br><br>3<br><br>4<br><br>5<br><br>6<br><br>7<br><br>8<br><br>9<br><br>10</html>");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		label_1.setVerticalAlignment(SwingConstants.TOP);
		label_1.setBounds(10, 28, 46, 370);
		frame.getContentPane().add(label_1);

		JLabel label_2 = new JLabel(
				"<html>1<br><br>2<br><br>3<br><br>4<br><br>5<br><br>6<br><br>7<br><br>8<br><br>9<br><br>10</html>");
		label_2.setVerticalAlignment(SwingConstants.TOP);
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		label_2.setBounds(935, 27, 46, 370);
		frame.getContentPane().add(label_2);

		for (int i = 0; i < 10; i++) {
			for (int x = 0; x < 10; x++) {

				buttons[i][x] = new GridButton();
				buttons[i][x].setIcon(GridButton.water);
				buttons[i][x].setEnabled(true);
				panel_1.add(buttons[i][x]);
			}
		}
		DefaultListModel listModel;
		listModel = new DefaultListModel();
		list.setModel(listModel);
		textArea.append(">> Welcome " + Multimenu.userName + ".\n");
		textArea.append(">> Now waiting for a connection from another player. " + "\n");
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		textArea.setBorder(BorderFactory.createCompoundBorder(border, 
		          BorderFactory.createEmptyBorder(5, 5, 5, 5)));
	}
}
