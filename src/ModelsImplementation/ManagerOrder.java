package ModelsImplementation;

import HelperClasses.ErrorHandler;
import HelperClasses.NotFound;
import ModelsInterfaces.IBook;
import ModelsInterfaces.IManagerOrder;
import Mysql.MysqlHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Dahlia
 */
public class ManagerOrder implements IManagerOrder{
    
    //Mysql handler
    private PreparedStatement statementSQL;
    //Error handler
    private final ErrorHandler errorHandler;
   
    public ManagerOrder(){
        errorHandler = new ErrorHandler();	
    }
    
    @Override
    public boolean addOrder(IBook book, int quantity) throws NotFound{
        
    	String ISBN;
    	try {
    		ISBN = book.getISBN();
    	}catch(NotFound ex) {return false;}
    	
        //update the entry
        String sqlQuery = "INSERT INTO manager_order (ISBN,no_of_copies) VALUES (?,?) "
                + "ON DUPLICATE KEY UPDATE no_of_copies=no_of_copies+?";
        statementSQL = MysqlHandler.getInstance().getPreparedStatement(sqlQuery);
        
        try {
        
            statementSQL.setString(1, ISBN);
            statementSQL.setInt(2, quantity);
            statementSQL.setInt(3, quantity);
            
            statementSQL.executeUpdate();
	    
        } catch (SQLException ex) {
            errorHandler.report("Author Class", ex.getMessage());
            return false;
        } finally {
            //Null the selectorStatemen
            MysqlHandler.getInstance().closePreparedStatement(statementSQL);
            statementSQL = null;  
        }

        return true;
    }

    @Override
    public void confirmOrder(int managerOrderId) throws NotFound {
       
        //delete the entry
        String sqlQuery = "DELETE FROM manager_order WHERE mgr_order_id = ?";
        statementSQL = MysqlHandler.getInstance().getPreparedStatement(sqlQuery);
        
        try {
        
            statementSQL.setInt(1, managerOrderId);
            statementSQL.executeUpdate();
	    
        } catch (SQLException ex) {
            errorHandler.report("Author Class", ex.getMessage());
        } finally {
            //Null the selectorStatemen
            MysqlHandler.getInstance().closePreparedStatement(statementSQL);
            statementSQL = null;  
        }

        	          
    }

    @Override
    public ArrayList <singleOrder> getManagerOrder() {
        
        ArrayList <singleOrder> list = new ArrayList <>();

        String sqlQuery = "SELECT * FROM manager_order";
            statementSQL = MysqlHandler.getInstance().getPreparedStatement(sqlQuery);
            
            try {
            
                statementSQL.execute();
                ResultSet result = statementSQL.getResultSet();
                
                while(result.next()){
                	try {
				list.add(new singleOrder(result.getInt("mgr_order_id"),
				result.getInt("no_of_copies"),new Book(result.getString("ISBN"))));
			} catch (NotFound e) {
				e.printStackTrace();
			}
                }
                
            } catch (SQLException ex) {
                errorHandler.report("Author Class", ex.getMessage());
            } finally {
                //Null the selectorStatemen
                MysqlHandler.getInstance().closePreparedStatement(statementSQL);
                statementSQL = null;  
            }
        	
        	return list;
    }
    
    public static void main(String argc[]) throws NotFound{
        IManagerOrder x = new ManagerOrder();
        x.confirmOrder(7);
    }
}