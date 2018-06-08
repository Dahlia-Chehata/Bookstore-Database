package controller;

import gui.books_handling.ManagerActionsPage;

public class Manager_controller {

	private int books_counter;
	private GuiController g;
	private ManagerActionsPage manager_action_page;
	private ManagerAddBookController addbook_controller;
	private ManagerModifyBookController modifybook_controller;
//	private AddBook add_book_page;
	private ManagerSearchBookController searchbooks_controller;

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

	public void modify_book() {
		modifybook_controller = new ManagerModifyBookController(this);

	}
	public void search_books() {
		searchbooks_controller = new ManagerSearchBookController(this);
	}

	public void promote_user() {

	}

	public void place_orders() {

	}

	public void view_reports() {

	}
}
