package gui.buttonHelpers;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

public class ButtonRenderer extends JButton implements TableCellRenderer {

	private String text;

	public ButtonRenderer() {
		setOpaque(true);
	}

  public ButtonRenderer(String text) {
	  setOpaque(true);
	  this.text = text;
  }

@Override
  public Component getTableCellRendererComponent(JTable table, Object value,
      boolean isSelected, boolean hasFocus, int row, int column) {
    if (isSelected) {
      setForeground(table.getSelectionForeground());
      setBackground(table.getSelectionBackground());
    } else {
      setForeground(table.getForeground());
      setBackground(UIManager.getColor("Button.background"));
    }
    setText(text);

//    setText((value == null) ? "" : value.toString());
    return this;
  }


}
