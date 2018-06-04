package ModelsImplementation;

import HelperClasses.ErrorHandler;
import HelperClasses.NotFound;
import ModelsInterfaces.IBook;
import ModelsInterfaces.IOrder;
import ModelsInterfaces.IOrderManager;
import ModelsInterfaces.IOrdersGetter;
import ModelsInterfaces.IUser;
import Mysql.MysqlHandler;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import javafx.util.Pair;

/**
 *
 * @author Fares
 */
public class OrderManager implements IOrderManager{

    //Error handler
    private ErrorHandler errorHandler = new ErrorHandler();
   
    OrderManager(){
        errorHandler = new ErrorHandler();
    }
    
    @Override
    public IOrdersGetter getOrders() {
        return new OrdersGetter();
    }

    @Override
    public IOrder getOrderById(int orderId) {
        try{
            return new Order(orderId);
        } catch (NotFound ex){
            return null;
        }
    }

    @Override
    public IOrder addOrder(IUser user, String creditcardNo, Date creditExpiryDate, ArrayList<Pair<IBook, Integer>> items) {
        
        int userId;
        PreparedStatement statement;
        boolean succ = true;
        
        //try to get user ID.
        try{
            userId = user.getId();
        }catch (NotFound ex){
            //if not existing, then return null.
            return null;
        }
        
        //Adding the order require transaction to make sure everything working correctly.
        Connection conn = null;
        try {
            conn = MysqlHandler.getInstance().getConnection();
            conn.setAutoCommit(false);
        } catch (SQLException ex) {
            errorHandler.report("Order Manager Class", ex.getMessage());
            MysqlHandler.getInstance().closeConnection(conn);
            return null;
        }
        
        //add the entry int orders relation
        try {
            String sqlQuery = "INSERT INTO `orders` (`User_id`,`Order_Time`,`credit_card_no`,`Credit_Expiry_Date`) VALUES (?,?,?,?);";
            statement = conn.prepareStatement(sqlQuery);
            statement.setInt(1, userId);
            statement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            statement.setString(3, creditcardNo);
            statement.setDate(4, creditExpiryDate);
            statement.executeUpdate();
        } catch (SQLException ex) {
            errorHandler.report("Order Manager Class", ex.getMessage());
            MysqlHandler.getInstance().closeConnection(conn);
            return null;
        }
        
        //Set the id of the order
        try {
            String sqlQuery = "SET @KEY = ( select LAST_INSERT_ID() ) ;";
            statement = conn.prepareStatement(sqlQuery);
            statement.execute();
        } catch (SQLException ex) {
            errorHandler.report("Order Manager Class", ex.getMessage());
            MysqlHandler.getInstance().closeConnection(conn);
            return null;
        }
        
        //add the authors.
        //update the books.
        try {
            String sqlQuery = "INSERT INTO `purchases` " +
            "(`Order_id`,`ISBN`,`no_of_copies`,`TotalPrice`) VALUES (@key,?,?,?);";
            
            String sqlQueryUpdate = "UPDATE `Books_ISBNs` " +
                    " SET Available_Quantity = ( Available_Quantity - ? ) " +
                    " WHERE ISBN = ? ";
            PreparedStatement updateBook;

            statement = conn.prepareStatement(sqlQuery);
            updateBook = conn.prepareStatement(sqlQueryUpdate);
            
            for(int i=0;i<items.size();i++){
                try {
                    //insert the order item
                    statement.setString(1, items.get(i).getKey().getISBN());
                    statement.setInt(2, items.get(i).getValue());
                    statement.setDouble(3, items.get(i).getValue()*items.get(i).getKey().getPrice());
                    //update the book availability
                    updateBook.setInt(1,items.get(i).getValue());
                    updateBook.setString(2,items.get(i).getKey().getISBN());
                } catch (NotFound ex) {
                    succ = false;
                }
                statement.executeUpdate();
                updateBook.executeUpdate();
            }
            
        } catch (SQLException ex) {
            errorHandler.report("Order Manager Class", ex.getMessage());
            MysqlHandler.getInstance().closeConnection(conn);
            return null;
        }
        
        //Error in inserting
        if(succ = false){
            MysqlHandler.getInstance().closeConnection(conn);
            return null;  
        }
        
        //try to add the order
        try {            
            conn.commit();
        } catch (SQLException ex) {
            errorHandler.report("Order Class", ex.getMessage());
            MysqlHandler.getInstance().closeConnection(conn);
            return null;
        }
        
        //return to autocommit
        try {
            conn.setAutoCommit(true);
        } catch (SQLException ex) {
            errorHandler.report("Order Manager Class", ex.getMessage());
            MysqlHandler.getInstance().closeConnection(conn);
            return null;
        }
        
        int orderId = -1;
        //get the id
        try {
            String sqlQuery = "SELECT @KEY AS `Order_Id`";
            
            statement = conn.prepareStatement(sqlQuery);
            statement.execute();
            
            ResultSet data = statement.getResultSet();
            if(data.next()){
                orderId = data.getInt("Order_Id");
            }else{
                succ = false;
            }
            
        } catch (SQLException ex) {
            errorHandler.report("Order Manager Class", ex.getMessage());
            MysqlHandler.getInstance().closeConnection(conn);
            return null;
        }
        
        //close the connection
        MysqlHandler.getInstance().closeConnection(conn);
        
        if(succ == false){
            return null;
        }
        
        Order order;
        try {
            order = new Order(orderId);
        } catch (NotFound ex) {
            return null;
        }
        
        return order;
    }
}
