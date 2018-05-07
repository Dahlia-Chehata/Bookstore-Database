/**
 *
 * @author Fares
 */
public interface IOrderItem {

    /**
     *
     * @return the quantity of the ordered books.
     */
    int getQuantity();

    /**
     *
     * @return the total price of there ordered books when bought.
     */
    double getTotalPrice();

    /**
     *
     * @return the book in this order item.
     */
    IBook getBook();
}
