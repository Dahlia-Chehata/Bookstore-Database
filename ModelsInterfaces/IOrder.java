package ModelsInterfaces;

import HelperClasses.NotFound;
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
    int getId() throws NotFound;

    /**
     *
     * @return object of the user ordered this order.
     */
    IUser getUser() throws NotFound;

    /**
     *
     * @return the total price paid for this order.
     */
    double getTotalPrice() throws NotFound;

    /**
     *
     * @return the time when this order was ordered.
     */
    String getTimeStamp() throws NotFound;

    /**
     *
     * @return the credit card number used for this order.
     */
    String getCreditCardNo() throws NotFound;

    /**
     *
     * @return the expiry date of this credit card.
     */
    String getCreditExpiryDate() throws NotFound;

    /**
     *
     * @return ArrayList of the all the books ordered in the order.
     */
    ArrayList<IOrderItem> getBooks() throws NotFound;
    
}
