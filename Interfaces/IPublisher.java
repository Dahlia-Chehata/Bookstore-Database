
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
public interface IPublisher {
    
    int getId();
    String getName();
    String getAddress();
    String getTelephone();
    
    boolean changeName(String newName);
    boolean changeAddress(String newAddress);
    boolean changeTelephone(String newTelephone);
    
    boolean publishBook(IBook bookToBePublished);
    ArrayList<IBook> getBooks();
}
