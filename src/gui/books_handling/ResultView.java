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

		JLabel lblNewLabel = new JLabel("Viewing 15 of " + total_size_of_data + " Results");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
		splitPane.setLeftComponent(lblNewLabel);

		JSplitPane splitPane_1 = new JSplitPane();
		splitPane.setRightComponent(splitPane_1);

		JSplitPane splitPane_2 = new JSplitPane();
		splitPane_1.setRightComponent(splitPane_2);

		JButton btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				number_of_next_clicks++;
				try {
					data = result_controller.getData(number_of_next_clicks);
				} catch (NotFound e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				dm.setRowCount(0);

				dm = new DefaultTableModel(data, column_names);
				table = new JTable(dm);
				frame.getContentPane().add(table);
				frame.getContentPane().add(table.getTableHeader(), BorderLayout.PAGE_START);
//				table = new JTable(data, column_names);
			}
		});
		btnNext.setFont(new Font("Times New Roman", Font.BOLD, 15));
		splitPane_2.setRightComponent(btnNext);

		JButton btnViewCart_1 = new JButton("View Cart");
		btnViewCart_1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				result_controller.viewCart();

			}
		});
		btnViewCart_1.setFont(new Font("Times New Roman", Font.BOLD, 16));
		splitPane_2.setLeftComponent(btnViewCart_1);

		JLabel lblNewLabel_1 = new JLabel("Page 1 of 3");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 16));
		splitPane_1.setLeftComponent(lblNewLabel_1);


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
