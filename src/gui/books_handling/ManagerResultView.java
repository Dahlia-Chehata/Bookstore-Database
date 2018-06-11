package gui.books_handling;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import HelperClasses.NotFound;
import controller.IResultView;
import controller.ResultViewController;
import gui.buttonHelpers.*;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class ManagerResultView implements IResultView {


	private ResultViewController result_controller;
	private IResultView result_view;
	
	private JFrame frame;
	private JTable table;
	private String[] column_names;
	private String[][] data;
	private int total_size_of_data;
	protected DefaultTableModel dm;
	private int number_of_next_clicks;


	/**
	 * Create the application.
	 * @param column_names2 
	 * @param size 
	 * @throws NotFound 
	 */
	public ManagerResultView(ResultViewController r, String[] column_names2, int size) throws NotFound {
		this.result_view = this;
		this.total_size_of_data = size;
		this.column_names = column_names2;
		this.result_controller = r;
		initialize();
		this.frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws NotFound 
	 */
	private void initialize() throws NotFound {
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
			@Override
			public void actionPerformed(ActionEvent e) {
				result_controller.viewCart();
			}
		});
		btnViewCart.setFont(new Font("Times New Roman", Font.BOLD, 16));
		splitPane_1.setLeftComponent(btnViewCart);

		JButton btnNext = new JButton("Next");
		btnNext.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnNext.addActionListener(new ActionListener() {
			@Override
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
				
				table.getColumn("Buy").setCellRenderer(new ButtonRenderer());
			    table.getColumn("Buy").setCellEditor(new ButtonEditorResultAddToCart(new JCheckBox(), result_view));

			    table.getColumn("Order").setCellRenderer(new ButtonRenderer());
			    table.getColumn("Order").setCellEditor(new ButtonEditorResultAddToCart(new JCheckBox(), result_view));
				
				frame.getContentPane().add(table);
				frame.getContentPane().add(table.getTableHeader(), BorderLayout.PAGE_START);
//				table = new JTable(data, column_names);
			}
		});
		splitPane_1.setRightComponent(btnNext);

		
		data = result_controller.getData(0);
		dm = new DefaultTableModel(data, column_names);
		table = new JTable(dm);
		table.getColumn("Buy").setCellRenderer(new ButtonRenderer());
	    table.getColumn("Buy").setCellEditor(new ButtonEditorResultAddToCart(new JCheckBox(), this));

	    table.getColumn("Order").setCellRenderer(new ButtonRenderer());
	    table.getColumn("Order").setCellEditor(new ButtonEditorResultAddToCart(new JCheckBox(), this));

		frame.getContentPane().add(table);
		frame.getContentPane().add(table.getTableHeader(), BorderLayout.PAGE_START);


//		JButton btnNext = new JButton("Next");


//		splitPane.setRightComponent(btnNext);

	}

	public void setData(String[][] data) {
		this.data = data;
	}

	@Override
	public void setColumn_names(String[] column_names) {
		this.column_names = column_names;
	}

	@Override
	public void setTotal_size_of_data(int total_size_of_data) {
		this.total_size_of_data = total_size_of_data;
	}

	@Override
	public boolean set_add_to_cart (String ISBN, int number) throws NotFound {
		if (result_controller.add_to_cart(ISBN, number)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean order_book(String ISBN, int label) {
		if (result_controller.order_book(ISBN, label)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public ArrayList<String> getPublisherNames() {
		return (result_controller.getPublisherNames());
	}

	@Override
	public ArrayList<String> getPublishersPhones() {
		return (result_controller.getPublisherPhones());
	}

	@Override
	public void updateTable(String[][] data) {
		this.data = data;
		dm = new DefaultTableModel(data, column_names);
		
		table = new JTable(dm);
		table.getColumn("Buy").setCellRenderer(new ButtonRenderer("Add to cart"));
	    table.getColumn("Buy").setCellEditor(new ButtonEditorResultAddToCart(new JCheckBox(), this));

	    table.getColumn("Order").setCellRenderer(new ButtonRenderer("Order"));
	    table.getColumn("Order").setCellEditor(new ButtonEditorResultAddToCart(new JCheckBox(), this));

		frame.getContentPane().add(table);
		frame.getContentPane().add(table.getTableHeader(), BorderLayout.PAGE_START);
		
	}
}
