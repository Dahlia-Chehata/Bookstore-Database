package controller;

import ModelsInterfaces.IUser;
import ModelsInterfaces.IUserManager;
import gui.user_handling.LoginPage;

public class GuiController {

	IUser loggedin_user;
	IUserManager user_manager;

	private String[] login_information;
	private String[] signup_information;


	public GuiController() {
		user_manager = new User

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

	private static void user_log_in() {
		LoginPage login_page = new LoginPage();
	}


	public void set_signup_information(String[] signup_information) {
		this.signup_information = signup_information;
		send_signup_information();
	}



	private void send_signup_information() {
		//to database
	}

	public boolean set_login_information(String[] login_information) {
		this.login_information = login_information;
		if (!send_login_information()) {
			return false;
		}
		return true;
	}

	private boolean send_login_information() {
		loggedin_user = user_manager.getByEmailAndPassword(login_information[0], login_information[1]);
		if (loggedin_user != null) { //An available user
			return true;
		} else { //user not found or password doesn't match
			return false;
		}
	}


}
