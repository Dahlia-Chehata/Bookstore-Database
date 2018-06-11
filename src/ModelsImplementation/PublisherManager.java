package ModelsImplementation;

import HelperClasses.ErrorHandler;
import HelperClasses.NotFound;
import ModelsInterfaces.IPublisher;
import ModelsInterfaces.IPublisherManager;
import Mysql.MysqlHandler;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Fares
 */
public class PublisherManager implements IPublisherManager{

    //Mysql handler
    private PreparedStatement statementSQL;
    private final ErrorHandler errorHandler;
    
    public PublisherManager(){
        errorHandler = new ErrorHandler();
    }
    
    @Override
    public IPublisher getById(int id) {
        String sqlQuery = " SELECT * FROM `Publishers` " + 
                " WHERE `Publishers`.`Publisher_id` = ?";
        
        statementSQL = MysqlHandler.getInstance().getPreparedStatement(sqlQuery);
        ResultSet data = null;
        
        try{
            statementSQL.setInt(1,id);
            statementSQL.execute();
            data = statementSQL.getResultSet();
            
            //if found, create object and return it
            if(data.next()){
                try{
                    return new Publisher(data.getInt("Publisher_id"));
                } catch (NotFound ex){}
            }
            
        } catch (SQLException ex) {
            errorHandler.report("Publisher manager Class", ex.getMessage());
            errorHandler.terminate();
        } finally {
            //Close && nullify the statement
            MysqlHandler.getInstance().closePreparedStatement(statementSQL);
            statementSQL = null;
        }
        
        return null;
    }

    @Override
    public ArrayList<IPublisher> getByName(String name) {
        
        String sqlQuery = " SELECT * FROM `Publishers` " + 
                " WHERE `Publishers`.`Publisher_Name` = ?";
        ArrayList<IPublisher> publishersToReturn = new ArrayList<>();
        
        statementSQL = MysqlHandler.getInstance().getPreparedStatement(sqlQuery);
        ResultSet data = null;
        
        try{
            statementSQL.setString(1,name);
            statementSQL.execute();
            data = statementSQL.getResultSet();
            
            //if found, create object and return it
            while(data.next()){
                try{
                    publishersToReturn.add(new Publisher(data.getInt("Publisher_id")));
                } catch (NotFound ex){}
            }
            
        } catch (SQLException ex) {
            errorHandler.report("Publisher manager Class", ex.getMessage());
            errorHandler.terminate();
        } finally {
            //Close && nullify the statement
            MysqlHandler.getInstance().closePreparedStatement(statementSQL);
            statementSQL = null;
        }
        
        return publishersToReturn;
    }

    @Override
    public ArrayList<IPublisher> getAllPublishers() {
        String sqlQuery = " SELECT * FROM `Publishers` ";
        ArrayList<IPublisher> publishersToReturn = new ArrayList<>();
        
        statementSQL = MysqlHandler.getInstance().getPreparedStatement(sqlQuery);
        ResultSet data = null;
        
        try{
            statementSQL.execute();
            data = statementSQL.getResultSet();
            
            //if found, create object and return it
            while(data.next()){
                try{
                    publishersToReturn.add(new Publisher(data.getInt("Publisher_id")));
                } catch (NotFound ex){}
            }
            
        } catch (SQLException ex) {
            errorHandler.report("Publisher manager Class", ex.getMessage());
            errorHandler.terminate();
        } finally {
            //Close && nullify the statement
            MysqlHandler.getInstance().closePreparedStatement(statementSQL);
            statementSQL = null;
        }
        
        return publishersToReturn;
    }

    @Override
    public IPublisher addPublisher(String name, String address, String telephone) {

        String sqlQuery = " INSERT INTO `Publishers` " +
                " (Publisher_Name,Publisher_Address,Publisher_Telephone) " + 
                " Values (?,?,?) ";
        
        statementSQL = MysqlHandler.getInstance().getPreparedStatementWithKeys(sqlQuery);
        ResultSet data = null;
        
        try{
            
            statementSQL.setString(1, name);
            statementSQL.setString(2, address);
            statementSQL.setString(3, telephone);
            
            statementSQL.executeUpdate();
            data = statementSQL.getGeneratedKeys();
            
            //if inserted successfully, create object and return it
            if(data.next()){
                try{
                    return new Publisher(data.getInt(1));
                } catch (NotFound ex){}
            }
            
        } catch (SQLException ex) {
            errorHandler.report("Publisher manager Class", ex.getMessage());
        } finally {
            //Close && nullify the statement
            MysqlHandler.getInstance().closePreparedStatement(statementSQL);
            statementSQL = null;
        }
        
        return null;
    }
    
    public static void main(String[] args) throws NotFound{
        IPublisherManager mng = new PublisherManager();
        //mng.addStatus("normal");
        mng.addPublisher("name", "address", "telephone");
        System.out.println(mng.getAllPublishers().size());
        System.out.println(mng.getByName("name").size());
        
        IPublisher pub = mng.getById(1);
        System.out.println(pub.getAddress());
        System.out.println(pub.getBooks().size());
        System.out.println(pub.getId());
        System.out.println(pub.getName());
        System.out.println(pub.getTelephone());
        System.out.println(pub.changeAddress("newAddress"));
        System.out.println(pub.changeName("newName"));
        System.out.println(pub.changeTelephone("newTelephone"));
        Mysql.MysqlHandler.getInstance().state();

        //System.out.println(mng.getById(1).changeName("Hello"));
    }
    
}
