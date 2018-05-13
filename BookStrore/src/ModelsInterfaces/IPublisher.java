package ModelsInterfaces;

import HelperClasses.NotFound;
import java.util.ArrayList;

/**
 *
 * @author Fares
 */
public interface IPublisher {
    
    /**
     *
     * @return the id of this publisher.
     */
    int getId() throws NotFound;

    /**
     *
     * @return the name of this publisher.
     */
    String getName() throws NotFound;

    /**
     *
     * @return the address of this publisher.
     */
    String getAddress() throws NotFound;

    /**
     *
     * @return the telephone of this publisher.
     */
    String getTelephone() throws NotFound;
    
    /**
     *
     * @param newName for this publisher.
     * @return true if the update succeeded, false otherwise.
     */
    boolean changeName(String newName) throws NotFound;

    /**
     *
     * @param newAddress for this publisher.
     * @return true if the update succeeded, false otherwise.
     */
    boolean changeAddress(String newAddress) throws NotFound;

    /**
     *
     * @param newTelephone for this publisher.
     * @return true if the update succeeded, false otherwise.
     */
    boolean changeTelephone(String newTelephone) throws NotFound;
    
    /**
     *
     * @param bookToBePublished, the book to be published by this publisher.
     * @return true if the addition succeeded, false otherwise.
     */
    boolean publishBook(IBook bookToBePublished) throws NotFound;

    /**
     *
     * @return All the books published by this publisher.
     */
    ArrayList<IBook> getBooks() throws NotFound;
}
