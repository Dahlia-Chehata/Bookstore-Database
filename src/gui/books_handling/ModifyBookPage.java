package gui.books_handling;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import HelperClasses.NotFound;
import ModelsInterfaces.IBook;
import ModelsInterfaces.IBooksGetter;
import controller.ManagerModifyBookController;

public class ModifyBookPage {

	private JFrame frame;
	private JTextField textField_id;
	private JTextField isbn;
	private JTextField title;
	private JLabel lblTitle;
	private JLabel lblThreshold;
	private JTextField threshold;
	private JLabel lblCategory;
	private JComboBox bookcategories;
	private JLabel lblPublisherName;
	private JComboBox pub_name_telephone;
	private JLabel lblAuthorNames;
	private JTextField author_names;
	private JLabel lblNewLabel_1;
	private JLabel lblPublisherYear;
	private JTextField pub_year;
	private JLabel lblSellingPrice;
	private JTextField selling_price;
	private JButton btnEditBook;
	private JTextField avail_quantity;
	private JLabel lblAvailableQuantity;
	private ManagerModifyBookController managermodifybook_controller;
	private IBooksGetter getter;
	private IBook book;

	/**
	 * Create the application.
	 * @param managerModifyBookController 
	 * @param iBook 
	 * @throws NotFound 
	 */
	public ModifyBookPage(ManagerModifyBookController managerModifyBookController, IBook iBook) throws NotFound {
		book = iBook;
		this.managermodifybook_controller = managerModifyBookController;
		initialize();
		this.frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws NotFound 
	 */
	private void initialize() throws NotFound {
		frame = new JFrame();
		frame.setBounds(100, 100, 527, 452);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Modify Book");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel.setBounds(10, 11, 155, 31);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblISBN = new JLabel("ISBN");
		lblISBN.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblISBN.setBounds(55, 55, 64, 16);
		frame.getContentPane().add(lblISBN);

		isbn = new JTextField(book.getISBN());
		isbn.setColumns(10);
		isbn.setBounds(129, 53, 86, 20);
		frame.getContentPane().add(isbn);

		title = new JTextField(book.getTitle());
		title.setColumns(10);
		title.setBounds(139, 92, 261, 20);
		frame.getContentPane().add(title);

		lblTitle = new JLabel("Book title");
		lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblTitle.setBounds(55, 94, 64, 16);
		frame.getContentPane().add(lblTitle);

		lblThreshold = new JLabel("Threshold");
		lblThreshold.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblThreshold.setBounds(55, 137, 64, 16);
		frame.getContentPane().add(lblThreshold);

		threshold = new JTextField(String.valueOf(book.getThreshold()));
		threshold.setColumns(10);
		threshold.setBounds(131, 135, 39, 20);
		frame.getContentPane().add(threshold);
//		ArrayList<String> pub_names = m.getPublisherNames();
//		ArrayList<String> pub_phones = m.getPublishersPhones();
//		int number_of_publishers = pub_names.size();
//		for (int i = 0; i < number_of_publishers; i++) {
//			pub_name_telephone.addItem(pub_names.get(i) + " - " + pub_phones.get(i));
//		}

		lblPublisherYear = new JLabel("Publishing Year");
		lblPublisherYear.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblPublisherYear.setBounds(55, 213, 97, 16);
		frame.getContentPane().add(lblPublisherYear);

		pub_year = new JTextField(String.valueOf(book.getPublicationYear()));
		pub_year.setColumns(10);
		pub_year.setBounds(157, 211, 68, 20);
		frame.getContentPane().add(pub_year);

		lblSellingPrice = new JLabel("Selling Price");
		lblSellingPrice.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblSellingPrice.setBounds(55, 258, 97, 16);
		frame.getContentPane().add(lblSellingPrice);

		selling_price = new JTextField(String.valueOf(book.getPrice()));
		selling_price.setBounds(157, 258, 68, 20);
		frame.getContentPane().add(selling_price);

		JLabel lblInformationModifiedSuccessfully = new JLabel("Information modified successfully");
		lblInformationModifiedSuccessfully.setForeground(Color.BLUE);
		lblInformationModifiedSuccessfully.setBounds(22, 376, 261, 26);
		lblInformationModifiedSuccessfully.setVisible(false);
		frame.getContentPane().add(lblInformationModifiedSuccessfully);
		
		btnEditBook = new JButton("Modify Book");
		btnEditBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					set_book_attributes();
					lblInformationModifiedSuccessfully.setVisible(true);
				} catch (NotFound e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnEditBook.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btnEditBook.setBounds(302, 294, 147, 50);
		frame.getContentPane().add(btnEditBook);

		avail_quantity = new JTextField(String.valueOf(book.getAvailableQuantity()));
		avail_quantity.setColumns(10);
		avail_quantity.setBounds(168, 170, 39, 20);
		frame.getContentPane().add(avail_quantity);

		lblAvailableQuantity = new JLabel("Available quantity");
		lblAvailableQuantity.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblAvailableQuantity.setBounds(55, 168, 115, 20);
		frame.getContentPane().add(lblAvailableQuantity);

	}


	private void set_book_attributes() throws NotFound {
//		managermodifybook_controller.setAuthors_before(author_names.getText());
		managermodifybook_controller.setAvailable_quantity(avail_quantity.getText());
//		managermodifybook_controller.setCategory(bookcategories.getSelectedItem().toString());
		managermodifybook_controller.setBook_isbn(isbn.getText());
//		managermodifybook_controller.setPub_information(pub_name_telephone.getSelectedItem().toString());
		managermodifybook_controller.setPub_year(pub_year.getText());
		managermodifybook_controller.setSelling_price(selling_price.getText());
		managermodifybook_controller.setThreshold(threshold.getText());
	}
}
