package ModelsInterfaces;

import HelperClasses.NotFound;
import java.util.ArrayList;

/**
 *
 * @author Fares
 */
public interface IBook {
    

    /**
     *
     * @return the id of the book.
     */
    int getID() throws NotFound;

    /**
     *
     * @return the ISBN of the book.
     */
    String getISBN() throws NotFound;

    /**
     *
     * @return the title of the book.
     */
    String getTitle() throws NotFound;

    /**
     *
     * @return the publication year of the book.
     */
    int getPublicationYear() throws NotFound;

    /**
     *
     * @return the price of the book.
     */
    double getPrice() throws NotFound;
    
    /**
     *
     * @return the current available quantity for this book.
     */
    int getAvailableQuantity() throws NotFound;

    /**
     *
     * @return the minimum number of book that should always exist.  
     */
    int getThreshold() throws NotFound;
    
    /**
     *
     * @return the category this book in.
     */
    ICategory getCategory() throws NotFound;

    /**
     *
     * @return all the authors of the book.
     */
    ArrayList<IAuthor> getAuthors() throws NotFound;

    /**
     *
     * @return the publisher of the book.
     */
    IPublisher getPublisher() throws NotFound;
    

    /**
     *
     * @param newISBN
     * @return true if update succeeded, false otherwise.
     */
    boolean changeISBN(String newISBN) throws NotFound;

    /**
     *
     * @param newTitle
     * @return true if update succeeded, false otherwise.
     */
    boolean changeTitle(String newTitle) throws NotFound;

    /**
     *
     * @param newPublicationYear
     * @return true if update succeeded, false otherwise.
     */
    boolean changePublicationYear(int newPublicationYear) throws NotFound;

    /**
     *
     * @param newPrice
     * @return true if update succeeded, false otherwise.
     */
    boolean changePrice(double newPrice) throws NotFound;
    
    /**
     *
     * @param newThreshold
     * @return true if update succeeded, false otherwise.
     */
    boolean setThreshold(int newThreshold) throws NotFound;

    /**
     *
     * @param newCategory
     * @return true if update succeeded, false otherwise.
     */
    boolean changeCategory(ICategory newCategory) throws NotFound;

    /**
     *
     * @param newPublisher
     * @return true if update succeeded, false otherwise.
     */
    boolean changePublisher(IPublisher newPublisher) throws NotFound;
    
    /**
     *
     * @param newAuthour
     * @return true if addition succeeded, false otherwise.
     */
    boolean addAuthor(IAuthor newAuthour) throws NotFound;

    /**
     *
     * @param currAuthour
     * @return true if the author no longer related to this book,
     * false otherwise.
     */
    boolean removeAuthour(IAuthor currAuthour) throws NotFound;
}
