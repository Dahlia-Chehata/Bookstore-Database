package ModelsImplementation;

import HelperClasses.ErrorHandler;
import HelperClasses.NotFound;
import ModelsInterfaces.IUserStatus;
import Mysql.MysqlHandler;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Fares
 */
public class UserStatus implements IUserStatus{

    //Mysql handler
    private PreparedStatement statementSQL;
    //Error handler
    private ErrorHandler errorHandler;
    private int statusId;
    
    public UserStatus(int statusId) throws NotFound{
        //set the id
        this.statusId = statusId;
        //vlidate the id
        getId();
    }
    
    private ResultSet dbUserStatusGetter() throws NotFound{
        
        String sqlQuery = " SELECT * FROM `Status_Menu` " + 
                " WHERE `Status_Menu`.`Status_id` = ?";
        
        statementSQL = MysqlHandler.getInstance().getPreparedStatement(sqlQuery);
        ResultSet data = null;
        
        try{
            statementSQL.setInt(1,statusId);
            statementSQL.execute();
            data = statementSQL.getResultSet();
        } catch (SQLException ex) {
            errorHandler.report("Publisher Class", ex.getMessage());
            errorHandler.terminate();
        }
        
        //Null the selectorStatement
        MysqlHandler.getInstance().closePreparedStatement(statementSQL);
        statementSQL = null;
        
        return data;
    }
    
    @Override
    public int getId() throws NotFound {
        ResultSet data = dbUserStatusGetter();
        try{
            if(data.next()){
                return data.getInt("Status_id");
            }else{
                throw new NotFound();
            }
        } catch (SQLException ex){
            errorHandler.report("Publisher Class", ex.getMessage());
            errorHandler.terminate();
            return -1;
        }
    }

    @Override
    public String getName() throws NotFound {
        ResultSet data = dbUserStatusGetter();
        try{
            if(data.next()){
                return data.getString("Permission");
            }else{
                throw new NotFound();
            }
        } catch (SQLException ex){
            errorHandler.report("UserStatus Class", ex.getMessage());
            errorHandler.terminate();
            return "";
        }
    }

    @Override
    public boolean changeName(String newName) throws NotFound {
     
        //update the entry
        String sqlQuery = "UPDATE Status_Menu SET Permission = ? WHERE Status_id = ?";
        statementSQL = MysqlHandler.getInstance().getPreparedStatement(sqlQuery);
        
        try {
        
            statementSQL.setString(1, newName);
            statementSQL.setInt(2, statusId);
            int rows = statementSQL.executeUpdate();
            
            //no row changed
	    if(rows == 0){
                throw new NotFound();
            }
	    
        } catch (SQLException ex) {
            errorHandler.report("UserStatus Class", ex.getMessage());
            MysqlHandler.getInstance().closePreparedStatement(statementSQL);
            statementSQL = null;
            return false;
        }
        
        //Null the selectorStatemen
        MysqlHandler.getInstance().closePreparedStatement(statementSQL);
        statementSQL = null;
        return true;
    }
    
}
