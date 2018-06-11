package gui.buttonHelpers;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import HelperClasses.NotFound;
import ModelsImplementation.Book;
import ModelsImplementation.ManagerOrder;
import ModelsInterfaces.IManagerOrder;
import controller.IResultView;


public class ButtonEditorResultOrder extends DefaultCellEditor {
  protected JButton button;

  private int amount;

  private boolean isPushed;

  private String ISBN;

private IResultView result_view;

private JComboBox pub_name_telephone;


  public ButtonEditorResultOrder(JCheckBox checkBox, IResultView result_view) {
    super(checkBox);
    this.result_view = result_view;
    button = new JButton();
    button.setOpaque(true);
    button.addActionListener(new ActionListener() {
      @Override
	public void actionPerformed(ActionEvent e) {
        fireEditingStopped();
      }
    });
  }

  @Override
public Component getTableCellEditorComponent(JTable table, Object value,
      boolean isSelected, int row, int column) {
    if (isSelected) {
      button.setForeground(table.getSelectionForeground());
      button.setBackground(table.getSelectionBackground());
    } else {
      button.setForeground(table.getForeground());
      button.setBackground(table.getBackground());
    }


//    amount = (value == null) ? "" : value.toString();

    button.setText("Order");
//    button.setText((value == null) ? "" : value.toString());

    isPushed = true;
    ISBN = (String) table.getValueAt(row, 0);
    System.out.println("HEEREEE::: " + table.getValueAt(row, 0) + "\n");

    return button;
  }

  @Override
public Object getCellEditorValue() {
    if (isPushed) { //orderbook
        JTextField field1 = new JTextField("1");
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Enter the required amount:"));
        panel.add(field1);

        pub_name_telephone = new JComboBox();
		pub_name_telephone.setBounds(157, 210, 243, 20);
		ArrayList<String> pub_names = result_view.getPublisherNames();
		ArrayList<String> pub_phones = result_view.getPublishersPhones();
		int number_of_publishers = pub_names.size();
		for (int i = 0; i < number_of_publishers; i++) {
			pub_name_telephone.addItem(pub_names.get(i) + " - " + pub_phones.get(i));
		}

        int result = JOptionPane.showConfirmDialog(null, panel, "Order",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
        	int amount = Integer.parseInt(field1.getText());
        	IManagerOrder manager_order = new ManagerOrder();
        	try {
				manager_order.addOrder(new Book(ISBN), amount);
			} catch (NotFound e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } else {
            System.out.println("Cancelled");
        }
    }
    isPushed = false;

//    System.out.println("\n" + amount);
//    return new String(amount);
    return amount;
  }

  @Override
public boolean stopCellEditing() {
    isPushed = false;
    return super.stopCellEditing();
  }

  @Override
protected void fireEditingStopped() {
    super.fireEditingStopped();
  }
}