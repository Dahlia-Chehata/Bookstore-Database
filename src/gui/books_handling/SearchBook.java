package gui.books_handling;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;

import HelperClasses.NotFound;
import controller.SearchBookController;

import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SearchBook {

	private JFrame frame;
	private JTextField textField_isbn;
	private JLabel lblIsbn;
	private JTextField title;
	private JLabel lblTitle;
	private JLabel lblThreshold;
	private JTextField threshold_min;
	private JLabel lblCategory;
	private JLabel lblPublisherName;
	private JTextField pub_name;
	private JLabel lblAuthorNames;
	private JTextField author_names;
	private JLabel lblNewLabel_1;
	private JLabel lblPublisherYear;
	private JTextField pub_year_old;
	private JLabel lblSellingPriceMin;
	private JTextField selling_price_min;
	private JButton btnSearch;
	private JComboBox<String> bookcategories;
	private JLabel lblAvailableQuantity;

	private SearchBookController seachbook_controller;
	private JTextField threshold_max;
	private JLabel lblPublicationYearnewest;
	private JTextField pub_year_new;
	private JTextField avail_quant_min;
	private JLabel lblAvailableQuantityMax;
	private JTextField avail_quant_max;
	private JTextField selling_price_max;
	private JLabel lblSellingPriceMax;

	/**
	 * Create the application.
	 */
	public SearchBook(SearchBookController m) {
		this.seachbook_controller = m;
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

		JLabel lblNewLabel = new JLabel("Search");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel.setBounds(29, 11, 155, 31);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblBookId = new JLabel("ISBN");
		lblBookId.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblBookId.setBounds(55, 55, 64, 16);
		frame.getContentPane().add(lblBookId);

		textField_isbn = new JTextField();
		textField_isbn.setBounds(129, 53, 86, 20);
		frame.getContentPane().add(textField_isbn);
		textField_isbn.setColumns(10);

		title = new JTextField();
		title.setColumns(10);
		title.setBounds(139, 92, 261, 20);
		frame.getContentPane().add(title);

		lblTitle = new JLabel("Book title");
		lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblTitle.setBounds(55, 94, 64, 16);
		frame.getContentPane().add(lblTitle);

		lblThreshold = new JLabel("Threshold Min");
		lblThreshold.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblThreshold.setBounds(55, 137, 97, 16);
		frame.getContentPane().add(lblThreshold);

		threshold_min = new JTextField();
		threshold_min.setColumns(10);
		threshold_min.setBounds(168, 135, 34, 20);
		frame.getContentPane().add(threshold_min);

		lblCategory = new JLabel("Category");
		lblCategory.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblCategory.setBounds(233, 57, 58, 16);
		frame.getContentPane().add(lblCategory);


		bookcategories = new JComboBox<String>();
		bookcategories.setFont(new Font("Times New Roman", Font.BOLD, 13));
		bookcategories.setBounds(303, 53, 97, 24);
		bookcategories.addItem("");
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
		lblAuthorNames.setBounds(55, 306, 103, 16);
		frame.getContentPane().add(lblAuthorNames);

		author_names = new JTextField();
		author_names.setColumns(10);
		author_names.setBounds(157, 304, 233, 20);
		frame.getContentPane().add(author_names);

//		lblNewLabel_1 = new JLabel("Separate author names by (,). Ex. John Marc, Philip Kane");
//		lblNewLabel_1.setForeground(Color.GRAY);
//		lblNewLabel_1.setBounds(87, 333, 363, 14);
//		frame.getContentPane().add(lblNewLabel_1);

		lblPublisherYear = new JLabel("Publication Year (oldest)");
		lblPublisherYear.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblPublisherYear.setBounds(55, 246, 140, 16);
		frame.getContentPane().add(lblPublisherYear);

		pub_year_old = new JTextField();
		pub_year_old.setColumns(10);
		pub_year_old.setBounds(209, 245, 68, 20);
		frame.getContentPane().add(pub_year_old);

		lblSellingPriceMin = new JLabel("Selling Price Min");
		lblSellingPriceMin.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblSellingPriceMin.setBounds(55, 358, 110, 16);
		frame.getContentPane().add(lblSellingPriceMin);

		selling_price_min = new JTextField();
		selling_price_min.setColumns(10);
		selling_price_min.setBounds(180, 358, 68, 20);
		frame.getContentPane().add(selling_price_min);

		btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				send_search_information();
				try {
					seachbook_controller.search();
				} catch (NotFound e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}


		});
		btnSearch.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btnSearch.setBounds(351, 433, 99, 50);
		frame.getContentPane().add(btnSearch);

		lblAvailableQuantity = new JLabel("Available quantity Min");
		lblAvailableQuantity.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblAvailableQuantity.setBounds(55, 168, 129, 20);
		frame.getContentPane().add(lblAvailableQuantity);

		JLabel lblThresholdMax = new JLabel("Threshold Max");
		lblThresholdMax.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblThresholdMax.setBounds(243, 139, 97, 16);
		frame.getContentPane().add(lblThresholdMax);

		threshold_max = new JTextField();
		threshold_max.setColumns(10);
		threshold_max.setBounds(356, 137, 34, 20);
		frame.getContentPane().add(threshold_max);

		lblPublicationYearnewest = new JLabel("Publication Year (newest)");
		lblPublicationYearnewest.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblPublicationYearnewest.setBounds(55, 274, 140, 16);
		frame.getContentPane().add(lblPublicationYearnewest);

		pub_year_new = new JTextField();
		pub_year_new.setColumns(10);
		pub_year_new.setBounds(209, 273, 68, 20);
		frame.getContentPane().add(pub_year_new);

		avail_quant_min = new JTextField();
		avail_quant_min.setColumns(10);
		avail_quant_min.setBounds(191, 166, 34, 20);
		frame.getContentPane().add(avail_quant_min);

		avail_quant_max = new JTextField();
		avail_quant_max.setColumns(10);
		avail_quant_max.setBounds(366, 168, 34, 20);
		frame.getContentPane().add(avail_quant_max);

		lblAvailableQuantityMax = new JLabel("Available quantity Max");
		lblAvailableQuantityMax.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblAvailableQuantityMax.setBounds(233, 168, 129, 20);
		frame.getContentPane().add(lblAvailableQuantityMax);

		selling_price_max = new JTextField();
		selling_price_max.setColumns(10);
		selling_price_max.setBounds(180, 386, 68, 20);
		frame.getContentPane().add(selling_price_max);

		lblSellingPriceMax = new JLabel("Selling Price Max");
		lblSellingPriceMax.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblSellingPriceMax.setBounds(55, 386, 110, 16);
		frame.getContentPane().add(lblSellingPriceMax);
	}

	private void send_search_information() {
		if (!(author_names.getText().isEmpty())) {
			seachbook_controller.setAuthors_before(author_names.getText());
		}
		seachbook_controller.setBook_title(title.getText());
		seachbook_controller.setAvailable_quantity(avail_quant_min.getText(), avail_quant_max.getText());
		seachbook_controller.setCategory(bookcategories.getSelectedItem().toString());
		seachbook_controller.setBook_isbn(textField_isbn.getText());
		seachbook_controller.setPub_name(pub_name.getText());
		seachbook_controller.setSelling_price(selling_price_min.getText(), selling_price_max.getText());
		seachbook_controller.setPub_year(pub_year_old.getText(), pub_year_new.getText());
		seachbook_controller.setThreshold(threshold_min.getText(), threshold_max.getText());

	}
}
