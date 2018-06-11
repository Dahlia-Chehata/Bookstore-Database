package gui.books_handling;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;

import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import controller.ViewCartController;
import gui.buttonHelpers.ButtonEditorCart;
import gui.buttonHelpers.ButtonRenderer;
import gui.buttonHelpers.DateLabelFormatter;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JSplitPane;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.Properties;
import java.awt.event.ActionEvent;

public class ShoppingCart {

	private JFrame frame;
	private JTable table;
	private DefaultTableModel dm;

	private String[] columnNames;
	private ViewCartController viewCartController;
	private String[][] data;
	/**
	 * Create the application.
	 * @param viewCartController
	 */
	public ShoppingCart(ViewCartController viewCartController) {
		this.viewCartController = viewCartController;
		this.data = viewCartController.getData();
		setColumnNames();
		initialize();
		this.frame.setVisible(true);
	}

	private void setColumnNames() {
		columnNames = new String[4];
		columnNames[0] = "ISBN";
		columnNames[1] = "Book Title";
		columnNames[2] = "Ordered quantity";
		columnNames[3] = "Discard";

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Your Shopping Cart");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);




		dm = new DefaultTableModel(data, columnNames);

		table = new JTable(dm);
		frame.getContentPane().add(table);
		frame.getContentPane().add(table.getTableHeader(), BorderLayout.PAGE_START);


		table.getColumn("Discard").setCellRenderer(new ButtonRenderer("Discard"));
	    table.getColumn("Discard").setCellEditor(new ButtonEditorCart(new JCheckBox()));



		frame.getContentPane().add(table, BorderLayout.CENTER);



		JSplitPane splitPane = new JSplitPane();
		frame.getContentPane().add(splitPane, BorderLayout.SOUTH);

		JButton btnBuy = new JButton("Buy!");
		btnBuy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

//				JTextField field1 = new JTextField("1");
		        JPanel panel = new JPanel(new GridLayout(0, 1));
//		        panel.add(new JLabel("Enter the required amount:"));
//		        panel.add(field1);

		        JTextField field2 = new JTextField();
		        panel.add(new JLabel("Enter your credit card number:"));
		        panel.add(field2);

		        panel.add(new JLabel("Credit card expiry date:"));

		        UtilDateModel model = new UtilDateModel();
				model.setSelected(true);
		        Properties p = new Properties();
				p.put("text.today", "Today");
				p.put("text.month", "Month");
				p.put("text.year", "Year");
		        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel,
		        		new DateLabelFormatter());

		        panel.add(datePicker);

		        int result = JOptionPane.showConfirmDialog(null, panel, "Order",
		            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

		        if (result == JOptionPane.OK_OPTION) {
//		            System.out.println(" " + field1.getText());
//		            int amount = Integer.parseInt(field1.getText());
		            String credit_card = field2.getText();
		            Date credit_card_expiry_date = (Date) datePicker.getModel().getValue();

		            if (!viewCartController.buy(credit_card, credit_card_expiry_date)) {
		            	JOptionPane.showMessageDialog(null,
					            "You entered an invalid amount!",
					            "Try again",
					            JOptionPane.ERROR_MESSAGE);
		            } else {
		            	JOptionPane.showMessageDialog(null,
					            "Congratulations!",
					            "Congratulations!",
					            JOptionPane.INFORMATION_MESSAGE);
		            }

		        }

			}


		});
		splitPane.setRightComponent(btnBuy);

		JButton btnDiscardAll = new JButton("Discard All");
		btnDiscardAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dm.setRowCount(0);
				String[][] data1 = new String[data.length][]; //empty
				dm = new DefaultTableModel(data1, columnNames);
				table = new JTable(dm);
				frame.getContentPane().add(table);
				frame.getContentPane().add(table.getTableHeader(), BorderLayout.PAGE_START);
			}
		});
		splitPane.setLeftComponent(btnDiscardAll);
	}

}
