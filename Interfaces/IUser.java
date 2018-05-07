
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
public interface IUser {
    
    int getId();
    String getFName();
    String getLName();
    String getEmail();
    String getPhone();
    IStatus getStatus();
    String getDefaultShippingAddress();
    boolean comparePassword(String password);
    ArrayList<IOrder> getOrders();

    
    boolean changeFName(String newFName);
    boolean changeLName(String newLName);
    boolean changeEmail(String newEmail);
    boolean changePhone(String newPhone);
    boolean changeStatus(IStatus newStatus);
    boolean changeDefaultShippingAddress(String newDefaultShippingAddress);
    boolean changePassword(String newPassword);
    boolean removeOrder(IOrder orderToBeDeleted);
    
}