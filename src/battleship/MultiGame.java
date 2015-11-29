package battleship;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MultiGame {

	private JFrame frame;
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
		GridButton buttons[] = new GridButton[100];
		frame = new JFrame();
		frame.setBounds(100, 100, 970, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLocation(0, 0);
		frame.getContentPane().add(panel);
		panel.setLayout(new GridLayout(10, 10));
		panel.setSize(400,400);
		
		for (int i = 0; i<100; i++){
			buttons[i] = new GridButton();
			panel.add(buttons[i]);
		}
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(554, 0, -300, 561);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(new GridLayout(10, 10));
		panel_1.setSize(400,400);
		
		JList list = new JList();
		list.setBounds(410, 27, 134, 373);
		frame.getContentPane().add(list);
		
		JLabel lblEventLog = new JLabel("Event Log");
		lblEventLog.setBounds(450, 11, 57, 14);
		frame.getContentPane().add(lblEventLog);
		
		textField = new JTextField();
		textField.setBounds(113, 530, 831, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblEnterAMesage = new JLabel("Enter a mesage:");
		lblEnterAMesage.setBounds(10, 533, 93, 14);
		frame.getContentPane().add(lblEnterAMesage);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(10, 411, 934, 108);
		frame.getContentPane().add(textArea);
		
		for (int i = 0; i<100; i++){
			buttons[i] = new GridButton();
			panel_1.add(buttons[i]);
		}
	}
}
