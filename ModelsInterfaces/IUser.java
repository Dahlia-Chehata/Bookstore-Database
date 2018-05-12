package ModelsInterfaces;

import java.util.ArrayList;

/**
 *
 * @author Fares
 */
public interface IUser {
    
    /**
     *
     * @return the id of this user.
     */
    int getId();

    /**
     *
     * @return the first name of this user.
     */
    String getFName();

    /**
     *
     * @return the last name of this user.
     */
    String getLName();

    /**
     *
     * @return the email of this user.
     */
    String getEmail();

    /**
     *
     * @return the phone number of this user.
     */
    String getPhone();

    /**
     *
     * @return the status of this user to know if the user
     * an Admin/User/....
     */
    IUserStatus getStatus();

    /**
     *
     * @return the default shipping address of this user.
     */
    String getDefaultShippingAddress();

    /**
     *
     * @param password the plain text of the user's password.
     * @return true if the password equal the password of the user,
     * false otherwise.
     */
    boolean comparePassword(String password);

    /**
     *
     * @return all the orders that have been ordered by this user.
     */
    ArrayList<IOrder> getOrders();

    /**
     *
     * @param newFName new first name for the user.
     * @return true if the update done successfully, false otherwise.
     */
    boolean changeFName(String newFName);

    /**
     *
     * @param newLName new last name for the user.
     * @return true if the update done successfully, false otherwise.
     */
    boolean changeLName(String newLName);

    /**
     *
     * @param newEmail new email for the user.
     * @return true if the update done successfully, false otherwise.
     */
    boolean changeEmail(String newEmail);

    /**
     *
     * @param newPhone new phone number for the user.
     * @return true if the update done successfully, false otherwise.
     */
    boolean changePhone(String newPhone);

    /**
     *
     * @param newStatus new status for the user.
     * @return true if the update done successfully, false otherwise.
     */
    boolean changeStatus(IUserStatus newStatus);

    /**
     *
     * @param newDefaultShippingAddress  new default shipping address for the user.
     * @return true if the update done successfully, false otherwise.
     */
    boolean changeDefaultShippingAddress(String newDefaultShippingAddress);

    /**
     *
     * @param newPassword  new password for the user.
     * @return true if the update done successfully, false otherwise.
     */
    boolean changePassword(String newPassword);

    /**
     *
     * @param orderToBeDeleted  the order to be deleted.
     * @return true if the removal done successfully, false otherwise.
     * also it will return false if the order isn't owned by this user.
     */
    boolean removeOrder(IOrder orderToBeDeleted);
    
}