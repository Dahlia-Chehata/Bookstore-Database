package gui.books_handling;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import HelperClasses.NotFound;
import controller.ManagerModifyBookController;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ModifyBook {

	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */

	private ManagerModifyBookController manager_modifybook_controller;

	/**
	 * Create the application.
	 */
	public ModifyBook(ManagerModifyBookController m) {
		this.manager_modifybook_controller = m;
		initialize();
		this.frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblSearchForA = new JLabel("Search for a book by:");
		lblSearchForA.setBounds(10, 38, 115, 24);
		frame.getContentPane().add(lblSearchForA);

		JRadioButton ISBN_radiobtn = new JRadioButton("ISBN");
		ISBN_radiobtn.setBounds(16, 88, 109, 23);
		frame.getContentPane().add(ISBN_radiobtn);

		JRadioButton title_radiobtn = new JRadioButton("Title");
		title_radiobtn.setBounds(16, 114, 109, 23);
		frame.getContentPane().add(title_radiobtn);

		textField = new JTextField();
		textField.setBounds(16, 172, 241, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JButton Search_btn = new JButton("Search");
		Search_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String input = textField.getText();
				manager_modifybook_controller.set_text(input);
				if (ISBN_radiobtn.isSelected()) {
					manager_modifybook_controller.set_search_method(0);
				} else { //it is a title
					manager_modifybook_controller.set_search_method(1);
				}
				try {
					manager_modifybook_controller.search();
				} catch (NotFound e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		Search_btn.setBounds(316, 212, 89, 23);
		frame.getContentPane().add(Search_btn);
	}
}
