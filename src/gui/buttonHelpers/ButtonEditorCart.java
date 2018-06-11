package gui.buttonHelpers;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ButtonEditorCart extends DefaultCellEditor {
  protected JButton button;

  private int label;

  private boolean isPushed;

  private String ISBN;

  private int row;
  private DefaultTableModel dm;


  public ButtonEditorCart(JCheckBox checkBox) {
    super(checkBox);
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
    this.row = row;
    System.out.println("ROW NUMBER:: " + row + "\n");
    dm = (DefaultTableModel) table.getModel();
    System.out.println("ROW COUNT:: " + dm.getRowCount() + "\n");

//    label = (value == null) ? "" : value.toString();

//    button.setText((value == null) ? "" : value.toString());
//    System.out.println("VALUE: " + ((value == null) ? "" : value.toString()) + "\n");
    button.setText("Discard");
    isPushed = true;
    ISBN = (String) table.getValueAt(row, 0);
//    System.out.println("HEEREEE::: " + table.getValueAt(row, 0) + "\n");

    return button;
  }

  @Override
public Object getCellEditorValue() {
    if (isPushed) {
    	dm.removeRow(row);
//        System.out.println("ROW COUNT GOWAAA:: " + dm.getRowCount() + "\n");

    	if (dm.getRowCount() > 0) {

        	JTable table = new JTable(dm);
    	} else {

    		String[] dummyCol = {};
    		dm.addRow(dummyCol);
    		button.setVisible(false);

//    		dm.removeRow(0);
//    		dm = null;
    	}

      //
      //
//      JOptionPane.showMessageDialog(button, label + ": Ouch!");
//       System.out.println(label + ": Ouch!");
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