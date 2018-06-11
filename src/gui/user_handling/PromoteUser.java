package gui.user_handling;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;

import HelperClasses.NotFound;
import controller.ManagerPromoteUserController;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PromoteUser {

	private JFrame frame;
	private JTextField textField;
	private ManagerPromoteUserController m;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					PromoteUser window = new PromoteUser();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 * @param managerPromoteUserController
	 */
	public PromoteUser(ManagerPromoteUserController managerPromoteUserController) {
		this.m = managerPromoteUserController;
		initialize();
		this.frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 448, 292);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblUsersEmail = new JLabel("User's Email");
		lblUsersEmail.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblUsersEmail.setBounds(34, 41, 109, 23);
		frame.getContentPane().add(lblUsersEmail);

		textField = new JTextField();
		textField.setBounds(71, 75, 178, 31);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel = new JLabel("The Email you've entered belongs to a user that doesn't exist!");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setBounds(10, 210, 350, 14);
		frame.getContentPane().add(lblNewLabel);
		lblNewLabel.setVisible(false);

		JLabel lblNewLabel1 = new JLabel("You've successfully promoted the user.");
		lblNewLabel1.setForeground(Color.RED);
		lblNewLabel1.setBounds(10, 210, 350, 14);
		frame.getContentPane().add(lblNewLabel1);
		lblNewLabel1.setVisible(false);

		JButton btnPromoteUser = new JButton("Promote User");
		btnPromoteUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblNewLabel.setVisible(false);
				lblNewLabel1.setVisible(false);
				if (!textField.getText().isEmpty()) {
					try {
						if (m.promote(textField.getText())) {
							lblNewLabel1.setVisible(true);
						} else { //error user not found
							lblNewLabel.setVisible(true);
						}
					} catch (NotFound e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnPromoteUser.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnPromoteUser.setBounds(260, 127, 125, 36);
		frame.getContentPane().add(btnPromoteUser);


	}
}
