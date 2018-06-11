package gui.books_handling;

import javax.swing.JFrame;

import HelperClasses.NotFound;
import controller.Customer_controller;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CustomerActionsPage {

	private JFrame frame;
	private Customer_controller customer_controller;

	/**
	 * Create the application.
	 */
	public CustomerActionsPage(Customer_controller customer_controller) {
		this.customer_controller = customer_controller;
		initialize();
		this.frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 192);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton btnSearchBooks = new JButton("Search Books");
		btnSearchBooks.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				customer_controller.search_books();
			}
		});
		btnSearchBooks.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnSearchBooks.setBounds(151, 85, 149, 34);
		frame.getContentPane().add(btnSearchBooks);

		JButton btnEditUser = new JButton("Edit User Information");
		btnEditUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					customer_controller.editInformation();
				} catch (NotFound e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnEditUser.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btnEditUser.setBounds(10, 12, 149, 34);
		frame.getContentPane().add(btnEditUser);

		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				customer_controller.logout();
				frame.setVisible(false);
				frame.dispose();
			}
		});
		btnLogout.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnLogout.setBounds(292, 11, 132, 34);
		frame.getContentPane().add(btnLogout);
	}

	public void reOpen() {
		this.frame.setVisible(true);
	}
}
