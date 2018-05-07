
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Fairous
 */
public interface IOrderManager {
    
    IOrdersGetter getOrders();
  
    IOrder getOrderById(int orderId);
    IOrder addOrder(IUser user, String creditcardNo,
                   String creditExpiryDate,
                   ArrayList<IOrderItem> items);
    
    boolean removeOrder(IOrder order);
}
