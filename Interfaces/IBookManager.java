
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
public interface IBookManager {
    
    IBooksGetter getBooks();
    
    IBook getBookById(int bookId);
    IBook getBookByISBN(int bookISBN);
    IBook addBook(String title, ICategory category,
            String ISBN, IPublisher publisher, int publicationYear,
            double price, int availableQuantity, int threshold,
            int quantityToBeOrderd, ArrayList<IAuthor> authors);
}
