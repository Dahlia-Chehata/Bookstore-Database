package ModelsInterfaces;

import java.util.ArrayList;

/**
 *
 * @author Fares
 */
public interface IBookManager {
    
    /**
     *
     * @return Object implements IBooksGetter to get wanted books
     * with given conditions.
     */
    IBooksGetter getBooks();

    /**
     * This will add book to application.
     * @param title of the book.
     * @param category of the book.
     * @param ISBN of the book.
     * @param publisher of the book.
     * @param publicationYear of the book.
     * @param price of the book.
     * @param availableQuantity of the book.
     * @param threshold that must be saved all the time, otherwise order
     * will be automatically placed.
     * @param quantityToBeOrderd the quantity to be ordered automatically above
     * the threshold when the available quantity go below certain threshold.
     * @param authors ArrayList of the authors.
     * @return Book object in success or null in failure.
     */
    IBook addBook(String title, ICategory category,
            String ISBN, IPublisher publisher, int publicationYear,
            double price, int availableQuantity, int threshold,
            int quantityToBeOrderd, ArrayList<IAuthor> authors);

    /**
     * Get top X sold book in the time range.
     * @param numOfBooks - X
     * @param startingDate
     * @param endingDate
     * @return ArrayList of top books sorted starting by the best seller.
     */
    ArrayList<IBook> getTop(int numOfBooks, String startingDate,
                            String endingDate);
}
