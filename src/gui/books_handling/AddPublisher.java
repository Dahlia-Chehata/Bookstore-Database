package gui.books_handling;

import javax.swing.JFrame;

import controller.ManagerAddPublisherController;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class AddPublisher {

	private JFrame frame;
	private ManagerAddPublisherController m;
	private JTextField pub_name;
	private JTextField pub_addr;
	private JTextField pub_tel;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					AddPublisher window = new AddPublisher();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public AddPublisher(ManagerAddPublisherController m) {
		this.m = m;
		initialize();
		this.frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblAddPublisher = new JLabel("Add Publisher");
		lblAddPublisher.setFont(new Font("Times New Roman", Font.BOLD, 19));
		lblAddPublisher.setBounds(10, 21, 172, 37);
		frame.getContentPane().add(lblAddPublisher);

		JLabel lblPublisherName = new JLabel("Publisher Name");
		lblPublisherName.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblPublisherName.setBounds(40, 69, 121, 22);
		frame.getContentPane().add(lblPublisherName);

		pub_name = new JTextField();
		pub_name.setBounds(188, 71, 189, 20);
		frame.getContentPane().add(pub_name);
		pub_name.setColumns(10);

		pub_addr = new JTextField();
		pub_addr.setColumns(10);
		pub_addr.setBounds(188, 111, 189, 20);
		frame.getContentPane().add(pub_addr);

		JLabel lblPublisherAddress = new JLabel("Publisher Address");
		lblPublisherAddress.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblPublisherAddress.setBounds(40, 109, 121, 22);
		frame.getContentPane().add(lblPublisherAddress);

		pub_tel = new JTextField();
		pub_tel.setColumns(10);
		pub_tel.setBounds(188, 149, 189, 20);
		frame.getContentPane().add(pub_tel);

		JLabel lblPublisherTelephone = new JLabel("Publisher Telephone");
		lblPublisherTelephone.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblPublisherTelephone.setBounds(40, 147, 142, 22);
		frame.getContentPane().add(lblPublisherTelephone);

		JLabel lblPublisherHasBeen = new JLabel("Publisher has been added successfully.");
		lblPublisherHasBeen.setForeground(Color.BLUE);
		lblPublisherHasBeen.setBounds(19, 228, 226, 22);
		lblPublisherHasBeen.setVisible(false);
		frame.getContentPane().add(lblPublisherHasBeen);

		JButton btnAddPublisher = new JButton("Add Publisher");
		btnAddPublisher.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				m.add_publisher(pub_name.getText(), pub_addr.getText(), pub_tel.getText());
				lblPublisherHasBeen.setVisible(true);
			}
		});
		btnAddPublisher.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnAddPublisher.setBounds(255, 193, 137, 37);
		frame.getContentPane().add(btnAddPublisher);


	}

}
