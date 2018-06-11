package controller;

import HelperClasses.NotFound;
import ModelsImplementation.BooksGetter;
import ModelsImplementation.UserManager;
import ModelsInterfaces.IUser;
import ModelsInterfaces.IUserManager;
import gui.books_handling.ManagerActionsPage;

public class Manager_controller extends ActionsPageController {

	private int books_counter;
	private int userId;

	private GuiController g;
	private ManagerActionsPage manager_action_page;
	private ManagerAddBookController addbook_controller;
	private ManagerModifyBookController modifybook_controller;
//	private AddBook add_book_page;
	private SearchBookController searchbooks_controller;
	private ManagerPromoteUserController promoteuser_controller;
	private ManagerAddPublisherController addpublisher_controller;
	private EditInformationController editinformation_controller;
	private IUserManager user_manager;
	private IUser user;
	private ManagerViewReportsController viewreports_controller;

	public Manager_controller(GuiController g, int userId) {
		super(g, userId);
		this.g = g;
		books_counter = new BooksGetter().booksCount()+1;
		this.userId = userId;
		user_manager = new UserManager();
		user = user_manager.getById(userId);
	}

	public void start() {
		manager_action_page = new ManagerActionsPage(this);
//		manager_action_page.set_manager_controller(this);
	}

	public void add_book() throws NotFound {

		addbook_controller = new ManagerAddBookController(this);
		books_counter++;
//		add_book_page = new AddBook(this);
	}

	public int getBooks_counter() {
		return books_counter;
	}

	public void modify_book() {
		modifybook_controller = new ManagerModifyBookController(this);

	}

	@Override
	public void search_books() {
//		searchbooks_controller = new SearchBookController(this);
		super.search_books();
	}


	public void promote_user() {
		promoteuser_controller = new ManagerPromoteUserController(this);
	}


	public void view_reports(String button_selected) {
		viewreports_controller = new ManagerViewReportsController(this, button_selected);
	}

	@Override
	public int getUserId() {
		return userId;
	}

	@Override
	public IUser getUser() {
		return user;
	}

	public void addPublisher() {
		addpublisher_controller = new ManagerAddPublisherController(this);
	}

	@Override
	public void editInformation() throws NotFound {
//		editinformation_controller = new EditInformationController(this);
		super.editInformation();
	}

	public int getBookCount() {
		return books_counter;
	}

	public void gotoHomePage() {
		manager_action_page = new ManagerActionsPage(this);
	}

	@Override
	public void logout() {
		super.logout();
	}

	@Override
	public void reOpenActionsPage() {
		manager_action_page.reOpen();
	}
}
