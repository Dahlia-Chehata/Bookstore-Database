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
    int getID();

    /**
     *
     * @return the ISBN of the book.
     */
    String getISBN();

    /**
     *
     * @return the title of the book.
     */
    String getTitle();

    /**
     *
     * @return the publication year of the book.
     */
    int getPublicationYear();

    /**
     *
     * @return the price of the book.
     */
    double getPrice();
    
    /**
     *
     * @return the current available quantity for this book.
     */
    int getAvailableQuantity();

    /**
     *
     * @return the minimum number of book that should always exist.  
     */
    int getThreshold();

    /**
     *
     * @return the quantity to be ordered automatically above the threshold
     * incase the availability become lower than the threshold.
     */
    int getQuantityToBeOrdered();
    
    /**
     *
     * @return the category this book in.
     */
    ICategory getCategory();

    /**
     *
     * @return all the authors of the book.
     */
    ArrayList<IAuthor> getAuthors();

    /**
     *
     * @return the publisher of the book.
     */
    IPublisher getPublisher();
    

    /**
     *
     * @param newISBN
     * @return true if update succeeded, false otherwise.
     */
    boolean changeISBN(String newISBN);

    /**
     *
     * @param newTitle
     * @return true if update succeeded, false otherwise.
     */
    boolean changeTitle(String newTitle);

    /**
     *
     * @param newPublicationYear
     * @return true if update succeeded, false otherwise.
     */
    boolean changePublicationYear(int newPublicationYear);

    /**
     *
     * @param newPrice
     * @return true if update succeeded, false otherwise.
     */
    boolean changePrice(double newPrice);
    
    /**
     *
     * @param newThreshold
     * @return true if update succeeded, false otherwise.
     */
    boolean setThreshold(int newThreshold);

    /**
     *
     * @param newQuantityToBeOrdered
     * @return true if update succeeded, false otherwise.
     */
    boolean setQuantityToBeOrdered(int newQuantityToBeOrdered);
    
    /**
     *
     * @param newCategory
     * @return true if update succeeded, false otherwise.
     */
    boolean changeCategory(ICategory newCategory);

    /**
     *
     * @param newPublisher
     * @return true if update succeeded, false otherwise.
     */
    boolean changePublisher(IPublisher newPublisher);
    
    /**
     *
     * @param newAuthour
     * @return true if addition succeeded, false otherwise.
     */
    boolean addAuthor(IAuthor newAuthour);

    /**
     *
     * @param currAuthour
     * @return true if the author no longer related to this book,
     * false otherwise.
     */
    boolean removeAuthour(IAuthor currAuthour);
}
