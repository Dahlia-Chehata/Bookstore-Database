package gui.books_handling;

import java.awt.EventQueue;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JButton;

public class AddBook {

	private JFrame frame;
	private JTextField textField_id;
	private JTextField isbn;
	private JLabel lblIsbn;
	private JTextField title;
	private JLabel lblTitle;
	private JLabel lblThreshold;
	private JTextField threshold;
	private JLabel lblQuantityToBe;
	private JTextField quantity;
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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddBook window = new AddBook();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AddBook() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 527, 480);
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
//		TODO book_number from books_count to know the book's ID
//		textField_id.setText("#" + book_number);
		frame.getContentPane().add(textField_id);
		textField_id.setColumns(10);

		isbn = new JTextField();
		isbn.setColumns(10);
		isbn.setBounds(304, 53, 86, 20);
		frame.getContentPane().add(isbn);

		lblIsbn = new JLabel("ISBN");
		lblIsbn.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblIsbn.setBounds(255, 57, 39, 16);
		frame.getContentPane().add(lblIsbn);

		title = new JTextField();
		title.setColumns(10);
		title.setBounds(129, 92, 261, 20);
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

		quantity = new JTextField();
		quantity.setColumns(10);
		quantity.setBounds(351, 135, 39, 20);
		frame.getContentPane().add(quantity);

		lblCategory = new JLabel("Category");
		lblCategory.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblCategory.setBounds(55, 174, 58, 16);
		frame.getContentPane().add(lblCategory);

		//String[] book_categories = new String[] {"Science", "Art",
               // "Religion", "History", "Geography"};

		JComboBox<String> bookcategories = new JComboBox<String>();
		bookcategories.setFont(new Font("Times New Roman", Font.BOLD, 13));
		bookcategories.setBounds(129, 170, 97, 24);
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
		pub_name.setBounds(157, 210, 233, 20);
		frame.getContentPane().add(pub_name);

		lblAuthorNames = new JLabel("Author Name(s)");
		lblAuthorNames.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblAuthorNames.setBounds(55, 275, 103, 16);
		frame.getContentPane().add(lblAuthorNames);

		author_names = new JTextField();
		author_names.setColumns(10);
		author_names.setBounds(157, 273, 233, 20);
		frame.getContentPane().add(author_names);

		lblNewLabel_1 = new JLabel("Separate author names by (,). Ex. John Marc, Philip Kane");
		lblNewLabel_1.setForeground(Color.GRAY);
		lblNewLabel_1.setBounds(87, 302, 363, 14);
		frame.getContentPane().add(lblNewLabel_1);

		lblPublisherYear = new JLabel("Publisher Year");
		lblPublisherYear.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblPublisherYear.setBounds(55, 243, 97, 16);
		frame.getContentPane().add(lblPublisherYear);

		pub_year = new JTextField();
		pub_year.setColumns(10);
		pub_year.setBounds(157, 241, 68, 20);
		frame.getContentPane().add(pub_year);

		lblSellingPrice = new JLabel("Selling Price");
		lblSellingPrice.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblSellingPrice.setBounds(55, 327, 97, 16);
		frame.getContentPane().add(lblSellingPrice);

		selling_price = new JTextField();
		selling_price.setColumns(10);
		selling_price.setBounds(157, 327, 68, 20);
		frame.getContentPane().add(selling_price);

		btnAddBook = new JButton("Add Book");
		btnAddBook.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btnAddBook.setBounds(351, 359, 99, 50);
		frame.getContentPane().add(btnAddBook);
	}
}
