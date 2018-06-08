package ModelsImplementation;

import HelperClasses.ErrorHandler;
import HelperClasses.NotFound;
import ModelsInterfaces.IBook;
import ModelsInterfaces.IOrderItem;
import Mysql.MysqlHandler;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Fares
 */
public class OrderItem implements IOrderItem{

    //Mysql handler
    private PreparedStatement statementSQL;
    //Error handler
    private final ErrorHandler errorHandler;
    
    private final int orderId;
    private final String ISBN;
    
    public OrderItem(int orderId, String ISBN) throws NotFound{
        
        errorHandler = new ErrorHandler();
        this.orderId = orderId;
        this.ISBN = ISBN;
        
        getQuantity();
    }
    
    private ResultSet dbOrderGetter() throws NotFound{
        
        String sqlQuery = " SELECT * FROM `purchases` " + 
                " WHERE `purchases`.`Order_id` = ? AND `purchases`.`ISBN` = ?";
        
        statementSQL = MysqlHandler.getInstance().getPreparedStatement(sqlQuery);
        ResultSet data = null;
        
        try{
            
            statementSQL.setInt(1,orderId);
            statementSQL.setString(2,ISBN);
            
            statementSQL.execute();
            data = statementSQL.getResultSet();
        } catch (SQLException ex) {
            errorHandler.report("Order Item Class", ex.getMessage());
            errorHandler.terminate();
        }
        
        return data;
    }
    
    @Override
    public int getQuantity() throws NotFound {
        ResultSet data = dbOrderGetter();
        try{
            if(data.next()){
                return data.getInt("no_of_copies");
            }else{
                throw new NotFound();
            }
        } catch (SQLException ex){
            errorHandler.report("Order Item Class", ex.getMessage());
            errorHandler.terminate();
            return -1;
        } finally {
            //Close && nullify the statement
            MysqlHandler.getInstance().closePreparedStatement(statementSQL);
            statementSQL = null;
        }
    }

    @Override
    public double getTotalPrice() throws NotFound {
        ResultSet data = dbOrderGetter();
        try{
            if(data.next()){
                return data.getDouble("TotalPrice");
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
    public IBook getBook() throws NotFound {
        ResultSet data = dbOrderGetter();
        try{
            if(data.next()){
                try{
                    return new Book(data.getString("ISBN"));
                } catch (NotFound ex){
                    return this.getBook();
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
    
}
