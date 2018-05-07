
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
public interface IOrdersGetter {
    
    IOrdersGetter getBooksByUser(IUser user);
    IOrdersGetter getBooksByCreditcardNo(String creditcardNo);
    IOrdersGetter getBooksByTime(String startingDate, String endingDate);
    IOrdersGetter getBooksByTotalPrice(int startingPrice, int endingPrice);
    
    ArrayList<IOrder> get();
}
