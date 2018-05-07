
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Fairous
 */
public interface IBooksGetter {
    
    IBooksGetter getBooksByTitle(String bookTitle);
    IBooksGetter getBooksByAuthor(IAuthor author);
    IBooksGetter getBooksByCategory(ICategory category);
    IBooksGetter getBooksByPublicationYear(int publicationYear);
    IBooksGetter getBooksBySellingPrice(int startingPrice, int endingPrice);
    
    ArrayList<IBook> get();
}
