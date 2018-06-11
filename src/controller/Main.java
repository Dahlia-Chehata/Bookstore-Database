package controller;

import HelperClasses.NotFound;

public class Main {
	public static void main (String [] args) {
		ManagerAddBookController x;
		try {
			x = new ManagerAddBookController();
			System.out.println(x.publishers.size());
            x.setPub_information("newName - newTelephone");
		} catch (NotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
