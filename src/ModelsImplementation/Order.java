package ModelsImplementation;

import HelperClasses.ErrorHandler;
import HelperClasses.NotFound;
import ModelsInterfaces.IOrder;
import ModelsInterfaces.IOrderItem;
import ModelsInterfaces.IUser;
import Mysql.MysqlHandler;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Fares
 */
public class Order implements IOrder{

    //Mysql handler
    private PreparedStatement statementSQL;
    //Error handler
    private final ErrorHandler errorHandler;
    private final int orderId;
    
    public Order(int orderId) throws NotFound{
        errorHandler = new ErrorHandler();
        this.orderId = orderId;
        getId();
    }
    
    private ResultSet dbOrderGetter() throws NotFound{
        
        String sqlQuery = " SELECT * FROM `orders` " + 
                " WHERE `orders`.`Id` = ?";
        
        statementSQL = MysqlHandler.getInstance().getPreparedStatement(sqlQuery);
        ResultSet data = null;
        
        try{
            statementSQL.setInt(1,orderId);
            statementSQL.execute();
            data = statementSQL.getResultSet();
        } catch (SQLException ex) {
            errorHandler.report("Order Class", ex.getMessage());
            errorHandler.terminate();
        }
        
        return data;
    }
    
    @Override
    public int getId() throws NotFound {
        ResultSet data = dbOrderGetter();
        try{
            if(data.next()){
                return data.getInt("Id");
            }else{
                throw new NotFound();
            }
        } catch (SQLException ex){
            errorHandler.report("Order Class", ex.getMessage());
            errorHandler.terminate();
            return -1;
        } finally {
            //Close && nullify the statement
            MysqlHandler.getInstance().closePreparedStatement(statementSQL);
            statementSQL = null;
        }
    }

    @Override
    public IUser getUser() throws NotFound {
        ResultSet data = dbOrderGetter();
        try{
            if(data.next()){
            
                try{
                    return new User(data.getInt("User_id"));
                } catch (NotFound ex){
                    return this.getUser();
                }
                
            }else{
                throw new NotFound();
            }
        } catch (SQLException ex){
            errorHandler.report("Order Class", ex.getMessage());
            errorHandler.terminate();
            return null;
        } finally {
            //Close && nullify the statement
            MysqlHandler.getInstance().closePreparedStatement(statementSQL);
            statementSQL = null;
        }
    }

    @Override
    public double getTotalPrice() throws NotFound {
        String sqlQuery = " SELECT SUM(purchases.TotalPrice) AS TotalPrice  FROM `orders` " + 
                " INNER JOIN  purchases ON purchases.Order_id = orders.Id " +
                " WHERE `orders`.`Id` = ?";
        
        ArrayList<IOrderItem> dataToReturn = new ArrayList<>();
        
        statementSQL = MysqlHandler.getInstance().getPreparedStatement(sqlQuery);
        ResultSet data = null;
        
        try{
            statementSQL.setInt(1,orderId);
            statementSQL.execute();
            data = statementSQL.getResultSet();
            
            if(data.next()){
                return data.getDouble("TotalPrice");
            }
            
        } catch (SQLException ex) {
            errorHandler.report("Order Class", ex.getMessage());
            errorHandler.terminate();
        } finally {
            //Close && nullify the statement
            MysqlHandler.getInstance().closePreparedStatement(statementSQL);
            statementSQL = null;
        }
        
        //Null the selectorStatement
        MysqlHandler.getInstance().closePreparedStatement(statementSQL);
        statementSQL = null;
        
        return 0;
    }

    @Override
    public String getTimeStamp() throws NotFound {
        ResultSet data = dbOrderGetter();
        try{
            if(data.next()){
                return data.getDate("Order_Time").toString();
            }else{
                throw new NotFound();
            }
        } catch (SQLException ex){
            errorHandler.report("Order Class", ex.getMessage());
            errorHandler.terminate();
            return null;
        } finally {
            //Close && nullify the statement
            MysqlHandler.getInstance().closePreparedStatement(statementSQL);
            statementSQL = null;
        }
    }

    @Override
    public String getCreditCardNo() throws NotFound {
        ResultSet data = dbOrderGetter();
        try{
            if(data.next()){
                return data.getString("credit_card_no");
            }else{
                throw new NotFound();
            }
        } catch (SQLException ex){
            errorHandler.report("Order Class", ex.getMessage());
            errorHandler.terminate();
            return null;
        } finally {
            //Close && nullify the statement
            MysqlHandler.getInstance().closePreparedStatement(statementSQL);
            statementSQL = null;
        }
    }

    @Override
    public String getCreditExpiryDate() throws NotFound {
        ResultSet data = dbOrderGetter();
        try{
            if(data.next()){
                return data.getString("Credit_Expiry_Date");
            }else{
                throw new NotFound();
            }
        } catch (SQLException ex){
            errorHandler.report("Order Class", ex.getMessage());
            errorHandler.terminate();
            return null;
        } finally {
            //Close && nullify the statement
            MysqlHandler.getInstance().closePreparedStatement(statementSQL);
            statementSQL = null;
        }
    }

    @Override
    public ArrayList<IOrderItem> getBooks() throws NotFound {
        
        String sqlQuery = " SELECT purchases.Order_id,purchases.ISBN  FROM `orders` " + 
                " INNER JOIN  purchases ON purchases.Order_id = orders.Id " +
                " WHERE `orders`.`Id` = ?";
        
        ArrayList<IOrderItem> dataToReturn = new ArrayList<>();
        
        statementSQL = MysqlHandler.getInstance().getPreparedStatement(sqlQuery);
        ResultSet data = null;
        
        try{
            statementSQL.setInt(1,orderId);
            statementSQL.execute();
            data = statementSQL.getResultSet();
            
            while(data.next()){
                
                try{
                    dataToReturn.add(new OrderItem(data.getInt("Order_id"),data.getString("ISBN")));
                } catch(NotFound ex){}
            
            }
            
        } catch (SQLException ex) {
            errorHandler.report("Order Class", ex.getMessage());
            errorHandler.terminate();
        } finally {
            //Close && nullify the statement
            MysqlHandler.getInstance().closePreparedStatement(statementSQL);
            statementSQL = null;
        }
        
        return dataToReturn;
    }
    
}
