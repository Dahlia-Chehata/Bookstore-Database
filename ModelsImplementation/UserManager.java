package ModelsImplementation;

import HelperClasses.ErrorHandler;
import HelperClasses.SHA256;
import HelperClasses.NotFound;
import ModelsInterfaces.IUser;
import ModelsInterfaces.IUserManager;
import ModelsInterfaces.IUserStatus;
import Mysql.MysqlHandler;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Fairous
 */
public class UserManager implements IUserManager{
    //Mysql handler
    private PreparedStatement statementSQL;
    //Error handler
    private final ErrorHandler errorHandler;

    UserManager(){
        errorHandler = new ErrorHandler();
    }
    
    @Override
    public IUser getById(int id) {
        try{
            return new User(id);
        } catch (NotFound ex){
            return null;
        }
    }

    @Override
    public ArrayList<IUser> getByUsername(String username) {
        
        String sqlQuery = " SELECT User_id FROM `Users` " + 
                " WHERE `Users`.`User_Name` = ?";
        
        statementSQL = MysqlHandler.getInstance().getPreparedStatement(sqlQuery);
        ResultSet data = null;
        ArrayList<IUser> users = new ArrayList<>();
        
        try{
            statementSQL.setString(1,username);
            statementSQL.execute();
            data = statementSQL.getResultSet();
            
            while(data.next()){
                try{
                    users.add(new User(data.getInt("User_id")));
                }catch(NotFound ex){}
            }
            
        } catch (SQLException ex) {
            errorHandler.report("User Manager Class", ex.getMessage());
            errorHandler.terminate();
        } finally {
            //Close && nullify the statement
            MysqlHandler.getInstance().closePreparedStatement(statementSQL);
            statementSQL = null;
        }
        
        return users;
    }

    @Override
    public IUser getByEmail(String email) {
        String sqlQuery = " SELECT User_id FROM `Users` " + 
                " WHERE `Users`.`Email` = ?";
        
        statementSQL = MysqlHandler.getInstance().getPreparedStatement(sqlQuery);
        ResultSet data = null;
        
        try{
            statementSQL.setString(1,email);
            statementSQL.execute();
            data = statementSQL.getResultSet();
            
            if(data.next()){
                try{
                    return new User(data.getInt("User_id"));
                }catch(NotFound ex){}
            }
            
        } catch (SQLException ex) {
            errorHandler.report("User Manager Class", ex.getMessage());
            errorHandler.terminate();
        } finally {
            //Close && nullify the statement
            MysqlHandler.getInstance().closePreparedStatement(statementSQL);
            statementSQL = null;
        }
        
        return null;
    }

    @Override
    public IUser getByEmailAndPassword(String email, String password) {
        
        try{
            
            //get the user by email
            IUser wantedUser = getByEmail(email);
            
            //check if user found
            if(wantedUser == null){
                return null;
            }
            
            //compare the password
            if(wantedUser.comparePassword(password)){
                return wantedUser;
            }
            
            //if password didn't match
            return null;
            
        }catch(NotFound ex){
            return null;
        }
    }

    @Override
    public ArrayList<IUser> getAllUsers() {
        
        String sqlQuery = " SELECT User_id FROM `Users` ";
        
        statementSQL = MysqlHandler.getInstance().getPreparedStatement(sqlQuery);
        ResultSet data = null;
        ArrayList<IUser> users = new ArrayList<>();
        
        try{
            statementSQL.execute();
            data = statementSQL.getResultSet();
            
            while(data.next()){
                try{
                    users.add(new User(data.getInt("User_id")));
                }catch(NotFound ex){}
            }
            
        } catch (SQLException ex) {
            errorHandler.report("User Manager Class", ex.getMessage());
            errorHandler.terminate();
        } finally {
            //Close && nullify the statement
            MysqlHandler.getInstance().closePreparedStatement(statementSQL);
            statementSQL = null;
        }
        
        return users;
    }

    @Override
    public IUser addUser(String username, String email, String password, String fname,
            String lname, String defaultShippingAddress, String phoneNumber, IUserStatus status) {
        
        
        //try to get the status_id
        int statusId;
        try{
            statusId = status.getId();
        } catch (NotFound ex){
            return null;
        }
        
        //hasher
        SHA256 hasher = new SHA256();
        
        String sqlQuery = " INSERT INTO `Users` " +
        " (User_Name,Password,First_Name,last_Name,Email,Phone_Number,Status_id,Default_Shipping_Address) " + 
        " Values (?,?,?,?,?,?,?,?) ";
        
        statementSQL = MysqlHandler.getInstance().getPreparedStatementWithKeys(sqlQuery);
        ResultSet data = null;
        
        try{

            statementSQL.setString(1, username);
            statementSQL.setString(2, hasher.hash(password));
            statementSQL.setString(3, fname);
            statementSQL.setString(4, lname);
            statementSQL.setString(5, email);
            statementSQL.setString(6, phoneNumber);
            statementSQL.setInt(7, statusId);
            statementSQL.setString(8, defaultShippingAddress);
            
            statementSQL.executeUpdate();
            data = statementSQL.getGeneratedKeys();
            
            //if inserted successfully, create object and return it
            if(data.next()){
                try{
                    return new User(data.getInt(1));
                } catch (NotFound ex){}
            }
            
        } catch (SQLException ex) {
            errorHandler.report("User Manager Class", ex.getMessage());
        } finally {
            //Close && nullify the statement
            MysqlHandler.getInstance().closePreparedStatement(statementSQL);
            statementSQL = null;
        }
        
        return null;
    }
}
