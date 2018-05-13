package ModelsInterfaces;

import HelperClasses.NotFound;

/**
 *
 * @author Fares
 */
public interface IUserStatus {
    
    /**
     *
     * @return the id of the userStatus
     */
    int getId() throws NotFound;

    /**
     *
     * @return the name of the userStatus
     */
    String getName() throws NotFound;
    
    /**
     *
     * @param newName for the User Status.
     * @return true if the update succeeded, false otherwise.
     */
    boolean changeName(String newName) throws NotFound;
}
