package gui.user_handling;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import HelperClasses.NotFound;
import controller.EditInformationController;

public class EditUserInformation {

	private JFrame frame;
	private JLabel label;
	private JTextField username;
	private JTextField first_name;
	private JTextField last_name;
	private JTextField email;
	private JTextField phone_number;
	private JTextField shipping_address;
	private JLabel lblNewLabel;
	private JPasswordField password;
	private JPasswordField password_repeat;
	private JButton btn_edit;
	private JLabel label_1;
	private JLabel label_2;
	private JLabel label_3;
	private Component label_4;
	private Component label_5;
	private EditInformationController edit_controller;
	private String[] old_information;


	/**
	 * Create the application.
	 * @param old_information
	 */
	public EditUserInformation(EditInformationController edit_controller, String[] old_information) {
		this.old_information = old_information;
		this.edit_controller = edit_controller;
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

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblUsername.setBounds(25, 202, 80, 28);
		frame.getContentPane().add(lblUsername);

		username = new JTextField();
		username.setBounds(50, 233, 152, 23);
		frame.getContentPane().add(username);
		username.setText(old_information[0]);
		username.setColumns(10);

		JLabel error_label = new JLabel("The passwords don't match!");
		error_label.setForeground(Color.RED);
		error_label.setBounds(25, 420, 335, 19);
		error_label.setVisible(false);
		frame.getContentPane().add(error_label);

		JLabel error_label1 = new JLabel("There are some wrong information!");
		error_label1.setForeground(Color.RED);
		error_label1.setBounds(25, 420, 335, 19);
		error_label1.setVisible(false);
		frame.getContentPane().add(error_label1);

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
		first_name.setText(old_information[2]);
		frame.getContentPane().add(first_name);

		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblLastName.setBounds(266, 66, 145, 28);
		frame.getContentPane().add(lblLastName);

		last_name = new JTextField();
		last_name.setColumns(10);
		last_name.setBounds(287, 98, 152, 23);
		last_name.setText(old_information[3]);
		frame.getContentPane().add(last_name);

		JLabel lblEmail = new JLabel("E-Mail");
		lblEmail.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblEmail.setBounds(25, 137, 80, 28);
		frame.getContentPane().add(lblEmail);

		email = new JTextField();
		email.setColumns(10);
		email.setBounds(50, 168, 152, 23);
		email.setText(old_information[1]);
		frame.getContentPane().add(email);

		JLabel lblPhoneNumber = new JLabel("Phone Number");
		lblPhoneNumber.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblPhoneNumber.setBounds(266, 136, 145, 28);
		frame.getContentPane().add(lblPhoneNumber);

		phone_number = new JTextField();
		phone_number.setColumns(10);
		phone_number.setBounds(287, 168, 152, 23);
		phone_number.setText(old_information[5]);
		frame.getContentPane().add(phone_number);

		JLabel lblDefaultShippingAddress = new JLabel("Default shipping address");
		lblDefaultShippingAddress.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblDefaultShippingAddress.setBounds(25, 338, 177, 28);
		frame.getContentPane().add(lblDefaultShippingAddress);

		shipping_address = new JTextField();
		shipping_address.setColumns(10);
		shipping_address.setBounds(50, 369, 152, 23);
		shipping_address.setText(old_information[4]);
		frame.getContentPane().add(shipping_address);

		lblNewLabel = new JLabel("On ordering you may choose to enter a new shipping address.");
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setForeground(Color.GRAY);
		lblNewLabel.setBounds(50, 395, 389, 19);
		frame.getContentPane().add(lblNewLabel);

		password = new JPasswordField();
		password.setBounds(50, 302, 152, 23);
		frame.getContentPane().add(password);

		password_repeat = new JPasswordField();
		password_repeat.setBounds(287, 302, 152, 23);
		frame.getContentPane().add(password_repeat);

		btn_edit = new JButton("Edit Information");
		btn_edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				if(password.getText().equals(password_repeat.getText())) {
					error_label.setVisible(false);
					error_label1.setVisible(false);

					String[] edited_information = new String[7];
					edited_information[0] = username.getText();
					edited_information[1] = email.getText();
					edited_information[2] = password.getText();
					edited_information[3] = first_name.getText();
					edited_information[4] = last_name.getText();
					edited_information[5] = shipping_address.getText();
					edited_information[6] = phone_number.getText();
//					edited_information[7] = first_name.getText();

					try {
						edit_controller.editInformation(edited_information);
						frame.setVisible(false);
						frame.dispose();
					} catch (NotFound e) {
						error_label1.setVisible(true);
//						e.printStackTrace();
					}

				} else {
					error_label.setVisible(true);
				}




			}
		});
		btn_edit.setFont(new Font("Times New Roman", Font.BOLD, 15));
		btn_edit.setBounds(287, 420, 152, 44);
		frame.getContentPane().add(btn_edit);


	}

}
