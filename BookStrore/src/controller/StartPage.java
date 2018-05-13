package controller;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class StartPage implements IFrameController {

	private JFrame frame;
	/**
	 * to tell the controller either the user wants
	 * to sign up or log in
	 * 1 >> Log in
	 * 2 >> Sign up
	 */
	private int action_number;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					StartPage window = new StartPage();
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
	public StartPage() {
		initialize();
		this.frame.setVisible(true);

	}



	public int get_action () {
		//this.frame.setVisible(false);

		System.out.print(action_number);

		return action_number;
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton btn_login = new JButton("Log In");
		btn_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				action_number = 1;
			}
		});
		btn_login.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btn_login.setBounds(73, 86, 272, 57);
		frame.getContentPane().add(btn_login);

		JButton btn_signup = new JButton("Sign Up");
		btn_signup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				action_number = 2;
			}
		});
		btn_signup.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btn_signup.setBounds(73, 170, 272, 57);
		frame.getContentPane().add(btn_signup);

		JLabel lblNewLabel = new JLabel("Welcome to Book Store Database");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblNewLabel.setBounds(20, 11, 378, 57);
		frame.getContentPane().add(lblNewLabel);
	}



	@Override
	public void hide_frame() {
		this.frame.setVisible(false);

	}

}
