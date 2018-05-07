
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
public interface IBook {
    
    //BookGetterInf
    int getID();
    String getISBN();
    String getTitle();
    int getPublicationYear();
    double getPrice();
    
    int getAvailableQuantity();
    int getThreshold();
    int getQuantityToBeOrdered();
    
    ICategory getCategory();
    ArrayList<IAuthor> getAuthors();
    IPublisher getPublisher();
    
    //BookSetters
    boolean changeISBN(String newISBN);
    boolean changeTitle(String newTitle);
    boolean changePublicationYear(int newPublicationYear);
    boolean changePrice(double newPrice);
    
    boolean setThreshold(int newThreshold);
    boolean setQuantityToBeOrdered(int newQuantityToBeOrdered);
    
    boolean changeCategory(ICategory newCategory);
    boolean changePublisher(IPublisher newPublisher);
    
    boolean addAuthor(IAuthor newAuthour);
    boolean removeAuthour(IAuthor currAuthour);
}
