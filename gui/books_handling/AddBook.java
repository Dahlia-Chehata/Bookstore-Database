
package gui.books_handling;

import java.awt.EventQueue;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;

import controller.ManagerAddBookController;
import controller.Manager_controller;

import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddBook {

	private ManagerAddBookController m;

	private JFrame frame;
	private JTextField textField_id;
	private JTextField isbn;
	private JLabel lblIsbn;
	private JTextField title;
	private JLabel lblTitle;
	private JLabel lblThreshold;
	private JTextField threshold;
	private JLabel lblQuantityToBe;
	private JTextField quantity_to_be_ordered;
	private JLabel lblCategory;
	private JLabel lblPublisherName;
	private JTextField pub_name;
	private JLabel lblAuthorNames;
	private JTextField author_names;
	private JLabel lblNewLabel_1;
	private JLabel lblPublisherYear;
	private JTextField pub_year;
	private JLabel lblSellingPrice;
	private JTextField selling_price;
	private JButton btnAddBook;
	private JComboBox<String> bookcategories;
	private JTextField avail_quantity;
	private JLabel lblAvailableQuantity;
	private JLabel lblPublisherAddress;
	private JTextField pub_addr;
	private JLabel lblPublisherPhone;
	private JTextField pub_phone;
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					AddBook window = new AddBook();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public AddBook(ManagerAddBookController m) {
		this.m = m;
		initialize();
		this.frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 527, 566);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Add a new book");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel.setBounds(10, 11, 155, 31);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblBookId = new JLabel("Book ID");
		lblBookId.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblBookId.setBounds(55, 55, 64, 16);
		frame.getContentPane().add(lblBookId);

		textField_id = new JTextField();
		textField_id.setEditable(false);
		textField_id.setBounds(129, 53, 86, 20);
//		textField_id.setText("#" + m.getBooks_counter());
		frame.getContentPane().add(textField_id);
		textField_id.setColumns(10);

		isbn = new JTextField();
		isbn.setColumns(10);
		isbn.setBounds(314, 53, 86, 20);
		frame.getContentPane().add(isbn);

		lblIsbn = new JLabel("ISBN");
		lblIsbn.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblIsbn.setBounds(265, 57, 39, 16);
		frame.getContentPane().add(lblIsbn);

		title = new JTextField();
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

		threshold = new JTextField();
		threshold.setColumns(10);
		threshold.setBounds(131, 135, 39, 20);
		frame.getContentPane().add(threshold);

		lblQuantityToBe = new JLabel("Quantity to be ordered");
		lblQuantityToBe.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblQuantityToBe.setBounds(213, 137, 130, 16);
		frame.getContentPane().add(lblQuantityToBe);

		quantity_to_be_ordered = new JTextField();
		quantity_to_be_ordered.setColumns(10);
		quantity_to_be_ordered.setBounds(361, 135, 39, 20);
		frame.getContentPane().add(quantity_to_be_ordered);

		lblCategory = new JLabel("Category");
		lblCategory.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblCategory.setBounds(233, 172, 58, 16);
		frame.getContentPane().add(lblCategory);


		bookcategories = new JComboBox<String>();
		bookcategories.setFont(new Font("Times New Roman", Font.BOLD, 13));
		bookcategories.setBounds(303, 168, 97, 24);
		bookcategories.addItem("Science");
		bookcategories.addItem("Art");
		bookcategories.addItem("Religion");
		bookcategories.addItem("History");
		bookcategories.addItem("Geography");
		frame.getContentPane().add(bookcategories);

		lblPublisherName = new JLabel("Publisher Name");
		lblPublisherName.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblPublisherName.setBounds(55, 212, 103, 16);
		frame.getContentPane().add(lblPublisherName);

		pub_name = new JTextField();
		pub_name.setColumns(10);
		pub_name.setBounds(168, 210, 233, 20);
		frame.getContentPane().add(pub_name);

		lblAuthorNames = new JLabel("Author Name(s)");
		lblAuthorNames.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblAuthorNames.setBounds(55, 349, 103, 16);
		frame.getContentPane().add(lblAuthorNames);

		author_names = new JTextField();
		author_names.setColumns(10);
		author_names.setBounds(157, 347, 233, 20);
		frame.getContentPane().add(author_names);

		lblNewLabel_1 = new JLabel("Separate author names by (,). Ex. John Marc, Philip Kane");
		lblNewLabel_1.setForeground(Color.GRAY);
		lblNewLabel_1.setBounds(87, 376, 363, 14);
		frame.getContentPane().add(lblNewLabel_1);

		lblPublisherYear = new JLabel("Publisher Year");
		lblPublisherYear.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblPublisherYear.setBounds(55, 317, 97, 16);
		frame.getContentPane().add(lblPublisherYear);

		pub_year = new JTextField();
		pub_year.setColumns(10);
		pub_year.setBounds(157, 315, 68, 20);
		frame.getContentPane().add(pub_year);

		lblSellingPrice = new JLabel("Selling Price");
		lblSellingPrice.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblSellingPrice.setBounds(55, 401, 97, 16);
		frame.getContentPane().add(lblSellingPrice);

		selling_price = new JTextField();
		selling_price.setColumns(10);
		selling_price.setBounds(157, 401, 68, 20);
		frame.getContentPane().add(selling_price);

		btnAddBook = new JButton("Add Book");
		btnAddBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!empty_fields()) {
					set_book_attributes();
				} else {
					//error
				}
			}


		});
		btnAddBook.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btnAddBook.setBounds(351, 433, 99, 50);
		frame.getContentPane().add(btnAddBook);

		avail_quantity = new JTextField();
		avail_quantity.setColumns(10);
		avail_quantity.setBounds(168, 170, 39, 20);
		frame.getContentPane().add(avail_quantity);

		lblAvailableQuantity = new JLabel("Available quantity");
		lblAvailableQuantity.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblAvailableQuantity.setBounds(55, 168, 115, 20);
		frame.getContentPane().add(lblAvailableQuantity);

		lblPublisherAddress = new JLabel("Publisher Address");
		lblPublisherAddress.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblPublisherAddress.setBounds(55, 247, 103, 16);
		frame.getContentPane().add(lblPublisherAddress);

		pub_addr = new JTextField();
		pub_addr.setColumns(10);
		pub_addr.setBounds(167, 245, 233, 20);
		frame.getContentPane().add(pub_addr);

		lblPublisherPhone = new JLabel("Publisher Phone");
		lblPublisherPhone.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblPublisherPhone.setBounds(55, 286, 103, 16);
		frame.getContentPane().add(lblPublisherPhone);

		pub_phone = new JTextField();
		pub_phone.setColumns(10);
		pub_phone.setBounds(168, 284, 233, 20);
		frame.getContentPane().add(pub_phone);
	}


	private void set_book_attributes() {
		m.setAuthors_before(author_names.getText());
		m.setAvailable_quantity(avail_quantity.getText());
		m.setCategory(bookcategories.getSelectedItem().toString());
		m.setBook_isbn(isbn.getText());
		m.setPub_information(pub_name.getText(), pub_addr.getText(), pub_phone.getText());
		m.setPub_year(pub_year.getText());
		m.setSelling_price(selling_price.getText());
		m.setThreshold(threshold.getText());
		m.setQuantity_to_be_orderd(quantity_to_be_ordered.getText());
	}

	private boolean empty_fields() {
		if (isbn.getText().isEmpty() || title.getText().isEmpty()
				|| threshold.getText().isEmpty() || quantity_to_be_ordered.getText().isEmpty()
				|| pub_name.getText().isEmpty() || author_names.getText().isEmpty()
				|| pub_year.getText().isEmpty() || selling_price.getText().isEmpty()
				|| bookcategories.getSelectedItem().toString().isEmpty()
				|| avail_quantity.getText().isEmpty() || pub_addr.getText().isEmpty()
				|| pub_phone.getText().isEmpty()) {
			return true;
		}
		return false;
	}
}
