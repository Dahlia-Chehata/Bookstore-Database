package controller;

import HelperClasses.NotFound;
import ModelsImplementation.UserManager;
import ModelsImplementation.UserStatus;
import ModelsInterfaces.IUser;
import gui.user_handling.PromoteUser;

public class ManagerPromoteUserController {

	Manager_controller manager_controller;
	private PromoteUser promoteuser_page;
	private UserManager user_manager;

	public ManagerPromoteUserController(Manager_controller manager_controller) {
		this.manager_controller = manager_controller;
		promoteuser_page = new PromoteUser(this);

	}

	public boolean promote(String email) throws NotFound {
		user_manager = new UserManager();
		IUser user = user_manager.getByEmail(email);

		if (user != null) { //user found
			user.changeStatus(new UserStatus(1));
			return true;
		} else { //no user
			return false;
		}
	}



}
