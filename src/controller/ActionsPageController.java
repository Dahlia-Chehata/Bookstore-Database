package controller;

import HelperClasses.NotFound;
import ModelsImplementation.CartManager;
import ModelsImplementation.UserManager;
import ModelsInterfaces.ICartManager;
import ModelsInterfaces.IUser;
import ModelsInterfaces.IUserManager;

public abstract class ActionsPageController {

	private IUser user;
	private IUserManager user_manager;
	private EditInformationController editinformation_controller;
	private SearchBookController searchbooks_controller;
	private int user_id;
	private CartManager cart_manager;
	
//	private GuiController gui_controller;

	public ActionsPageController(GuiController g, int userId) {
//		cart_manager = g.getCartManager();
//		this.gui_controller = g;
		this.user_id = userId;
		user_manager = new UserManager();
		user = user_manager.getById(userId);
	}


	public void editInformation() throws NotFound {
		editinformation_controller = new EditInformationController(this);

	}

	public void logout() {
//		ICartManager cart_manager = new CartManager();
		if (cart_manager != null) {
			cart_manager.flushCart(user);
		}
	}


	public IUser getUser() {
		return user;
	}


	public void search_books() {
		cart_manager = new CartManager();
		searchbooks_controller = new SearchBookController(this);
	}


	public int getUserId() {
		return user_id;
	}


	public void start() {
		// TODO Auto-generated method stub

	}


	public void reOpenActionsPage() {
		// TODO Auto-generated method stub

	}
	
	public boolean isManager() throws NotFound {
		if (user.getStatus().getName().equals("manager")) {
			return true;
		} else {
			return false;
		}
	}


	public CartManager getCartManager() {
		return cart_manager;
	}
}
