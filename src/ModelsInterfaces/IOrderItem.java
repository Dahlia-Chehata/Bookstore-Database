package ModelsInterfaces;

import HelperClasses.NotFound;

/**
 *
 * @author Fares
 */
public interface IOrderItem {

    /**
     *
     * @return the quantity of the ordered books.
     */
    int getQuantity() throws NotFound;

    /**
     *
     * @return the total price of there ordered books when bought.
     */
    double getTotalPrice() throws NotFound;

    /**
     *
     * @return the book in this order item.
     */
    IBook getBook() throws NotFound;
}
