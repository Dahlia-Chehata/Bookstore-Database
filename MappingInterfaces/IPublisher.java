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
    int getId();

    /**
     *
     * @return the name of this publisher.
     */
    String getName();

    /**
     *
     * @return the address of this publisher.
     */
    String getAddress();

    /**
     *
     * @return the telephone of this publisher.
     */
    String getTelephone();
    
    /**
     *
     * @param newName for this publisher.
     * @return true if the update succeeded, false otherwise.
     */
    boolean changeName(String newName);

    /**
     *
     * @param newAddress for this publisher.
     * @return true if the update succeeded, false otherwise.
     */
    boolean changeAddress(String newAddress);

    /**
     *
     * @param newTelephone for this publisher.
     * @return true if the update succeeded, false otherwise.
     */
    boolean changeTelephone(String newTelephone);
    
    /**
     *
     * @param bookToBePublished, the book to be published by this publisher.
     * @return true if the addition succeeded, false otherwise.
     */
    boolean publishBook(IBook bookToBePublished);

    /**
     *
     * @return All the books published by this publisher.
     */
    ArrayList<IBook> getBooks();
}
