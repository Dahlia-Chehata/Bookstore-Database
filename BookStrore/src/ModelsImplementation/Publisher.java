package ModelsImplementation;

import HelperClasses.ErrorHandler;
import HelperClasses.NotFound;
import ModelsInterfaces.IBook;
import ModelsInterfaces.IPublisher;
import Mysql.MysqlHandler;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Fares
 */
public class Publisher implements IPublisher{

    //Mysql handler
    private PreparedStatement statementSQL;
    private ErrorHandler errorHandler;
    private int publiserId;
    
    public Publisher(int publiserId) throws NotFound{
        this.publiserId = publiserId;
        //vlidate the id
        getId();
    }
    
    private ResultSet dbPublisherGetter() throws NotFound{
        
        String sqlQuery = " SELECT * FROM `Publishers` " + 
                " WHERE `Publishers`.`Publisher_id` = ?";
        
        statementSQL = MysqlHandler.getInstance().getPreparedStatement(sqlQuery);
        ResultSet data = null;
        
        try{
            statementSQL.setInt(1,publiserId);
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
        ResultSet data = dbPublisherGetter();
        try{
            if(data.next()){
                return data.getInt("Publisher_id");
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
        ResultSet data = dbPublisherGetter();
        try{
            if(data.next()){
                return data.getString("Publisher_Name");
            }else{
                throw new NotFound();
            }
        } catch (SQLException ex){
            errorHandler.report("Publisher Class", ex.getMessage());
            errorHandler.terminate();
            return "";
        }
    }

    @Override
    public String getAddress() throws NotFound {
        ResultSet data = dbPublisherGetter();
        try{
            if(data.next()){
                return data.getString("Publisher_Address");
            }else{
                throw new NotFound();
            }
        } catch (SQLException ex){
            errorHandler.report("Publisher Class", ex.getMessage());
            errorHandler.terminate();
            return "";
        }
    }

    @Override
    public String getTelephone() throws NotFound {
        ResultSet data = dbPublisherGetter();
        try{
            if(data.next()){
                return data.getString("Publisher_Telephone");
            }else{
                throw new NotFound();
            }
        } catch (SQLException ex){
            errorHandler.report("Publisher Class", ex.getMessage());
            errorHandler.terminate();
            return "";
        }
    }

    private boolean dbPublisherUpdater(String colName, String newData) throws NotFound{
     
        //update the entry
        String sqlQuery = "UPDATE Publishers SET " + colName + " = ? WHERE Publisher_id = ?";
        statementSQL = MysqlHandler.getInstance().getPreparedStatementWithKeys(sqlQuery);
        
        try {
        
            statementSQL.setString(1, newData);
            statementSQL.setInt(2, publiserId);
            int rows = statementSQL.executeUpdate();

	    if(rows == 0){
                throw new NotFound();
            }
	    
        } catch (SQLException ex) {
            errorHandler.report("Publisher Class", ex.getMessage());
            return false;
        }
        
        //Null the selectorStatemen
        MysqlHandler.getInstance().closePreparedStatement(statementSQL);
        statementSQL = null;
        return true;
    }
    
    @Override
    public boolean changeName(String newName) throws NotFound {
        
        if(newName == null || newName.length() == 0 || newName.length() > 40){
            return false;
        }
        
        return dbPublisherUpdater("Publisher_Name",newName);
    }

    @Override
    public boolean changeAddress(String newAddress) throws NotFound {
        
        if(newAddress == null || newAddress.length() == 0 || newAddress.length() > 40){
            return false;
        }
        
        return dbPublisherUpdater("Publisher_Address",newAddress);
    }

    @Override
    public boolean changeTelephone(String newTelephone) throws NotFound {
        
        if(newTelephone == null || newTelephone.length() == 0 || newTelephone.length() > 40){
            return false;
        }
        
        return dbPublisherUpdater("Publisher_Telephone",newTelephone);
    }

    @Override
    public boolean publishBook(IBook bookToBePublished) throws NotFound {
        return bookToBePublished.changePublisher(this);
    }

    @Override
    public ArrayList<IBook> getBooks() throws NotFound {
        
        //validate the id
        getId();
        
        BookManager bookManager = new BookManager();
        return bookManager.getBooks().getBooksByPublisher(this).get();
    }
    
}
