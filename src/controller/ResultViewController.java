package controller;

import java.util.ArrayList;

import HelperClasses.NotFound;
import ModelsImplementation.Book;
import ModelsImplementation.BooksGetter;
import ModelsImplementation.CartManager;
import ModelsImplementation.PublisherManager;
import ModelsImplementation.UserManager;
import ModelsInterfaces.IAuthor;
import ModelsInterfaces.IBook;
import ModelsInterfaces.IBooksGetter;
import ModelsInterfaces.IPublisher;
import ModelsInterfaces.IPublisherManager;
import ModelsInterfaces.IUser;
import ModelsInterfaces.IUserManager;
import gui.books_handling.ManagerResultView;
import gui.books_handling.UserResultView;

public class ResultViewController {

	private SearchBookController searchbook_controller;
	private IResultView result_view;
	private boolean is_manager;
	private String[][] data;
	private ArrayList<IBook> books_returned;
	private CartManager cart_manager;
	private int userId;
	private ViewCartController cart_viewer;
	private IUserManager user_manager;
	private IUser user;
	ArrayList<String> publishers_names;
	ArrayList<String> publishers_phones;
	private BooksGetter getter_copy;
	private int size;
	private IBooksGetter getter;
	private String[] column_names;

	public ResultViewController(SearchBookController searchBookController, int user_id, boolean is_manager) throws NotFound {
		this.searchbook_controller = searchBookController;
		user_manager = new UserManager();
		this.is_manager = is_manager;
		this.userId = user_id;
		user = user_manager.getById(user_id);

		try {
			populate_result();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		openResultViewPage();

		//		result_view = new UserResultView();
	}

	private void openResultViewPage() throws NotFound {
		if (is_manager) {
			result_view = new ManagerResultView(this, column_names, size);
		} else {
			result_view = new UserResultView(this, column_names, size);
		}
//		result_view.setTotal_size_of_data(size);

	}

	public void setSearchbook_controller(SearchBookController searchbook_controller) {
		this.searchbook_controller = searchbook_controller;
	}

	void populate_result() throws CloneNotSupportedException {
		if (is_manager) {
			populate_manager_result_view();
		} else {
			populate_customer_result_view();
		}

		getter = searchbook_controller.getBookGetter();

		getter_copy = getter.clone();

		size = getter.booksCount(); //number of results

//		getter = getter_copy.clone();
	}


	private void populate_customer_result_view() {
		/*
		 * = {"ISBN", "Title", "Author Name(s)",
				"Publisher Name", "Available Quantity", "Buy"};
		 */
		column_names = new String[6];
		column_names[0] = "ISBN";
		column_names[1] = "Title";
		column_names[2] = "Author Name(s)";
		column_names[3] = "Publisher Name";
		column_names[4] = "Available Quantity";
		column_names[5] = "Buy";
		
	}

	private void populate_manager_result_view() {
//		String[] column_names = {"ISBN", "Title", "Author Name(s)",
//				"Publisher Name", "Available Quantity", "Buy", "Order"};
		column_names = new String[7];
		column_names[0] = "ISBN";
		column_names[1] = "Title";
		column_names[2] = "Author Name(s)";
		column_names[3] = "Publisher Name";
		column_names[4] = "Available Quantity";
		column_names[5] = "Buy";
		column_names[6] = "Order";
	}

	public String[][] getData(int i) throws NotFound { //the ith time
		for (int j = 0; j < size; j = j + 15) {
			try {
				int k = 0;
				getter = getter_copy.clone();
				ArrayList<IBook> a = getter.get(15, i);
				data = generateBooksData(a);

			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return data;
	}

	private String[][] generateBooksData(ArrayList<IBook> a) throws NotFound {
		String[][] data_generated;
		if (is_manager) {
			data_generated = new String[a.size()][7];
		} else {
			data_generated = new String[a.size()][6];
		}

		ArrayList<IAuthor> authors;
		String authors_concatenated;

		int i = 0;
		for(IBook book : a) {
			int l = 0;
			authors = book.getAuthors();
			authors_concatenated = changeToStr(authors);
			//"ISBN", "Title", "Author Name(s)",
//			"Publisher Name", "Available Quantity", "Buy", "Order"};
			data_generated[i][0] = book.getISBN();
			data_generated[i][1] = book.getTitle();
			data_generated[i][2] = authors_concatenated;
			data_generated[i][3] = book.getPublisher().getName();
			data_generated[i][4] = String.valueOf(book.getAvailableQuantity());
			data_generated[i][5] = "Buy";
			if (is_manager) {
				data_generated[i][6] = "Order";
			}
			i++;
		}

		return data_generated;
	}

	private String changeToStr(ArrayList<IAuthor> authors) {
		String str = "";
		int size = authors.size();
		for (int i = 0; i < size; i++) {
			str = str + authors.get(i).getName();
			if (i != size - 1) {
				str = str + "\n";
			}
		}
		return str;
	}

	public boolean add_to_cart(String ISBN, int number) throws NotFound {
		cart_manager = new CartManager();
		IBook book = new Book(ISBN);
//		IUser user = new User(userId);
		if (cart_manager.addBook(user, book, number)) {
			return true;
		} else {
			return false;
		}
	}

	public void viewCart() {
		try {
			cart_viewer = new ViewCartController(this);
		} catch (NotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public IUser getUser() {
		return user;
	}

	public boolean order_book(String iSBN, int label) {

		return false;
	}

	public ArrayList<String> getPublisherNames() {
		publishers_names = new ArrayList<>();
		publishers_phones = new ArrayList<>();
		IPublisherManager publisher_manager = new PublisherManager();
		ArrayList<IPublisher> publishers = publisher_manager.getAllPublishers();
		for (IPublisher p : publishers) {
			try {
				publishers_names.add(p.getName());
				publishers_phones.add(p.getTelephone());
			} catch (NotFound e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public ArrayList<String> getPublisherPhones() {
		return publishers_phones;
	}

}
