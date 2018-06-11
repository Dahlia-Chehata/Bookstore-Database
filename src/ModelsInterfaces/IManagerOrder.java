package ModelsInterfaces;

import java.util.ArrayList;

import HelperClasses.NotFound;
import ModelsImplementation.singleOrder;
import javafx.util.Pair;
/**
 * 
 * @author Dahlia
 *
 */

public interface IManagerOrder {	    
	    /**
	     *
	     * @param user, the user who order the book.
	     * @param book, the book to be ordered.
	     * @param quantity, quantity of the ordered book.
	     * @return true if the addition succeeded, false otherwise.
	     */
	    public boolean addOrder(IBook book, int quantity)  throws NotFound;
	    
	    /**
	     *
	     * @param manager order id , the manager removes the order.
	     * @throws NotFound 
	     */
	    public void confirmOrder(int managerOrderId) throws NotFound ;
	    
	    /**
	     * @param manager order id, the manager to get the list of items in the order.
	     * @return ArrayList of all the books in the order with its quantity.
	     */
	    public ArrayList <singleOrder> getManagerOrder();
	    
	}