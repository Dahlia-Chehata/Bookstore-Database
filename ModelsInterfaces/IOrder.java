import java.util.ArrayList;

/**
 *
 * @author Fares
 */
public interface IOrder {
    
    /**
     *
     * @return the id of the order.
     */
    int getId();

    /**
     *
     * @return object of the user ordered this order.
     */
    IUser getUser();

    /**
     *
     * @return the total price paid for this order.
     */
    double getTotalPrice();

    /**
     *
     * @return the time when this order was ordered.
     */
    String getTimeStamp();

    /**
     *
     * @return the credit card number used for this order.
     */
    String getCreditCardNo();

    /**
     *
     * @return the expiry date of this credit card.
     */
    String getCreditExpiryDate();

    /**
     *
     * @return ArrayList of the all the books ordered in the order.
     */
    ArrayList<IOrderItem> getBooks();
    
}
