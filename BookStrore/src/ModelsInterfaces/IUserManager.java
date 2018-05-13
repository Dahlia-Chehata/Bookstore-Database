package ModelsInterfaces;

import java.util.ArrayList;

/**
 *
 * @author Fares
 */
public interface IUserManager {
    
    /**
     *
     * @param id the id of the wanted user.
     * @return if found, the User object. if not, will return null.
     * @see IUser
     */
    IUser getById(int id);

    /**
     *
     * @param username the username of the wanted user.
     * @return ArrayList of all users that have same username, this could be
     * empty.
     * @see IUser
     */
    ArrayList<IUser> getByUsername(String username);

    /**
     *
     * @param email the email of the wanted user.
     * @return if found, the User object. if not, will return null.
     * @see IUser
     */
    IUser getByEmail(String email);

    /**
     *
     * @param email the email of the wanted user.
     * @param password the password of the user with this email.
     * @return if found, the User object. if not, will return null.
     * @see IUser
     */
    IUser getByEmailAndPassword(String email, String password);
    
    /**
     *
     * @return a ArrayList of all the registered users.
     * @see IUser
     */
    ArrayList<IUser> getAllUsers();

    /**
     *
     * @param username can't be empty.
     * @param email can't be empty.
     * @param password can't be empty.
     * @param fname first name - can't be empty.
     * @param lname last name -  can't be empty.
     * @param sefaultShippingAddress can't be empty.
     * @param phoneNumber can't be empty.
     * @return if the user registered successfully, User object will be returned
     * else, null will be returned.
     * @see IUser
     */
    IUser addUser(String username, String email, String password,
                  String fname, String lname, String sefaultShippingAddress,
                  String phoneNumber);
}
