package gui.books_handling;

import javax.swing.JFrame;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import java.awt.Font;
import HelperClasses.NotFound;
import controller.Manager_controller;

import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class ManagerActionsPage {

	Manager_controller manager_controller;

	private JFrame frame;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton btnLogout;
	private JButton btnEditInformation;
	JButton btnSearchBooks;
	/**
	 * Create the application.
	 * @param manager_controller
	 */
	public ManagerActionsPage(Manager_controller manager_controller) {
		this.manager_controller = manager_controller;
		initialize();
		this.frame.setVisible(true);

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 475, 342);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton btn_add_new_book = new JButton("Add a new book");
		btn_add_new_book.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					manager_controller.add_book();
				} catch (NotFound e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btn_add_new_book.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btn_add_new_book.setBounds(72, 72, 128, 38);
		frame.getContentPane().add(btn_add_new_book);

		JButton btn_add_publisher = new JButton("Add Publisher");
		btn_add_publisher.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				manager_controller.addPublisher();
			}
		});
		btn_add_publisher.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btn_add_publisher.setBounds(72, 141, 128, 38);
		frame.getContentPane().add(btn_add_publisher);

		JButton btn_modify_book = new JButton("Modify a book");
		btn_modify_book.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				manager_controller.modify_book();
			}
		});
		btn_modify_book.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btn_modify_book.setBounds(237, 72, 149, 38);
		frame.getContentPane().add(btn_modify_book);

		JButton btn_promote_customers = new JButton("Promote customers");
		btn_promote_customers.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				manager_controller.promote_user();
			}
		});
		btn_promote_customers.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btn_promote_customers.setBounds(237, 141, 149, 38);
		frame.getContentPane().add(btn_promote_customers);


		JRadioButton radiobtn_total_sales = new JRadioButton("Total sales for books in the previous month");
		radiobtn_total_sales.setToolTipText("1");
		buttonGroup.add(radiobtn_total_sales);
		radiobtn_total_sales.setSelected(true);
		radiobtn_total_sales.setBounds(6, 36, 304, 29);
		frame.getContentPane().add(radiobtn_total_sales);
		radiobtn_total_sales.setVisible(false);

		JRadioButton radiobtn_top_customers = new JRadioButton("The top 5 customers who purchase the most during the last 2 months");
		radiobtn_top_customers.setToolTipText("2");
		buttonGroup.add(radiobtn_top_customers);
		radiobtn_top_customers.setBounds(6, 67, 447, 34);
		frame.getContentPane().add(radiobtn_top_customers);
		radiobtn_top_customers.setVisible(false);

		JRadioButton radiobtn_top_books = new JRadioButton("The top 10 selling books for the last three months");
		radiobtn_top_books.setToolTipText("3");
		buttonGroup.add(radiobtn_top_books);
		radiobtn_top_books.setBounds(6, 104, 304, 29);
		frame.getContentPane().add(radiobtn_top_books);
		radiobtn_top_books.setVisible(false);

		JButton btn_veiw_reports_2 = new JButton("View reports");
		btn_veiw_reports_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String button_selected = getSelectedButtonText(buttonGroup);
				manager_controller.view_reports(button_selected);
			}
		});
		btn_veiw_reports_2.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btn_veiw_reports_2.setBounds(287, 190, 128, 38);
		btn_veiw_reports_2.setVisible(false);
		frame.getContentPane().add(btn_veiw_reports_2);

		JButton btn_view_reports = new JButton("View reports");
		btn_view_reports.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btn_add_new_book.setVisible(false);
				btn_add_publisher.setVisible(false);
				btn_modify_book.setVisible(false);
				btn_promote_customers.setVisible(false);
				btn_view_reports.setVisible(false);
				btnEditInformation.setVisible(false);
				btnLogout.setVisible(false);
				btnSearchBooks.setVisible(false);

				btn_veiw_reports_2.setVisible(true);
				radiobtn_total_sales.setVisible(true);
				radiobtn_top_customers.setVisible(true);
				radiobtn_top_books.setVisible(true);
			}
		});
		btn_view_reports.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btn_view_reports.setBounds(237, 212, 149, 38);
		frame.getContentPane().add(btn_view_reports);

		btnSearchBooks = new JButton("Search Books");
		btnSearchBooks.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				manager_controller.search_books();
			}
		});
		btnSearchBooks.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btnSearchBooks.setBounds(72, 212, 128, 38);
		frame.getContentPane().add(btnSearchBooks);

		btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				manager_controller.logout();
				frame.setVisible(false);
				frame.dispose();
			}
		});
		btnLogout.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btnLogout.setBounds(304, 11, 149, 38);
		frame.getContentPane().add(btnLogout);

		btnEditInformation = new JButton("Edit Information");
		btnEditInformation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					manager_controller.editInformation();
				} catch (NotFound e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnEditInformation.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btnEditInformation.setBounds(6, 11, 128, 38);
		frame.getContentPane().add(btnEditInformation);


	}


	private String getSelectedButtonText(ButtonGroup buttonGroup) {
	    for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
	        AbstractButton button = buttons.nextElement();

	        if (button.isSelected()) {
	            return button.getToolTipText();
	        }
	    }

	    return null;
	}


	public void set_manager_controller (Manager_controller m) {
		this.manager_controller = m;
}
	public void reOpen() {
		this.frame.setVisible(true);
	}
}
