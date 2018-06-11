package controller;

import HelperClasses.NotFound;
import gui.books_handling.CustomerActionsPage;

public class Customer_controller extends ActionsPageController {

	GuiController g;
	private CustomerActionsPage customer_actionpage;

	public Customer_controller(GuiController g, int userId) {
		super(g, userId);
		this.g = g;
	}

	public void start() {
		customer_actionpage = new CustomerActionsPage(this);
	}

	@Override
	public void logout() {
		super.logout();
	}

	@Override
	public void editInformation() throws NotFound {
		super.editInformation();
	}

	@Override
	public void search_books() {
		super.search_books();
	}

	@Override
	public void reOpenActionsPage() {
		customer_actionpage.reOpen();
	}
}
