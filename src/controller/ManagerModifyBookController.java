package controller;

import gui.books_handling.ModifyBook;

public class ManagerModifyBookController {

	private Manager_controller m;
	private ModifyBook modifybook_page;
	private String text;

	public ManagerModifyBookController(Manager_controller manager_controller) {
		this.m = manager_controller;
		modifybook_page = new ModifyBook(this);

	}

	public void set_search_method(int number) { //0: ISBN   1: Title
		switch (number) {
		case 0: //ISBN
			//search by ISBN
			break;
		case 1: //Title
			//search by Title
			break;
		default:
			break;
		}

	}

	public void set_text(String text) {
		this.text = text;
	}

}
