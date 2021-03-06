package ModelsImplementation;

import HelperClasses.ErrorHandler;
import HelperClasses.NotFound;
import ModelsInterfaces.IUserStatus;
import ModelsInterfaces.IUserStatusManager;
import Mysql.MysqlHandler;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Fares
 */
public class UserStatusManager implements IUserStatusManager{
    
    //Mysql handler
    private PreparedStatement statementSQL;
    //Error handler
    private final ErrorHandler errorHandler;

    public UserStatusManager(){
        errorHandler = new ErrorHandler();
    }
    
    @Override
    public ArrayList<IUserStatus> getAllUserStatuses() {

        ArrayList<IUserStatus> statusesToBeReturned = new ArrayList<>();
        
        String sqlQuery = " SELECT * FROM `Status_Menu` ";
        
        statementSQL = MysqlHandler.getInstance().getPreparedStatement(sqlQuery);
        ResultSet data = null;
        
        try{
            statementSQL.execute();
            data = statementSQL.getResultSet();
            
            //Try to add the status to the ArrayList
            while(data.next()){
                try{
                    statusesToBeReturned.add(new UserStatus(data.getInt("Status_id")));
                }catch(NotFound ex){}
            }
            
        } catch (SQLException ex) {
            errorHandler.report("UserStatus Manager Class", ex.getMessage());
            errorHandler.terminate();
        } finally {
            //Close && nullify the statement
            MysqlHandler.getInstance().closePreparedStatement(statementSQL);
            statementSQL = null;
        }
               
        return statusesToBeReturned;
    }

    @Override
    public IUserStatus getById(int id) {
        try{
            return new UserStatus(id);
        } catch (NotFound ex){
            return null;
        }
    }

    @Override
    public IUserStatus addStatus(String newUserStatus) {
        
        String sqlQuery = " INSERT INTO `Status_Menu` " +
                " (Permission) " + 
                " VALUES (?);";
        
        statementSQL = MysqlHandler.getInstance().getPreparedStatementWithKeys(sqlQuery);
        ResultSet data = null;
        
        try{
            
            statementSQL.setString(1, newUserStatus);
            
            statementSQL.executeUpdate();
            data = statementSQL.getGeneratedKeys();
            
            //if inserted successfully, create object and return it
            if(data.next()){
                try{
                    return new UserStatus(data.getInt(1));
                } catch (NotFound ex){}
            }
            
        } catch (SQLException ex) {
            errorHandler.report("UserStatus Manager Class", ex.getMessage());
        } finally {
            //Close && nullify the statement
            MysqlHandler.getInstance().closePreparedStatement(statementSQL);
            statementSQL = null;
        }
        
        return null;
    }

    public static void main(String[] args) throws NotFound{
        UserStatusManager mng = new UserStatusManager();
        mng.addStatus("normal");
        System.out.println(mng.getAllUserStatuses().size());
        System.out.println(mng.getById(1).changeName("Hello"));
        Mysql.MysqlHandler.getInstance().state();
    }
    
}
