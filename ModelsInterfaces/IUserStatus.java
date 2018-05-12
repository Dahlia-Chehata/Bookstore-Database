/**
 *
 * @author Fares
 */
public interface IUserStatus {
    
    /**
     *
     * @return the id of the userStatus
     */
    int getId();

    /**
     *
     * @return the name of the userStatus
     */
    String getName();
    
    /**
     *
     * @param newName for the User Status.
     * @return true if the update succeeded, false otherwise.
     */
    boolean changeName(String newName);
}
