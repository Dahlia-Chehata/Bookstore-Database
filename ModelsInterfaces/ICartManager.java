package ModelsInterfaces;

import HelperClasses.NotFound;
import java.util.ArrayList;
import javafx.util.Pair;

/**
 *
 * @author Fares
 */
public interface ICartManager {
    
    /**
     *
     * @param user, the user who order the book.
     * @param book, the book to be ordered.
     * @param quantity, quantity of the ordered book.
     * @return true if the addition succeeded, false otherwise.
     */
    public boolean addBook(IUser user, IBook book, int quantity);
    
    /**
     *
     * @param user, the user to empty the cart.
     */
    public void flushCart(IUser user);
    
    /**
     *
     * @param user, the user to get the list of items in the cart.
     * @return ArrayList of all the books in the cart with it's quantity.
     */
    public ArrayList<Pair<IBook,Integer>> getUserCart(IUser user);
    
}
