package ModelsInterfaces;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author Fares
 */
public interface IOrdersGetter {
    
    /**
     * Get all the orders by this user.
     * This will be ANDED by the rest of the conditions.
     * @param user
     * @return the same object to be added with more conditions or
     * finalized by get(), salesSum(), salesCount();
     */
    IOrdersGetter getOrdersByUser(IUser user);

    /**
     * To get all the orders ordered by this credit card.
     * This will be ANDED by the rest of the conditions.
     * @param creditcardNo, the credit card number.
     * @return the same object to be added with more conditions or
     * finalized by get(), salesSum(), salesCount();
     */
    IOrdersGetter getOrdersByCreditcardNo(String creditcardNo);

    /**
     * To get all the orders in this time range.
     * This will be ANDED by the rest of the conditions.
     * @param startingDate
     * @param endingDate
     * @return the same object to be added with more conditions or
     * finalized by get(), salesSum(), salesCount();
     */
    IOrdersGetter getOrdersByTime(Timestamp startingDate, Timestamp endingDate);

    /**
     * To get all the orders in this price range.
     * This will be ANDED by the rest of the conditions.
     * @param startingPrice
     * @param endingPrice
     * @return the same object to be added with more conditions or
     * finalized by get(), salesSum(), salesCount();
     */
    IOrdersGetter getOrdersByTotalPrice(double startingPrice, double endingPrice);
    
    /**
     *
     * @return the sum of all the selected orders.
     */
    double salesSum();

    /**
     *
     * @return the count of all the selected orders.
     */
    int salesCount();

    /**
     *
     * @param limit max number of orders to be returned.
     * @param offset offset of orders.
     * @return ArrayList of all the selected orders.
     */
    ArrayList<IOrder> get(int limit, int offset);
    
    /**
     *
     * @return ArrayList of all the selected orders.
     */
    ArrayList<IOrder> get();
}
