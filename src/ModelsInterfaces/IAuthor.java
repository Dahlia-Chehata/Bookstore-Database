package ModelsInterfaces;

import java.util.ArrayList;

/**
 *
 * @author Fares
 */
public interface IAuthor {
    
    /**
     *
     * @return the name of the author.
     */
    String getName();

    /**
     *
     * @return ArrayList of all the books written by this author.
     */
    ArrayList<IBook> getWrittenBooks();
    
    /**
     *
     * @param bookToBeRemoved
     * @return true if there is no relation between the author and this book,
     * and false if deletion fails and the author still author to this book.
     */
    boolean removeBook(IBook bookToBeRemoved);
    
    /**
     *
     * @param bookToBeAdded
     * @return true if the book existed and added successfully to the author,
     * and false if fails.
     */
    boolean addBook(IBook bookToBeAdded);

}
