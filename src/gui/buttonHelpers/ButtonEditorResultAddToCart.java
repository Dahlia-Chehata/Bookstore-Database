package gui.buttonHelpers;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import HelperClasses.NotFound;
import controller.IResultView;

public class ButtonEditorResultAddToCart extends DefaultCellEditor {
  protected JButton button;

  private int label;

  private boolean isPushed;

  private IResultView result_view;
  private String ISBN;

  public ButtonEditorResultAddToCart(JCheckBox checkBox, IResultView result_view) {
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


//    label = (value == null) ? "" : value.toString();

    button.setText("Add to Cart");
    button.setText((value == null) ? "" : value.toString());

    isPushed = true;
    ISBN = (String) table.getValueAt(row, 0);
    System.out.println("HEEREEE::: " + table.getValueAt(row, 0) + "\n");

    return button;
  }

  @Override
public Object getCellEditorValue() {
    if (isPushed) {
        JTextField field1 = new JTextField("1");
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Enter the required amount:"));
        panel.add(field1);
        int result = JOptionPane.showConfirmDialog(null, panel, "Order",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            System.out.println(" " + field1.getText());
            label = Integer.parseInt(field1.getText());

            try {
				if (!(result_view.set_add_to_cart(ISBN, label))) {
					JOptionPane.showMessageDialog(this.getComponent(),
				            "You entered an invalid amount!",
				            "Try again",
				            JOptionPane.ERROR_MESSAGE);
				}
			} catch (HeadlessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotFound e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        } else {
	        	label = 1;
	            System.out.println("Cancelled");
	        }



      //
      //
    //  JOptionPane.showMessageDialog(button, label + ": Ouch!");
      // System.out.println(label + ": Ouch!");
    }
    isPushed = false;

    System.out.println("\n" + label);
//    return new String(label);
    return label;
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