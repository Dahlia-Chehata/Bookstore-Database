package gui.user_handling;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.TextArea;
import javax.swing.JButton;
import javax.swing.JPasswordField;

public class SignupPage {

	private JFrame frame;
	private JTextField username;
	private JTextField first_name;
	private JTextField last_name;
	private JTextField email;
	private JTextField phone_number;
	private JTextField shipping_address;
	private JLabel lblNewLabel;
	private JButton btn_signup;
	private JLabel label;
	private JLabel label_1;
	private JLabel label_2;
	private JLabel label_3;
	private JLabel label_4;
	private JLabel label_5;
	private JLabel label_6;
	private JLabel label_7;
	private JLabel lblRequiredFields;
	private JPasswordField password;
	private JPasswordField password_repeat;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					SignupPage window = new SignupPage();
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
	public SignupPage() {
		initialize();
		this.frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 491, 528);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		label = new JLabel("*");
		label.setForeground(Color.RED);
		label.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		label.setBounds(98, 73, 13, 14);
		frame.getContentPane().add(label);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblUsername.setBounds(25, 202, 80, 28);
		frame.getContentPane().add(lblUsername);

		username = new JTextField();
		username.setBounds(50, 233, 152, 23);
		frame.getContentPane().add(username);
		username.setColumns(10);

		JLabel lblRepeatPassword = new JLabel("Repeat password");
		lblRepeatPassword.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblRepeatPassword.setBounds(266, 270, 145, 28);
		frame.getContentPane().add(lblRepeatPassword);

		JLabel lblPassword = new JLabel("Enter your password");
		lblPassword.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblPassword.setBounds(25, 270, 145, 28);
		frame.getContentPane().add(lblPassword);

		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblFirstName.setBounds(25, 66, 80, 28);
		frame.getContentPane().add(lblFirstName);

		JLabel lblSignUp = new JLabel("Sign Up");
		lblSignUp.setFont(new Font("Times New Roman", Font.BOLD, 28));
		lblSignUp.setBounds(182, 11, 108, 55);
		frame.getContentPane().add(lblSignUp);

		first_name = new JTextField();
		first_name.setColumns(10);
		first_name.setBounds(50, 98, 152, 23);
		frame.getContentPane().add(first_name);

		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblLastName.setBounds(266, 66, 145, 28);
		frame.getContentPane().add(lblLastName);

		last_name = new JTextField();
		last_name.setColumns(10);
		last_name.setBounds(287, 98, 152, 23);
		frame.getContentPane().add(last_name);

		JLabel lblEmail = new JLabel("E-Mail");
		lblEmail.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblEmail.setBounds(25, 137, 80, 28);
		frame.getContentPane().add(lblEmail);

		email = new JTextField();
		email.setColumns(10);
		email.setBounds(50, 168, 152, 23);
		frame.getContentPane().add(email);

		JLabel lblPhoneNumber = new JLabel("Phone Number");
		lblPhoneNumber.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblPhoneNumber.setBounds(266, 136, 145, 28);
		frame.getContentPane().add(lblPhoneNumber);

		phone_number = new JTextField();
		phone_number.setColumns(10);
		phone_number.setBounds(287, 168, 152, 23);
		frame.getContentPane().add(phone_number);

		JLabel lblDefaultShippingAddress = new JLabel("Default shipping address");
		lblDefaultShippingAddress.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblDefaultShippingAddress.setBounds(25, 338, 177, 28);
		frame.getContentPane().add(lblDefaultShippingAddress);

		shipping_address = new JTextField();
		shipping_address.setColumns(10);
		shipping_address.setBounds(50, 369, 152, 23);
		frame.getContentPane().add(shipping_address);

		lblNewLabel = new JLabel("On ordering you may choose to enter a new shipping address.");
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(Color.GRAY);
		lblNewLabel.setBounds(50, 395, 302, 14);
		frame.getContentPane().add(lblNewLabel);

		btn_signup = new JButton("Sign Up");
		btn_signup.setFont(new Font("Times New Roman", Font.BOLD, 17));
		btn_signup.setBounds(331, 420, 108, 44);
		frame.getContentPane().add(btn_signup);

		label_1 = new JLabel("*");
		label_1.setForeground(Color.RED);
		label_1.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		label_1.setBounds(71, 145, 13, 14);
		frame.getContentPane().add(label_1);

		label_2 = new JLabel("*");
		label_2.setForeground(Color.RED);
		label_2.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		label_2.setBounds(92, 210, 13, 14);
		frame.getContentPane().add(label_2);

		label_3 = new JLabel("*");
		label_3.setForeground(Color.RED);
		label_3.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		label_3.setBounds(161, 278, 13, 14);
		frame.getContentPane().add(label_3);

		label_4 = new JLabel("*");
		label_4.setForeground(Color.RED);
		label_4.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		label_4.setBounds(379, 278, 13, 14);
		frame.getContentPane().add(label_4);

		label_5 = new JLabel("*");
		label_5.setForeground(Color.RED);
		label_5.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		label_5.setBounds(366, 145, 13, 14);
		frame.getContentPane().add(label_5);

		label_6 = new JLabel("*");
		label_6.setForeground(Color.RED);
		label_6.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		label_6.setBounds(339, 74, 13, 14);
		frame.getContentPane().add(label_6);

		label_7 = new JLabel("*");
		label_7.setForeground(Color.RED);
		label_7.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		label_7.setBounds(189, 346, 13, 14);
		frame.getContentPane().add(label_7);

		lblRequiredFields = new JLabel("Required fields\r\n");
		lblRequiredFields.setVerticalAlignment(SwingConstants.TOP);
		lblRequiredFields.setHorizontalAlignment(SwingConstants.LEFT);
		lblRequiredFields.setForeground(Color.GRAY);
		lblRequiredFields.setBounds(36, 450, 75, 14);
		frame.getContentPane().add(lblRequiredFields);

		JLabel label_9 = new JLabel("*");
		label_9.setForeground(Color.RED);
		label_9.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		label_9.setBounds(25, 450, 13, 14);
		frame.getContentPane().add(label_9);

		password = new JPasswordField();
		password.setBounds(50, 302, 152, 23);
		frame.getContentPane().add(password);

		password_repeat = new JPasswordField();
		password_repeat.setBounds(287, 302, 152, 23);
		frame.getContentPane().add(password_repeat);
	}
}
