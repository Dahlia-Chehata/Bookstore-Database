package gui.books_handling;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class ManagerActionsPage {

	private JFrame frame;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManagerActionsPage window = new ManagerActionsPage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ManagerActionsPage() {
		initialize();
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
		btn_add_new_book.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btn_add_new_book.setBounds(72, 72, 128, 38);
		frame.getContentPane().add(btn_add_new_book);

		JButton btn_place_order = new JButton("Place order");
		btn_place_order.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btn_place_order.setBounds(72, 141, 128, 38);
		frame.getContentPane().add(btn_place_order);

		JButton btn_modify_book = new JButton("Modify a book");
		btn_modify_book.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btn_modify_book.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btn_modify_book.setBounds(237, 72, 149, 38);
		frame.getContentPane().add(btn_modify_book);

		JButton btn_promote_customers = new JButton("Promote customers");
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
			public void actionPerformed(ActionEvent e) {
				String button_selected = getSelectedButtonText(buttonGroup);

				//This should be in the controller to be sent to an appropriate interface
/*				switch (button_selected) {
				case "1": //Total sales
					break;

				case "2": //Top 5 customers
					System.out.println("HIII");
					break;

				case "3": //Top 10 selling books
					System.out.println("HEYYY");
					break;

				default:
					break;
				}*/

			}
		});
		btn_veiw_reports_2.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btn_veiw_reports_2.setBounds(287, 190, 128, 38);
		btn_veiw_reports_2.setVisible(false);
		frame.getContentPane().add(btn_veiw_reports_2);

		JButton btn_view_reports = new JButton("View reports");
		btn_view_reports.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btn_add_new_book.setVisible(false);
				btn_place_order.setVisible(false);
				btn_modify_book.setVisible(false);
				btn_promote_customers.setVisible(false);
				btn_view_reports.setVisible(false);

				btn_veiw_reports_2.setVisible(true);
				radiobtn_total_sales.setVisible(true);
				radiobtn_top_customers.setVisible(true);
				radiobtn_top_books.setVisible(true);
			}
		});
		btn_view_reports.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btn_view_reports.setBounds(72, 212, 128, 38);
		frame.getContentPane().add(btn_view_reports);


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

}
