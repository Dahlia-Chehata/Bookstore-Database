package gui.user_handling;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

import controller.GuiController;

import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class LoginPage {

	GuiController g = new GuiController();

	private JFrame frame;
	private JTextField email;
	private JPasswordField password;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					LoginPage window = new LoginPage();
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
	public LoginPage() {
		initialize();
		this.frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 500, 466);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		email = new JTextField();
		email.setBounds(143, 163, 183, 26);
		frame.getContentPane().add(email);
		email.setColumns(10);

		JLabel error_label = new JLabel("The email address is not signed up or the password isn't correct.");
		error_label.setForeground(Color.RED);
		error_label.setBounds(34, 381, 335, 14);
		error_label.setVisible(false);
		frame.getContentPane().add(error_label);

		JLabel lblUsername = new JLabel("Email Address");
		lblUsername.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblUsername.setBounds(29, 155, 128, 34);
		frame.getContentPane().add(lblUsername);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblPassword.setBounds(29, 219, 128, 34);
		frame.getContentPane().add(lblPassword);

		JButton btn_login = new JButton("Log In");
		btn_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String email_address = email.getText();
				String user_password = password.getText();
				String[] login_information = null;
				login_information[0] = email_address;
				login_information[1] = user_password;
				if (!g.set_login_information(login_information)) { //user not found
					error_label.setVisible(true);
					System.out.println("ERROR!");
				};

			}
		});
		btn_login.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btn_login.setBounds(263, 301, 106, 40);
		frame.getContentPane().add(btn_login);

		JLabel lblLogInTo = new JLabel("Log In");
		lblLogInTo.setVerticalAlignment(SwingConstants.TOP);
		lblLogInTo.setFont(new Font("Times New Roman", Font.BOLD, 43));
		lblLogInTo.setBounds(104, 23, 134, 65);
		frame.getContentPane().add(lblLogInTo);

		password = new JPasswordField();
		password.setBounds(143, 227, 183, 26);
		frame.getContentPane().add(password);


	}




}
