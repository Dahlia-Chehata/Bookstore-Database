package controller;

import java.util.ArrayList;

import HelperClasses.NotFound;

public interface IResultView {

	boolean set_add_to_cart(String iSBN, int label) throws NotFound;

	void setTotal_size_of_data(int size);

	void setColumn_names(String[] column_names);

	boolean order_book(String iSBN, int label);

	ArrayList<String> getPublisherNames();

	ArrayList<String> getPublishersPhones();

	void updateTable(String[][] data);

}
