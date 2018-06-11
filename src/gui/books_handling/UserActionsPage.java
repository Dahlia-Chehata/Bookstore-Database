//package gui.books_handling;
//
//import java.awt.EventQueue;
//
//import javax.swing.JFrame;
//import javax.swing.JButton;
//import java.awt.Font;
//import java.awt.event.ActionListener;
//import java.awt.event.ActionEvent;
//
//public class UserActionsPage {
//
//	private JFrame frame;
//
//	/**
//	 * Launch the application.
//	 */
////	public static void main(String[] args) {
////		EventQueue.invokeLater(new Runnable() {
////			public void run() {
////				try {
////					UserActionsPage window = new UserActionsPage();
////					window.frame.setVisible(true);
////				} catch (Exception e) {
////					e.printStackTrace();
////				}
////			}
////		});
////	}
//
//	/**
//	 * Create the application.
//	 */
//	public UserActionsPage() {
//		initialize();
//		this.frame.setVisible(true);
//	}
//
//	/**
//	 * Initialize the contents of the frame.
//	 */
//	private void initialize() {
//		frame = new JFrame();
//		frame.setBounds(100, 100, 450, 192);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.getContentPane().setLayout(null);
//
//		JButton btnSearchBooks = new JButton("Search Books");
//		btnSearchBooks.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//			}
//		});
//		btnSearchBooks.setFont(new Font("Times New Roman", Font.BOLD, 14));
//		btnSearchBooks.setBounds(151, 85, 149, 34);
//		frame.getContentPane().add(btnSearchBooks);
//
//		JButton btnEditUser = new JButton("Edit User Information");
//		btnEditUser.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//			}
//		});
//		btnEditUser.setFont(new Font("Times New Roman", Font.BOLD, 13));
//		btnEditUser.setBounds(10, 12, 149, 34);
//		frame.getContentPane().add(btnEditUser);
//
//		JButton btnLogout = new JButton("Logout");
//		btnLogout.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//
//			}
//		});
//		btnLogout.setFont(new Font("Times New Roman", Font.BOLD, 14));
//		btnLogout.setBounds(292, 11, 132, 34);
//		frame.getContentPane().add(btnLogout);
//	}
//
//}
