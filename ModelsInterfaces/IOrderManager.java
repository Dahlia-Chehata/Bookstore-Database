package ModelsInterfaces;

import java.sql.Date;
import java.util.ArrayList;
import javafx.util.Pair;

/**
 *
 * @author Fares
 */
public interface IOrderManager {
    
    /**
     *
     * @return Object implements IOrdersGetter to
     * get orders with given conditions.
     */
    IOrdersGetter getOrders();
  
    /**
     *
     * @param orderId, the wanted order id.
     * @return Object implements IOrder if found, null otherwise.
     */
    IOrder getOrderById(int orderId);

    /**
     *
     * @param user, the user ordered this order.
     * @param creditcardNo, the credit card used in this order.
     * @param creditExpiryDate, the expiry date of the credit card used.
     * @param items, list of all the items in the order.
     * @return Object implements IOrder if addition succeeded, null otherwise.
     */
    IOrder addOrder(IUser user, String creditcardNo,
                   Date creditExpiryDate,
                   ArrayList<Pair<IBook, Integer>> items);

}
