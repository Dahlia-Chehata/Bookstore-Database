package controller;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import HelperClasses.NotFound;
import ModelsImplementation.AuthorManager;
import ModelsImplementation.BookManager;
import ModelsImplementation.BooksGetter;
import ModelsImplementation.CategoryManager;
import ModelsImplementation.PublisherManager;
import ModelsInterfaces.IAuthor;
import ModelsInterfaces.IAuthorManager;
import ModelsInterfaces.IBookManager;
import ModelsInterfaces.ICategory;
import ModelsInterfaces.ICategoryManager;
import ModelsInterfaces.IPublisher;
import ModelsInterfaces.IPublisherManager;
import gui.books_handling.AddBook;

public class ManagerAddBookController {

	Manager_controller m ;

	/*
	 * isbn.getText().isEmpty() || title.getText().isEmpty()
				|| threshold.getText().isEmpty() || quantity.getText().isEmpty()
				|| pub_name.getText().isEmpty() || author_names.getText().isEmpty()
				|| pub_year.getText().isEmpty() || selling_price.getText().isEmpty())

	 */

	private IBookManager book_manager;
	private AddBook add_book_page;

	private String book_title;
	private String book_isbn;
	private int available_quantity_int;
	private int quantity_to_be_orderd_int;
	private String authors_before; //m7tag ta3deelat (remove ,)
	private ArrayList<IAuthor> authors;
	private ICategory book_category;
	private IPublisher book_publisher;
	ArrayList<IPublisher> publishers; //all publishers
	private int publishing_year;
	private double book_selling_price;
	private int threshold_int;
	IPublisherManager pub_manager ;

	private int book_number;


	public ManagerAddBookController(Manager_controller m) throws NotFound {
		this.m = m;
		this.book_number = m.getBookCount();
		this.pub_manager = new PublisherManager();
		publishers = pub_manager.getAllPublishers();
		add_book_page = new AddBook(this);

	}
	public void setBook_title(String book_title) {
		this.book_title = book_title;
	}

	public void setBook_isbn(String book_isbn) {
		this.book_isbn = book_isbn;
	}

	public void setThreshold(String threshold) {
		this.threshold_int = Integer.parseInt(threshold);
	}

	public void setAvailable_quantity(String available_quantity) {
		this.available_quantity_int = Integer.parseInt(available_quantity);
	}

	public void setQuantity_to_be_orderd(String quantity_to_be_orderd) {
		this.quantity_to_be_orderd_int = Integer.parseInt(quantity_to_be_orderd);

	}

	public void setCategory(String category) {
		ICategoryManager cat_manager = new CategoryManager();
		this.book_category = cat_manager.getCategoryByName(category);
//		this.book_category = cat_manager.addCategory(category);
	}

//	public void setAuthors(ArrayList<IAuthor> authors) {
//		this.authors = authors;
//	}

	public ArrayList<String> getPublisherNames() throws NotFound {

		ArrayList<String> publishers_names = setPublishersNames(publishers);
		return publishers_names;
	}

	public ArrayList<String> getPublishersPhones() throws NotFound {
		ArrayList<String> publishers_phones = setPublishersPhones(publishers);
		return publishers_phones;
	}

	private ArrayList<String> setPublishersPhones(ArrayList<IPublisher> publishers) throws NotFound {

		ArrayList<String> publishers_phones = new ArrayList<>();
		for (IPublisher pub : publishers) {
			publishers_phones.add(pub.getTelephone());
		}
		return publishers_phones;
	}

	private ArrayList<String> setPublishersNames(ArrayList<IPublisher> publishers) throws NotFound {
		ArrayList<String> publishers_names = new ArrayList<>();
		for (IPublisher pub : publishers) {
			publishers_names.add(pub.getName());
		}
		return publishers_names;
	}

	public void setPub_information(String pub_name_and_telephone) throws NotFound {
		String[] publisher_information = pub_name_and_telephone.split(" - ");
		for (IPublisher pub : publishers) {
			if (pub.getName().trim() .equals(publisher_information[0].trim()) &&
				 pub.getTelephone().trim().equals(publisher_information[1].trim())) {
					//correct publisher
					book_publisher = pub;
					System.out.println(book_publisher);
					break;
			}
		}
	}

	public void setPub_year(String pub_year) {
		this.publishing_year = Integer.parseInt(pub_year);
	}

	public void setSelling_price(String selling_price) {
		this.book_selling_price = Double.parseDouble(selling_price);
	}

	public void setAuthors_before(String authors_before) {
		this.authors_before = authors_before;
		set_authors();
	}

	private void set_authors() {
		authors = new ArrayList<>();
		String[] authors_array = authors_before.split(",");
		IAuthorManager author_manager = new AuthorManager();
		IAuthor author;
		for (int i = 0; i < authors_array.length; i++) {
			author = author_manager.getOrAddAuthor(authors_array[i]);
			authors.add(author);
		}
	}

	public void add_book() {
		book_manager = new BookManager();
		
		if ((book_manager.addBook(book_title, book_category, book_isbn, book_publisher,
				publishing_year, book_selling_price,available_quantity_int, threshold_int, authors))==null)
			JOptionPane.showMessageDialog(null,
		            "Duplicate entry for books!","Try again", JOptionPane.ERROR_MESSAGE);
	}

	public int getBooks_counter() {
		return book_number;
	}


}
