package controller;

import HelperClasses.NotFound;
import ModelsInterfaces.IUser;
import gui.user_handling.EditUserInformation;

public class EditInformationController {

	private IUser user;
	private EditUserInformation edit_information_page;
	private String[] old_information;
	private ActionsPageController user_controller;

	public EditInformationController(ActionsPageController user_controller) throws NotFound {
		this.user_controller = user_controller;
		user = user_controller.getUser();
		setOldInformation();
		edit_information_page = new EditUserInformation(this, old_information);


	}


	private void setOldInformation() throws NotFound {
		old_information = new String[6];

		old_information[0] = user.getUsername();
		old_information[1] = user.getEmail();
//		old_information[2] = user.get;
		old_information[2] = user.getFName();
		old_information[3] = user.getLName();
		old_information[4] = user.getDefaultShippingAddress();
		old_information[5] = user.getPhone();

	}


	public void editInformation(String[] new_information) throws NotFound {
		user.changeUsername(new_information[0]);
		user.changeEmail(new_information[1]);
		user.changePassword(new_information[2]);
		user.changeFName(new_information[3]);
		user.changeLName(new_information[4]);
		user.changeDefaultShippingAddress(new_information[5]);
		user.changePhone(new_information[6]);

	}

}
