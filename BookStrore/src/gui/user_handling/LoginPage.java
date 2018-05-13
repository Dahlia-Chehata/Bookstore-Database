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

public class LoginPage {

	GuiController g = new GuiController();

	private JFrame frame;
	private JTextField username;
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
		frame.setBounds(100, 100, 481, 466);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		username = new JTextField();
		username.setBounds(196, 160, 183, 26);
		frame.getContentPane().add(username);
		username.setColumns(10);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblUsername.setBounds(81, 155, 76, 34);
		frame.getContentPane().add(lblUsername);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblPassword.setBounds(81, 219, 76, 34);
		frame.getContentPane().add(lblPassword);

		JButton btn_login = new JButton("Log In");
		btn_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user_name = username.getText();
				String user_password = password.getText();
				String[] login_information = null;
				login_information[0] = user_name;
				login_information[1] = user_password;
				g.set_login_information(login_information);

			}
		});
		btn_login.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btn_login.setBounds(175, 309, 106, 40);
		frame.getContentPane().add(btn_login);

		JLabel lblLogInTo = new JLabel("Log In");
		lblLogInTo.setVerticalAlignment(SwingConstants.TOP);
		lblLogInTo.setFont(new Font("Times New Roman", Font.BOLD, 43));
		lblLogInTo.setBounds(157, 23, 134, 65);
		frame.getContentPane().add(lblLogInTo);

		password = new JPasswordField();
		password.setBounds(196, 224, 183, 26);
		frame.getContentPane().add(password);
	}
}
