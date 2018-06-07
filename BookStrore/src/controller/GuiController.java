package controller;

import HelperClasses.NotFound;
import ModelsImplementation.UserManager;
import ModelsImplementation.UserStatus;
import ModelsInterfaces.IUser;
import ModelsInterfaces.IUserManager;
import gui.user_handling.LoginPage;
import gui.user_handling.SignupPage;

public class GuiController {

	static GuiController g;

	IUser loggedin_user;
	static IUserManager user_manager;

	private String[] login_information;
	private String[] signup_information;


	/**
	 * boolean to show if the information entered by the user (logging in or signing up)
	 * are correct or no
	 */
	private static boolean pass_login_signup;
	private static boolean is_manager;

	public GuiController() {

		g = this;

		user_manager = new UserManager();

		pass_login_signup = false;
	}


	public static void main(String[] args) {
		int action_performed = start_screen();
		switch (action_performed) {
		case 1: //Login to an already available user
			System.out.println(action_performed + " Log in");
			user_log_in();
			break;

		case 2: //Sign up to a new user
			System.out.println(action_performed + " Sign up");
			user_signup();
			break;
		}




	}

	private static int start_screen() {
		StartPage start_page = new StartPage();

		int action_performed = start_page.get_action();

		while (action_performed == 0) { //nothing chosen yet
			action_performed = start_page.get_action();
		}
		start_page.hide_frame();
		return action_performed;

	}

	private static void user_signup() {
		SignupPage signup_page = new SignupPage();
	}


	private static void user_log_in() {
		LoginPage login_page = new LoginPage();

		while (!pass_login_signup) {
			System.out.println("\nPass login = " + pass_login_signup + "\n");
		}
		//pass_login_signup = true;
		login_page.hide_frame();
		//is_manager = true;
		if (is_manager) {
			Manager_controller manager_controller = new Manager_controller(g);
			manager_controller.start();
		} else {
			Customer_controller customer_controller = new Customer_controller(g);
			customer_controller.start();
		}

	}

	public void set_signup_information(String[] signup_information) throws NotFound {
		this.signup_information = signup_information;
		send_signup_information();
	}



	private void send_signup_information() throws NotFound {
		user_manager.addUser(signup_information[0], signup_information[1],
				signup_information[2], signup_information[3], signup_information[4],
				signup_information[5], signup_information[6], new UserStatus(0));
	}

	public boolean set_login_information(String[] login_information) throws NotFound {
		this.login_information = login_information;

		if (!send_login_information()) { //false information
			pass_login_signup = false;

			return false;
		}
		String status = loggedin_user.getStatus().getName();
		if (status.equals("manager")) {
			is_manager = true;
		} else {
			is_manager = false;
		}
		//is_manager = true;
		pass_login_signup = true;
		return true;
	}

	private boolean send_login_information() throws NotFound {
		return true;
//		loggedin_user = user_manager.getByEmailAndPassword(login_information[0], login_information[1]);
//		if (loggedin_user != null) { //An available user
//			return true;
//		} else { //user not found or password doesn't match
//			return false;
//		}
	}


}
