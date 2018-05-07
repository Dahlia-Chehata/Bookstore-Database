import java.util.ArrayList;

/**
 *
 * @author Fares
 */
public interface IAuthorManager {
    
    /**
     * Get object of Author if found in the application, if not
     * new Author object will be returned.
     * @param authorName
     * @return always IAuthor object.
     */
    IAuthor getOrAddAuthor(String authorName);

    /**
     *
     * @return ArrayList of all the authors in the application.
     */
    ArrayList<IAuthor> getAllAuthors();
}