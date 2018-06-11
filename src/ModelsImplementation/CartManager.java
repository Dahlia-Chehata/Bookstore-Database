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
    private ErrorHandler errorHandler = new ErrorHandler();
    
    ///Data storage
    HashMap<Integer,ArrayList<Pair<IBook, Integer>>> data = new HashMap<>();
    
    @Override
    public boolean addBook(IUser user, IBook book, int quantity) throws NotFound{
        
        if(quantity < 0){
            return false;
        }
        
        //check for existing User and Book & get the quantity available.
        int quantityAvailable;
        int userId;
        int bookId;
        try{
            userId = user.getId();
            bookId = book.getID();
            quantityAvailable = book.getAvailableQuantity();
        } catch(NotFound ex){
            return false;
        }
        
        //check quantity
        if(quantityAvailable < quantity){
            return false;
        }
        
        //if user has no items in the cart
        if(data.get(userId) == null){
            
            if(quantity == 0){
                return true;
            }
            
            //then make a new cart and insert the items
            ArrayList<Pair<IBook, Integer>> userList = new ArrayList<>();
            userList.add(new Pair<>(book,quantity));
            data.put(userId, userList);
            
        //if user has items in the cart
        }else{
            
            //get the items
            ArrayList<Pair<IBook, Integer>> userList = data.get(userId);
            boolean succ = false;
            
            //if the book already in the list then add the quantity to the quantity in the cart
            for(int i=0;i<userList.size();i++){
                if(userList.get(i).getKey().getISBN().equals(book.getISBN())){
                        
                    if(quantity == 0){
                        userList.remove(i);
                    }else{
                        userList.set(i, new Pair<>(book,quantity));
                    }

                    succ = true;
                    break;
                }
            }
            
            
            if(quantity == 0){
                return true;
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
        try {
            data.remove(user.getId());
        } catch (NotFound ex) {

        }
    }

    @Override
    public ArrayList<Pair<IBook, Integer>> getUserCart(IUser user) {
        
        ArrayList<Pair<IBook, Integer>> toReturn;
        
        try {
            toReturn = data.get(user.getId());
        } catch (NotFound ex) {
            return new ArrayList<>();
        }
        
        if(toReturn == null){
            return new ArrayList<>();
        }
        
        return toReturn;
    }
    
    public static void main(String args[]) throws NotFound{
        CartManager mng = new CartManager();
        System.out.println(mng.addBook(new User(1), new Book("1"), 10));
      //  System.out.println(mng.addBook(new User(1), new Book("LLI"), 5));
        //mng.flushCart(new User(1));
        System.out.println(mng.getUserCart(new User(1)).get(0).getValue());
                Mysql.MysqlHandler.getInstance().state();

    }
    
}