package controller;

import gui.books_handling.AddBook;
import gui.books_handling.ManagerActionsPage;

public class Manager_controller {

	private int books_counter;
	private GuiController g;
	private ManagerActionsPage manager_action_page;
	private ManagerAddBookController addbook_controller;
//	private AddBook add_book_page;

	public Manager_controller(GuiController g) {
		this.g = g;
		books_counter = 1;
	}

	public void start() {
		manager_action_page = new ManagerActionsPage();
		manager_action_page.set_manager_controller(this);
	}

	public void add_book() {
		addbook_controller = new ManagerAddBookController(this);
//		add_book_page = new AddBook(this);
	}

	public int getBooks_counter() {
		return books_counter;
	}

}
