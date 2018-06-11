//package controller;
//
//import java.util.ArrayList;
//
//import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;
//
//import ModelsImplementation.AuthorManager;
//import ModelsImplementation.BooksGetter;
//import ModelsImplementation.CategoryManager;
//import ModelsImplementation.PublisherManager;
//import ModelsInterfaces.IAuthor;
//import ModelsInterfaces.IAuthorManager;
//import ModelsInterfaces.IBooksGetter;
//import ModelsInterfaces.ICategory;
//import ModelsInterfaces.ICategoryManager;
//import ModelsInterfaces.IPublisher;
//import ModelsInterfaces.IPublisherManager;
//import gui.books_handling.SearchBook;
//
//public class ManagerSearchBookController {
//
//	public IBooksGetter getter;
//
//	private Manager_controller m;
//	private ResultViewController result;
//
//	private String book_title;
//	private String book_isbn;
//	private int available_quantity_min_int;
//	private int quantity_to_be_orderd_int;
//	private String authors_before; //m7tag ta3deelat (remove ,)
//	private ArrayList<IAuthor> authors;
//	private ICategory book_category;
//	private IPublisher book_publisher;
//	private int publishing_year_old;
//	private double book_selling_price_min;
//	private int threshold_min_int;
//
//	private int threshold_max_int;
//
//	private int available_quantity_max_int;
//
//	private String pub_name;
//
//	private int publishing_year_new;
//
//	private double book_selling_price_max;
//
//	private int userId;
//
//	private boolean is_manager;
//
//	private SearchBook search_page;
//
//	public ManagerSearchBookController(Manager_controller m) {
//		this.m = m;
//		userId = m.getUserId();
//	}
//
//
//	public void setBook_title(String book_title) {
//		this.book_title = book_title;
//	}
//
//	public void setBook_isbn(String book_isbn) {
//		this.book_isbn = book_isbn;
//	}
//
//	public void setThreshold(String threshold_min, String threshold_max) {
//		try {
//			this.threshold_min_int = Integer.parseInt(threshold_min);
//		} catch (NumberFormatException n) { //empty field
//			this.threshold_min_int = Integer.parseInt("-1");
//		}
//		try {
//			this.threshold_max_int = Integer.parseInt(threshold_max);
//
//		} catch (NumberFormatException e) {
//			this.threshold_max_int = Integer.parseInt("-1");
//		}
//		if ((this.threshold_min_int == -1) && (this.threshold_max_int != -1)) { //there is a max number
//			this.threshold_min_int = 0;
//		} else if ((this.threshold_min_int != -1) && (this.threshold_max_int == -1)) { //ther is a min mumber
//			this.threshold_max_int = Integer.MAX_VALUE;
//		}
//	}
//
//	public void setAvailable_quantity(String available_quantity_min, String available_quantity_max) {
//		try {
//			this.available_quantity_min_int = Integer.parseInt(available_quantity_min);
//		} catch (NumberFormatException n) { //empty field
//			this.available_quantity_min_int = Integer.parseInt("-1");
//		}
//		try {
//			this.available_quantity_max_int = Integer.parseInt(available_quantity_max);
//
//		} catch (NumberFormatException e) {
//			this.available_quantity_max_int = Integer.parseInt("-1");
//		}
//		if ((this.available_quantity_min_int == -1) && (this.available_quantity_max_int != -1)) { //there is a max number
//			this.available_quantity_min_int = 0;
//		} else if ((this.available_quantity_min_int != -1) && (this.available_quantity_max_int == -1)) { //ther is a min mumber
//			this.available_quantity_max_int = Integer.MAX_VALUE;
//		}
//	}
//
//	public void setCategory(String category) {
//		if (category.length() != 0) {
//			ICategoryManager cat_manager = new CategoryManager();
//			this.book_category = cat_manager.addCategory(category);
//		}
//
//	}
//
////	public void setAuthors(ArrayList<IAuthor> authors) {
////		this.authors = authors;
////	}
//
//	public void setPub_name(String pub_name) {
//		this.pub_name = pub_name;
//	}
//
//	public void setPub_year(String pub_year_old, String pub_year_new) {
//
//		try {
//			this.publishing_year_old = Integer.parseInt(pub_year_old);
//
//		} catch (NumberFormatException n) { //empty field
//			this.publishing_year_old = Integer.parseInt("-1");
//		}
//		try {
//			this.publishing_year_new = Integer.parseInt(pub_year_new);
//
//		} catch (NumberFormatException e) {
//			this.publishing_year_new = Integer.parseInt("-1");
//		}
//		if ((this.publishing_year_old == -1) && (this.publishing_year_new != -1)) { //there is a max number
//			this.publishing_year_old = 0;
//		} else if ((this.publishing_year_old != -1) && (this.publishing_year_new == -1)) { //ther is a min mumber
//			this.publishing_year_new = Integer.MAX_VALUE;
//		}
//	}
//
//	public void setSelling_price(String selling_price_min, String selling_price_max) {
//		try {
//			this.book_selling_price_min = Double.parseDouble(selling_price_min);
//
//		} catch (NumberFormatException n) { //empty field
//			this.book_selling_price_min = Integer.parseInt("-1");
//		}
//
//		try {
//			this.book_selling_price_max = Double.parseDouble(selling_price_max);
//
//		} catch (NumberFormatException e) {
//			this.book_selling_price_max = Integer.parseInt("-1");
//		}
//		if ((this.book_selling_price_min == -1) && (this.book_selling_price_max != -1)) { //there is a max number
//			this.book_selling_price_min = 0;
//		} else if ((this.book_selling_price_min != -1) && (this.book_selling_price_max == -1)) { //ther is a min mumber
//			this.book_selling_price_max = Integer.MAX_VALUE;
//		}
//
//	}
//
//	public void setAuthors_before(String authors_before) {
//		this.authors_before = authors_before;
//		set_authors();
//	}
//
//	private void set_authors() {
//		String[] authors_array = authors_before.split(",");
//		IAuthorManager author_manager = new AuthorManager();
//		IAuthor author;
//		for (int i = 0; i < authors_array.length; i++) {
//			author = author_manager.getOrAddAuthor(authors_array[i]);
//			authors.add(author);
//		}
//	}
//
//	public void search() {
//		getter = new BooksGetter();
//
//		if (book_isbn.length() != 0) { //search by isbn
//			getter = getter.getBooksByISBN(book_isbn);
//		}
//		if (book_title.length() != 0) {
//			getter = getter.getBooksByTitle(book_title);
//		}
//		if (pub_name.length() != 0) {
////			getter = getter.getBooksByPublisher(pub_name);
//		}
//		if (authors.size() != 0) {
//			for (int i = 0; i < authors.size(); i++) {
////				getter = getter.getBooksByAuthor(authors[i]);
//			}
//		}
//		if (book_category != null) {
//			getter = getter.getBooksByCategory(book_category);
//		}
//		if ((threshold_min_int != -1) && (threshold_max_int != -1)) { //search by threshold
//			getter = getter.getBooksByThreshold(threshold_min_int, threshold_max_int);
//		}
//		if ((publishing_year_new != -1) && (publishing_year_old != -1)) {
//			getter = getter.getBooksByPublicationYear(publishing_year_old, publishing_year_new);
//		}
//		if ((book_selling_price_min != -1) && (book_selling_price_max != -1)) {
//			getter = getter.getBooksBySellingPrice(book_selling_price_min, book_selling_price_max);
//		}
//		if ((available_quantity_min_int != -1) && (available_quantity_max_int != -1)) {
//			getter = getter.getBooksByAvailability(available_quantity_min_int, available_quantity_max_int);
//		}
//
//
////		result = new ResultViewController(userId, is_manager);
////		result.setSearchbook_controller(this);
//
//
//
//	}
//
//	public IBooksGetter getBookGetter() {
//		return getter;
//	}
//}
