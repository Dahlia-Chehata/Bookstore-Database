package controller;

import java.util.ArrayList;
import java.util.Arrays;

import ModelsImplementation.Author;
import ModelsImplementation.AuthorManager;
import ModelsImplementation.BookManager;
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
	private int publishing_year;
	private double book_selling_price;
	private int threshold_int;



	public ManagerAddBookController(Manager_controller m) {
		this.m = m;
		add_book_page = new AddBook(this);

//		add_book();
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
		this.book_category = cat_manager.addCategory(category);
	}

	public void setAuthors(ArrayList<IAuthor> authors) {
		this.authors = authors;
	}

	public void setPub_information(String pub_name, String pub_address, String pub_telephone) {
		IPublisherManager pub_manager = new PublisherManager();
		this.book_publisher = pub_manager.addPublisher(pub_name,
				pub_address, pub_telephone);
	}

	public void setPub_year(String pub_year) {
		this.publishing_year = Integer.parseInt(pub_year);
	}

	public void setSelling_price(String selling_price) {
		this.book_selling_price = Double.parseDouble(selling_price);
	}

//	public void setAdd_book_page(AddBook add_book_page) {
//		this.add_book_page = add_book_page;
//	}

	public void setAuthors_before(String authors_before) {
		this.authors_before = authors_before;
		set_authors();
	}

	private void set_authors() {
		String[] authors_array = authors_before.split(",");
		IAuthorManager author_manager = new AuthorManager();
		IAuthor author;
		for (int i = 0; i < authors_array.length; i++) {
			author = author_manager.getOrAddAuthor(authors_array[i]);
			authors.add(author);
		}
	}

	private void add_book() {
		book_manager = new BookManager();
		book_manager.addBook(book_title, book_category, book_isbn, book_publisher,
				publishing_year, book_selling_price, available_quantity_int,
				threshold_int, quantity_to_be_orderd_int, authors);
	}


}
