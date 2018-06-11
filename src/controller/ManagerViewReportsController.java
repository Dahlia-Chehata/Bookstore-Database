package controller;

import java.util.ArrayList;

import HelperClasses.NotFound;
import ModelsImplementation.Reports;
import ModelsInterfaces.IBook;
import ModelsInterfaces.IUser;
import gui.ReportsView;

public class ManagerViewReportsController {

	private Manager_controller manager_controller;
	private ReportsView reportsview_page;
	private String[][] data;
	private String[] column_names;
	private Reports report;
	private int totalsales;


	public ManagerViewReportsController(Manager_controller manager_controller, String button_selected) {
		report = new Reports();
		this.totalsales = -1;
		this.manager_controller = manager_controller;
		this.data = setReport(button_selected);
		
		reportsview_page = new ReportsView(this);
	}

	private String[][] setReport(String button_selected) {
		String[][] data_to_be_returned = null;
		switch (button_selected) {
		case "1": //Total sales
			column_names = initialize_columnnames_total_sales();
			data_to_be_returned = null;
			totalsales = generate_totalsales_report();
			break;

		case "2": //Top 5 customers
			column_names = initialize_columnnames_top_customers();
			data_to_be_returned = generate_top5_cusotmers();
			break;

		case "3": //Top 10 selling books
			column_names = initialize_columnnames_top_books();
			data_to_be_returned = generate_top10_selling_books();
			break;

		default:
			break;
		}
		return data_to_be_returned;
	}

	private String[] initialize_columnnames_top_books() {
		String[] c = {"ISBN", "Book Title"};

		return c;
	}

	private String[] initialize_columnnames_top_customers() {
		String[] c = {"Username", "Email"};

		return c;
	}

	private String[] initialize_columnnames_total_sales() {
		String[] c = {"ISBN", "Book Title", "Books sold"};

		return c;
	}

	public String[][] getData() {
		return data;
	}

	public String[] getColumn_names() {
		return column_names;
	}

	private String[][] generate_top10_selling_books() {
		ArrayList<IBook> top_books = report.top10BooksLast3Months();
		String[][] books = new String[top_books.size()][2];
		int i = 0;
		for (IBook book : top_books) {
			try {
				books[i][0] = book.getISBN();
				books[i][1] = book.getTitle();
				i++;
			} catch (NotFound e) {
				e.printStackTrace();
			}
		}

		return books;
	}

	private String[][] generate_top5_cusotmers() {
		ArrayList<IUser> top_customers = report.top5CustomersLast3Months();
		String[][] customers = new String[top_customers.size()][2];

		int i = 0;
		for (IUser user : top_customers) {
			try {
				customers[i][0] = user.getUsername();
				customers[i][1] = user.getEmail();
				i++;
			} catch (NotFound e) {
				e.printStackTrace();
			}
		}


		return customers;
	}

	private int generate_totalsales_report() {
		return (report.TotalSalesLastMonth());

	}

	public void gotoHomePage() {
		manager_controller.gotoHomePage();

	}

	public void back() {
//		manager_controller.back_to_
	}


	public int getTotalsales() {
		return totalsales;
	}
}
