package gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.JSplitPane;

import controller.ManagerViewReportsController;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;

public class ReportsView {

	private JFrame frame;
	private ManagerViewReportsController manager_viewreports_controller;
	private JTable table;
	private String[][] data;
	private String[] column_names;
	private boolean total_sales;

	/**
	 * Create the application.
	 * @param managerViewReportsController
	 */
	public ReportsView(ManagerViewReportsController managerViewReportsController) {
		this.manager_viewreports_controller = managerViewReportsController;
		column_names = manager_viewreports_controller.getColumn_names();
		data = manager_viewreports_controller.getData();
		if (data == null) { //total sales required
			total_sales = true;
		} 
		initialize();
		if (data != null)
			this.frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		if (total_sales) {
			JOptionPane.showMessageDialog(null,
		            "Total sales: $" + manager_viewreports_controller.getTotalsales(),
		            "",
		            JOptionPane.INFORMATION_MESSAGE);
//			frame.setVisible(false);
//			frame.dispose();
//			frame.dispatchEvent(new WindowEvent());
//			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

			manager_viewreports_controller.gotoHomePage();
			
		} else {
			frame = new JFrame();
			frame.setBounds(100, 100, 652, 370);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().setLayout(new BorderLayout(0, 0));

			JSplitPane splitPane = new JSplitPane();
			frame.getContentPane().add(splitPane, BorderLayout.SOUTH);

			JButton btnBack = new JButton("Back");
			btnBack.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					frame.setVisible(false);
					frame.dispose();
					manager_viewreports_controller.back();
				}
			});
			btnBack.setFont(new Font("Times New Roman", Font.BOLD, 16));
			splitPane.setLeftComponent(btnBack);

			JButton btnGoToHomepage = new JButton("Go to Homepage");
			btnGoToHomepage.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					frame.setVisible(false);
					frame.dispose();
//					frame.dispatchEvent(new WindowEvent());
//					frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

					manager_viewreports_controller.gotoHomePage();
				}
			});
			btnGoToHomepage.setFont(new Font("Times New Roman", Font.BOLD, 15));
			splitPane.setRightComponent(btnGoToHomepage);

			table = new JTable(data, column_names);
			frame.getContentPane().add(table, BorderLayout.CENTER);
			frame.getContentPane().add(table.getTableHeader(), BorderLayout.PAGE_START);


		}		

	}

}
