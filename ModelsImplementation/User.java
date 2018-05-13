package ModelsImplementation;

import HelperClasses.ErrorHandler;
import HelperClasses.NotFound;
import HelperClasses.SHA256;
import ModelsInterfaces.IOrder;
import ModelsInterfaces.IUser;
import ModelsInterfaces.IUserStatus;
import Mysql.MysqlHandler;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 *
 * @author Fares
 */
public class User implements IUser{

    //Mysql handler
    private PreparedStatement statementSQL;
    //Error handler
    private ErrorHandler errorHandler;
    private int userId;
    
    public User(int userId) throws NotFound{
        //set the data
        this.userId = userId;
        //vlidate the id
        getId();
    }
    
    private ResultSet dbUserGetter() throws NotFound{
        
        String sqlQuery = " SELECT * FROM `Users` " + 
                " WHERE `Users`.`User_id` = ?";
        
        statementSQL = MysqlHandler.getInstance().getPreparedStatement(sqlQuery);
        ResultSet data = null;
        
        try{
            statementSQL.setInt(1,userId);
            statementSQL.execute();
            data = statementSQL.getResultSet();
        } catch (SQLException ex) {
            errorHandler.report("User Class", ex.getMessage());
            errorHandler.terminate();
        }
        
        //Null the selectorStatement
        MysqlHandler.getInstance().closePreparedStatement(statementSQL);
        statementSQL = null;
        
        return data;
    }
    
    @Override
    public int getId() throws NotFound {
        ResultSet data = dbUserGetter();
        try{
            if(data.next()){
                return data.getInt("User_id");
            }else{
                throw new NotFound();
            }
        } catch (SQLException ex){
            errorHandler.report("User Class", ex.getMessage());
            errorHandler.terminate();
            return -1;
        }
    }

    @Override
    public String getFName() throws NotFound {
        ResultSet data = dbUserGetter();
        try{
            if(data.next()){
                return data.getString("First_Name");
            }else{
                throw new NotFound();
            }
        } catch (SQLException ex){
            errorHandler.report("User Class", ex.getMessage());
            errorHandler.terminate();
            return "";
        }
    }

    @Override
    public String getLName() throws NotFound {
        ResultSet data = dbUserGetter();
        try{
            if(data.next()){
                return data.getString("last_Name");
            }else{
                throw new NotFound();
            }
        } catch (SQLException ex){
            errorHandler.report("User Class", ex.getMessage());
            errorHandler.terminate();
            return "";
        }
    }

    @Override
    public String getEmail() throws NotFound {
        ResultSet data = dbUserGetter();
        try{
            if(data.next()){
                return data.getString("Email");
            }else{
                throw new NotFound();
            }
        } catch (SQLException ex){
            errorHandler.report("User Class", ex.getMessage());
            errorHandler.terminate();
            return "";
        }
    }

    @Override
    public String getPhone() throws NotFound {
        ResultSet data = dbUserGetter();
        try{
            if(data.next()){
                return data.getString("Phone_Number");
            }else{
                throw new NotFound();
            }
        } catch (SQLException ex){
            errorHandler.report("User Class", ex.getMessage());
            errorHandler.terminate();
            return "";
        }
    }

    @Override
    public IUserStatus getStatus() throws NotFound {
        ResultSet data = dbUserGetter();
        try{
            if(data.next()){
                return new UserStatus(data.getInt("Status_id"));
            }else{
                throw new NotFound();
            }
        } catch (SQLException ex){
            errorHandler.report("User Class", ex.getMessage());
            errorHandler.terminate();
            return null;
        }
    }

    @Override
    public String getDefaultShippingAddress() throws NotFound {
        ResultSet data = dbUserGetter();
        try{
            if(data.next()){
                return data.getString("Default_Shipping_Address");
            }else{
                throw new NotFound();
            }
        } catch (SQLException ex){
            errorHandler.report("User Class", ex.getMessage());
            errorHandler.terminate();
            return "";
        }
    }


    
    @Override
    public boolean comparePassword(String password) throws NotFound {
        
        ResultSet data = dbUserGetter();
        String storedPassword;
        
        try{
            if(data.next()){
                storedPassword = data.getString("Password");
            }else{
                throw new NotFound();
            }
        } catch (SQLException ex){
            errorHandler.report("User Class", ex.getMessage());
            errorHandler.terminate();
            return false;
        }
        
        //hasher
        SHA256 hasher = new SHA256();
        
        return storedPassword.equals(hasher.hash(password));
    }
    
    private boolean dbUserUpdater(String colName, String newData) throws NotFound{
     
        //update the entry
        String sqlQuery = "UPDATE `Users` SET " + colName + " = ? WHERE User_id = ?";
        statementSQL = MysqlHandler.getInstance().getPreparedStatementWithKeys(sqlQuery);
        
        try {
        
            statementSQL.setString(1, newData);
            statementSQL.setInt(2, userId);
            int rows = statementSQL.executeUpdate();

	    if(rows == 0){
                throw new NotFound();
            }
	    
        } catch (SQLException ex) {
            errorHandler.report("User Class", ex.getMessage());
            return false;
        }
        
        //Null the selectorStatement
        MysqlHandler.getInstance().closePreparedStatement(statementSQL);
        statementSQL = null;
        return true;
    }
    
    private boolean dbUserUpdater( String colName, int newData) throws NotFound{
     
        //update the entry
        String sqlQuery = "UPDATE `Users` SET " + colName + " = ?";
        statementSQL = MysqlHandler.getInstance().getPreparedStatementWithKeys(sqlQuery);
        
        try {
        
            statementSQL.setInt(1, newData);
            int rows = statementSQL.executeUpdate();

	    if(rows == 0){
                throw new NotFound();
            }
	    
        } catch (SQLException ex) {
            errorHandler.report("User Class", ex.getMessage());
            return false;
        }
        
        //Null the selectorStatement
        MysqlHandler.getInstance().closePreparedStatement(statementSQL);
        statementSQL = null;
        return true;
    }

    @Override
    public ArrayList<IOrder> getOrders() throws NotFound {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean changeFName(String newFName) throws NotFound {
        
        if(newFName == null || newFName.length() == 0 || newFName.length() > 40){
            return false;
        }
        
        return dbUserUpdater("First_Name",newFName);
    }

    @Override
    public boolean changeLName(String newLName) throws NotFound {
        
        if(newLName == null || newLName.length() == 0 || newLName.length() > 40){
            return false;
        }
        
        return dbUserUpdater("last_Name",newLName);
    }

    @Override
    public boolean changeEmail(String newEmail) throws NotFound {
        
        if(newEmail == null || newEmail.length() == 0 || newEmail.length() > 40){
            return false;
        }
        
        return dbUserUpdater("Email",newEmail);
    }

    @Override
    public boolean changePhone(String newPhone) throws NotFound {
        
        if(newPhone == null || newPhone.length() == 0 || newPhone.length() > 40){
            return false;
        }
        
        return dbUserUpdater("Phone_Number",newPhone);
    }

    @Override
    public boolean changeStatus(IUserStatus newStatus) throws NotFound {
        
        int newStatusId;
        try{
            newStatusId = newStatus.getId();
        } catch (NotFound ex){
            return false;
        }
        
        return dbUserUpdater("Status_id",newStatusId);
    }

    @Override
    public boolean changeDefaultShippingAddress(String newDefaultShippingAddress) throws NotFound {
        
        if(newDefaultShippingAddress == null || newDefaultShippingAddress.length() == 0 || newDefaultShippingAddress.length() > 40){
            return false;
        }
        
        return dbUserUpdater("Default_Shipping_Address",newDefaultShippingAddress);
    }

    @Override
    public boolean changePassword(String newPassword) throws NotFound {
        
        if(newPassword == null || newPassword.length() == 0 || newPassword.length() > 40){
            return false;
        }
        
        //hasher
        SHA256 hasher = new SHA256();
        
        return dbUserUpdater("Password",hasher.hash(newPassword));
    }

    @Override
    public boolean removeOrder(IOrder orderToBeDeleted) throws NotFound {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getUsername() throws NotFound {
        ResultSet data = dbUserGetter();
        try{
            if(data.next()){
                return data.getString("User_Name");
            }else{
                throw new NotFound();
            }
        } catch (SQLException ex){
            errorHandler.report("User Class", ex.getMessage());
            errorHandler.terminate();
            return "";
        }
    }

    @Override
    public boolean changeUsername(String newUsername) throws NotFound {
        if(newUsername == null || newUsername.length() == 0 || newUsername.length() > 40){
            return false;
        }
        
        return dbUserUpdater("User_Name",newUsername);
    }
    
}
