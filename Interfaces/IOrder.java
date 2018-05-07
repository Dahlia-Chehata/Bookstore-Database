
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
public interface IOrder {
    
    int getId();
    IUser getUser();
    double getTotalPrice();
    String getTimeStamp();
    String getCreditCardNo();
    String getCreditExpiryDate();
    ArrayList<IOrderItem> getBooks();
    
}
