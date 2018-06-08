package ModelsInterfaces;

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
     * Get all the Books similar to this title.
     * This will be ANDED by the rest of the conditions.
     * @param bookTitle
     * @return the same object to be added with more conditions or
     * finalized by get(), booksCount().
     */    
    IBooksGetter searchBooksByTitle(String bookTitle);
    
    
    /**
     * Get all the Books with this id.
     * This will be ANDED by the rest of the conditions.
     * @param bookId
     * @return the same object to be added with more conditions or
     * finalized by get(), booksCount().
     */
    IBooksGetter getBooksById(int bookId);

    /**
     * Get all the Books with this ISBN.
     * This will be ANDED by the rest of the conditions.
     * @param bookISBN
     * @return the same object to be added with more conditions or
     * finalized by get(), booksCount().
     */
    IBooksGetter getBooksByISBN(String bookISBN);

    /**
     * search all the Books with this ISBN.
     * This will be ANDED by the rest of the conditions.
     * @param bookISBN
     * @return the same object to be added with more conditions or
     * finalized by get(), booksCount().
     */
    IBooksGetter searchBooksByISBN(String bookISBN);
    
    /**
     * Get all the Books written by this author.
     * This will be ANDED by the rest of the conditions.
     * @param author
     * @return the same object to be added with more conditions or
     * finalized by get(), booksCount().
     */
    IBooksGetter getBooksByAuthor(IAuthor author);
    
    /**
     * search all the Books written by this author.
     * This will be ANDED by the rest of the conditions.
     * @param authorName
     * @return the same object to be added with more conditions or
     * finalized by get(), booksCount().
     */
    IBooksGetter searchBooksByAuthor(String authorName);
    
    /**
     * Get all the Books published by this publisher.
     * This will be ANDED by the rest of the conditions.
     * @param publisher
     * @return the same object to be added with more conditions or
     * finalized by get(), booksCount().
     */
    IBooksGetter getBooksByPublisher(IPublisher publisher);

    /**
     * search all the Books published by this publisher.
     * This will be ANDED by the rest of the conditions.
     * @param publisherName
     * @return the same object to be added with more conditions or
     * finalized by get(), booksCount().
     */
    IBooksGetter searchBooksByPublisher(String publisherName);
    
    /**
     * Get all the Books in this category.
     * This will be ANDED by the rest of the conditions.
     * @param category
     * @return the same object to be added with more conditions or
     * finalized by get(), booksCount().
     */
    IBooksGetter getBooksByCategory(ICategory category);

    /**
     * search all the Books in this category.
     * This will be ANDED by the rest of the conditions.
     * @param categoryName
     * @return the same object to be added with more conditions or
     * finalized by get(), booksCount().
     */
    IBooksGetter searchBooksByCategory(String categoryName);
    
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
     * Get all the Books with threshold in this range.
     * This will be ANDED by the rest of the conditions.
     * @param thresholdStart
     * @param thresholdEnd
     * @return the same object to be added with more conditions or
     * finalized by get(), booksCount().
     */
    IBooksGetter getBooksByThreshold(int thresholdStart,
            int thresholdEnd);
    
    /**
     * Get all the Books with available quantity in this range.
     * This will be ANDED by the rest of the conditions.
     * @param availableQuantityStart
     * @param availableQuantityEnd
     * @return the same object to be added with more conditions or
     * finalized by get(), booksCount().
     */
    IBooksGetter getBooksByAvailability(int availableQuantityStart,
            int availableQuantityEnd);
    
    /**
     * Get all the Books which prices in this price range.
     * This will be ANDED by the rest of the conditions.
     * @param startingPrice
     * @param endingPrice
     * @return the same object to be added with more conditions or
     * finalized by get(), booksCount().
     */
    IBooksGetter getBooksBySellingPrice(double startingPrice, double endingPrice);
    
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