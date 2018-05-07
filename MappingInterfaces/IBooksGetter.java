import java.util.ArrayList;

/**
 *
 * @author Fares
 */
public interface IBooksGetter {
    
    /**
     * Get all the Books with this title.
     * This will be ANDED by the rest of the conditions.
     * @param bookTitle
     * @return the same object to be added with more conditions or
     * finalized by get(), booksCount().
     */
    IBooksGetter getBooksByTitle(String bookTitle);

    /**
     * Get all the Books written by this author.
     * This will be ANDED by the rest of the conditions.
     * @param author
     * @return the same object to be added with more conditions or
     * finalized by get(), booksCount().
     */
    IBooksGetter getBooksByAuthor(IAuthor author);

    /**
     * Get all the Books in this category.
     * This will be ANDED by the rest of the conditions.
     * @param category
     * @return the same object to be added with more conditions or
     * finalized by get(), booksCount().
     */
    IBooksGetter getBooksByCategory(ICategory category);

    /**
     * Get all the Books published in this time range.
     * This will be ANDED by the rest of the conditions.
     * @param publicationYearStart
     * @param publicationYearEnd
     * @return the same object to be added with more conditions or
     * finalized by get(), booksCount().
     */
    IBooksGetter getBooksByPublicationYear(int publicationYearStart,
            int publicationYearEnd);

    /**
     * Get all the Books which prices in this price range.
     * This will be ANDED by the rest of the conditions.
     * @param startingPrice
     * @param endingPrice
     * @return the same object to be added with more conditions or
     * finalized by get(), booksCount().
     */
    IBooksGetter getBooksBySellingPrice(int startingPrice, int endingPrice);
    
    /**
     *
     * @return count of the matched books.
     */
    int booksCount();

    /**
     *
     * @return ArrayList of all the matched books.
     */
    ArrayList<IBook> get();
}
