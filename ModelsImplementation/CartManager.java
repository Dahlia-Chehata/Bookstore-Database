package ModelsImplementation;

import HelperClasses.ErrorHandler;
import HelperClasses.NotFound;
import ModelsInterfaces.IBook;
import ModelsInterfaces.ICartManager;
import ModelsInterfaces.IUser;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.util.Pair;

/**
 *
 * @author Fares
 */
public class CartManager implements ICartManager{

    //Error handler
    private ErrorHandler errorHandler;
    
    ///Data storage
    HashMap<IUser,ArrayList<Pair<IBook, Integer>>> data;
    
    @Override
    public boolean addBook(IUser user, IBook book, int quantity) {
        
        //check for existing User and Book & get the quantity available.
        int quantityAvailable;
        try{
            user.getId();
            book.getID();
            quantityAvailable = book.getAvailableQuantity();
        } catch(NotFound ex){
            return false;
        }
        
        //check quantity
        if(quantityAvailable < quantity){
            return false;
        }
        
        //if user has no items in the cart
        if(data.get(user) == null){
            //then make a new cart and insert the items
            ArrayList<Pair<IBook, Integer>> userList = new ArrayList<>();
            userList.add(new Pair<>(book,quantity));
            data.put(user, userList);
            
        //if user has items in the cart
        }else{
            
            //get the items
            ArrayList<Pair<IBook, Integer>> userList = data.get(user);
            boolean succ = false;
            
            //if the book already in the list then add the quantity to the quantity in the cart
            for(int i=0;i<userList.size();i++){
                if(userList.get(i).getKey().equals(book)){
                    
                    if(userList.get(i).getValue()+quantity > quantityAvailable){
                        return false;
                    }else{
                        userList.set(i, new Pair<>(book,userList.get(i).getValue()+quantity));
                        succ = true;
                        break;
                    }
                    
                }
            }
            
            //if the the book is not in the cart then add it to the cart
            if(!succ){
                userList.add(new Pair<>(book,quantity));
            }
                        
        }
        
        return true;
    }

    @Override
    public void flushCart(IUser user) {
        data.remove(user);
    }

    @Override
    public ArrayList<Pair<IBook, Integer>> getUserCart(IUser user) {
        
        ArrayList<Pair<IBook, Integer>> toReturn = data.get(user);
        
        if(toReturn == null){
            return new ArrayList<>();
        }
        
        return toReturn;
    }
    
}