package controller;

import java.sql.Date;
import java.util.ArrayList;

import HelperClasses.NotFound;
import ModelsImplementation.CartManager;
import ModelsImplementation.OrderManager;
import ModelsInterfaces.IBook;
import ModelsInterfaces.ICartManager;
import ModelsInterfaces.IOrder;
import ModelsInterfaces.IOrderManager;
import ModelsInterfaces.IUser;
import ModelsInterfaces.IUserManager;
import gui.books_handling.ShoppingCart;
import javafx.util.Pair;

public class ViewCartController {

	private ResultViewController resultViewController;
	private ShoppingCart shoppingcart_page;
	private ICartManager cart_manager;
	private IOrderManager order_manager;
	private IUserManager user_manager;
	private IUser user;
	private ArrayList<Pair<IBook, Integer>> user_cart;
	private String[][] data;
	private ArrayList<Pair<IBook, Integer>> items; //from cart manager


	public ViewCartController(ResultViewController resultViewController) throws NotFound {
		this.resultViewController = resultViewController;
		this.user = resultViewController.getUser();
		cart_manager = new CartManager();
		user_cart = cart_manager.getUserCart(user);
		setAttributes();
		shoppingcart_page = new ShoppingCart(this);

	}

	private void setAttributes() throws NotFound {
		int size_of_data = user_cart.size();
		data = new String[size_of_data][4];
		int i = 0;
		for (Pair<IBook, Integer> p : user_cart) {
			data[i][0] = p.getKey().getISBN();
			data[i][1] = p.getKey().getTitle();
			data[i][2] = p.getValue().toString();
			data[i][3] = "Discard"; //button name
			i++;
		}

	}

	public String[][] getData() {
		return data;
	}

	public boolean buy(String creditcard_number, Date creditcard_expirydate) {
		getItemsInCart();
		order_manager = new OrderManager();
		IOrder order = order_manager.addOrder(user, creditcard_number, creditcard_expirydate, items);
		if (order != null) {
			return true;
		} else {
			return false;
		}
	}

	private void getItemsInCart() {
		items = cart_manager.getUserCart(user);
	}



}
