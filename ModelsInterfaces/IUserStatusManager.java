import java.util.ArrayList;

/**
 *
 * @author Fares
 */
public interface IUserStatusManager {

    /**
     *
     * @return all the user statuses supported by the application.
     */
    ArrayList<IUserStatus> getAllUserStatuses();

    /**
     *
     * @param id the id of the wanted user status.
     * @return the user status if found, null otherwise.
     */
    IUserStatus getById(int id);

    /**
     *
     * @param newUserStatus the name of the new user's status.
     * @return object of user status if addition done successfully,
     * null otherwise.
     */
    IUserStatus addStatus(String newUserStatus);
}
