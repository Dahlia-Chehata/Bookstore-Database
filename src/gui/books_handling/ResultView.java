package gui.books_handling;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import HelperClasses.NotFound;
import controller.ResultViewController;
import gui.buttonHelpers.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ResultView {


	private ResultViewController result_controller;
	private JFrame frame;
	private JTable table;
	private String[] column_names;
	private String[][] data;
	private int total_size_of_data;
	protected DefaultTableModel dm;
	private int number_of_next_clicks;

//	private int userId;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ResultView window = new ResultView();
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
	public ResultView(ResultViewController r) {
		this.result_controller = r;
		initialize();
		this.frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 912, 458);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JSplitPane splitPane = new JSplitPane();
		frame.getContentPane().add(splitPane, BorderLayout.SOUTH);

		JLabel lblNewLabel = new JLabel("Viewing 10 of 100 Results");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
		splitPane.setLeftComponent(lblNewLabel);

		JSplitPane splitPane_1 = new JSplitPane();
		splitPane.setRightComponent(splitPane_1);

		JButton btnViewCart = new JButton("View Cart");
		btnViewCart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				result_controller.viewCart();
			}
		});
		btnViewCart.setFont(new Font("Times New Roman", Font.BOLD, 16));
		splitPane_1.setLeftComponent(btnViewCart);

		JButton btnNext = new JButton("Next");
		btnNext.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				number_of_next_clicks++;
				try {
					data = result_controller.getData(number_of_next_clicks);
				} catch (NotFound e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				dm.setRowCount(0);

				dm = new DefaultTableModel(data, column_names);
				table = new JTable(dm);
				frame.getContentPane().add(table);
				frame.getContentPane().add(table.getTableHeader(), BorderLayout.PAGE_START);
//				table = new JTable(data, column_names);
			}
		});
		splitPane_1.setRightComponent(btnNext);


		dm = new DefaultTableModel(data, column_names);
		table = new JTable(dm);
		table.getColumn("").setCellRenderer(new ButtonRenderer());
	    table.getColumn("").setCellEditor(new ButtonEditor(new JCheckBox(), this));

		frame.getContentPane().add(table);
		frame.getContentPane().add(table.getTableHeader(), BorderLayout.PAGE_START);


//		JButton btnNext = new JButton("Next");


//		splitPane.setRightComponent(btnNext);

	}

	public void setData(String[][] data) {
		this.data = data;
	}

	public void setColumn_names(String[] column_names) {
		this.column_names = column_names;
	}

	public void setTotal_size_of_data(int total_size_of_data) {
		this.total_size_of_data = total_size_of_data;
	}

	public boolean set_add_to_cart (String ISBN, int number) throws NotFound {
		if (result_controller.add_to_cart(ISBN, number)) {
			return true;
		} else {
			return false;
		}
	}
}
