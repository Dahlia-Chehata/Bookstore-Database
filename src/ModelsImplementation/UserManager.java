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

    public UserManager(){
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
    
    public static void main(String[] args) throws NotFound{
        UserManager mng = new UserManager();
        //mng.addUser("username", "newEmail@newEmail.com", "password", "fname", "lname", "sefaultShippingAddress", "phoneNumber", new UserStatus(1));
        System.out.println(mng.getAllUsers().size());
        System.out.println(mng.getByEmailAndPassword("email@email.com", "password"));
        System.out.println(mng.getByEmail("email@email.csom"));
        System.out.println(mng.getByUsername("ussdername").size());
        System.out.println(mng.getById(1).getFName());
        System.out.println("");
        System.out.println("");
        
        IUser user = mng.getById(1);
        System.out.println(user.getDefaultShippingAddress());
        System.out.println(user.getEmail());
        System.out.println(user.getFName());
        System.out.println(user.getId());
        System.out.println(user.getLName());
        //System.out.println(user.getOrders());
        System.out.println(user.getPhone());
        System.out.println(user.getStatus().getName());
        System.out.println(user.getUsername());
        System.out.println(user.changeDefaultShippingAddress("changeDefaultShippingAddress"));
        System.out.println(user.changeEmail("newEmail@newEmail.com"));
        System.out.println(user.changeFName("newFName"));
        System.out.println(user.changeLName("newLName"));
        System.out.println(user.changePassword("newPassword"));
        System.out.println(user.changePhone("newPhone"));
        System.out.println(user.changeStatus(new UserStatus(1)));
        System.out.println(user.changeUsername("newUsername"));
        System.out.println(user.comparePassword("newPassword"));
        Mysql.MysqlHandler.getInstance().state();

    }
}
