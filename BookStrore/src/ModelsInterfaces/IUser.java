package ModelsInterfaces;

import HelperClasses.NotFound;
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
    int getId() throws NotFound;

    /**
     *
     * @return the first name of this user.
     */
    String getFName() throws NotFound;

    /**
     *
     * @return the last name of this user.
     */
    String getLName() throws NotFound;

    /**
     *
     * @return the email of this user.
     */
    String getEmail() throws NotFound;

    /**
     *
     * @return the phone number of this user.
     */
    String getPhone() throws NotFound;

    /**
     *
     * @return the status of this user to know if the user
     * an Admin/User/....
     */
    IUserStatus getStatus() throws NotFound;

    /**
     *
     * @return the default shipping address of this user.
     */
    String getDefaultShippingAddress() throws NotFound;

    /**
     *
     * @param password the plain text of the user's password.
     * @return true if the password equal the password of the user,
     * false otherwise.
     */
    boolean comparePassword(String password) throws NotFound;

    /**
     *
     * @return all the orders that have been ordered by this user.
     */
    ArrayList<IOrder> getOrders() throws NotFound;

    /**
     *
     * @param newFName new first name for the user.
     * @return true if the update done successfully, false otherwise.
     */
    boolean changeFName(String newFName) throws NotFound;

    /**
     *
     * @param newLName new last name for the user.
     * @return true if the update done successfully, false otherwise.
     */
    boolean changeLName(String newLName) throws NotFound;

    /**
     *
     * @param newEmail new email for the user.
     * @return true if the update done successfully, false otherwise.
     */
    boolean changeEmail(String newEmail) throws NotFound;

    /**
     *
     * @param newPhone new phone number for the user.
     * @return true if the update done successfully, false otherwise.
     */
    boolean changePhone(String newPhone) throws NotFound;

    /**
     *
     * @param newStatus new status for the user.
     * @return true if the update done successfully, false otherwise.
     */
    boolean changeStatus(IUserStatus newStatus) throws NotFound;

    /**
     *
     * @param newDefaultShippingAddress  new default shipping address for the user.
     * @return true if the update done successfully, false otherwise.
     */
    boolean changeDefaultShippingAddress(String newDefaultShippingAddress) throws NotFound;

    /**
     *
     * @param newPassword  new password for the user.
     * @return true if the update done successfully, false otherwise.
     */
    boolean changePassword(String newPassword) throws NotFound;

    /**
     *
     * @param orderToBeDeleted  the order to be deleted.
     * @return true if the removal done successfully, false otherwise.
     * also it will return false if the order isn't owned by this user.
     */
    boolean removeOrder(IOrder orderToBeDeleted) throws NotFound;
    
}