package controller;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import HelperClasses.NotFound;
import ModelsInterfaces.IBook;
import ModelsInterfaces.IBooksGetter;
import gui.books_handling.ModifyBook;
import gui.books_handling.ModifyBookPage;

public class ManagerModifyBookController {

	private Manager_controller manager_controller;
	private ModifyBook modifybook_page;
	private ModifyBookPage modifybook_page_book;
	private String text;
	private SearchBookController searchbook_controller;
	private IBook book_to_be_modified;

	public ManagerModifyBookController(Manager_controller manager_controller) {
		this.manager_controller = manager_controller;
		searchbook_controller = new SearchBookController(true, this);
		modifybook_page = new ModifyBook(this);

	}

	public void set_search_method(int number) { //0: ISBN   1: Title
		switch (number) {
		case 0: //ISBN
			searchbook_controller.setBook_isbn(text);
			//search by ISBN
			break;
		case 1: //Title
			searchbook_controller.setBook_title(text);
			break;
		default:
			break;
		}

	}

	public void set_text(String text) {
		this.text = text;
	}

	public void search() throws NotFound {
		searchbook_controller.search();
	}

	public void setBookGetter(IBooksGetter getter) throws NotFound {
		if (!(getter.get().isEmpty())) {
			book_to_be_modified = getter.get().get(0);
			modifybook_page_book = new ModifyBookPage(this, book_to_be_modified);
		}else {
			JOptionPane.showMessageDialog(null,
			"No such item!","Try again", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void setAuthors_before(String text2) {
		// TODO Auto-generated method stub
		
	}

	public void setAvailable_quantity(String text2) {
   	try {
		if (!book_to_be_modified.changeAvailableQuantity(Integer.parseInt(text2)))
			JOptionPane.showMessageDialog(null,
		"ERROR in Available quantity update!","Try again", JOptionPane.ERROR_MESSAGE);
	} catch (NumberFormatException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (NotFound e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	}

	public void setCategory(String string) {
		if (string.length() != 0) {
//			book_to_be_modified.changeCategory(newCategory);
		}
	}
	public void setBook_title(String text2) {
		try {
			if (!book_to_be_modified.changeTitle(text2))
				JOptionPane.showMessageDialog(null,
				"ERROR in title update!","Try again", JOptionPane.ERROR_MESSAGE);
		} catch (NotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}
	public void setBook_isbn(String text2) {
		try {
			if (!book_to_be_modified.changeISBN(text2))
				JOptionPane.showMessageDialog(null,
			"ERROR in ISBN update!","Try again", JOptionPane.ERROR_MESSAGE);
		} catch (NotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}

	public void setPub_information(String string) {
		// TODO Auto-generated method stub
		
	}

	public void setPub_year(String text2) {
		try {
			if (!book_to_be_modified.changePublicationYear(Integer.parseInt(text2)))
				JOptionPane.showMessageDialog(null,
		"ERROR in publication year update!","Try again", JOptionPane.ERROR_MESSAGE);
		} catch (NumberFormatException | NotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}

	public void setSelling_price(String text2) {
		try {
			if (!book_to_be_modified.changePrice(Double.parseDouble(text2)))
				JOptionPane.showMessageDialog(null,
			"ERROR in price update!","Try again", JOptionPane.ERROR_MESSAGE);
		} catch (NumberFormatException | NotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}

	public void setThreshold(String text2) {
		try {
			if (!book_to_be_modified.setThreshold(Integer.parseInt(text2)))
				JOptionPane.showMessageDialog(null,
			"ERROR in Threshold update!","Try again", JOptionPane.ERROR_MESSAGE);
		} catch (NumberFormatException | NotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}

	public ArrayList<String> getPublisherNames() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<String> getPublishersPhones() {
		// TODO Auto-generated method stub
		return null;
	}

}

