package ModelsImplementation;

import HelperClasses.ErrorHandler;
import HelperClasses.NotFound;
import ModelsInterfaces.ICategory;
import ModelsInterfaces.ICategoryManager;
import Mysql.MysqlHandler;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Fares
 */
public class CategoryManager implements ICategoryManager{

    //Mysql handler
    private PreparedStatement statementSQL;
    private ErrorHandler errorHandler;
    
    @Override
    public ICategory getCategoryByName(String categoryName) {
        
        String sqlQuery = "SELECT category_id FROM `Categories` " + 
                " WHERE `Categories`.`Category_Name` = ?";
        
        statementSQL = MysqlHandler.getInstance().getPreparedStatement(sqlQuery);
        ResultSet data = null;
        
        try {
            
            statementSQL.setString(1,categoryName);
            statementSQL.execute();
            
            data = statementSQL.getResultSet();
            if(data.next()){
                
                try{
                    //create and return the category
                    return new Category(data.getInt("category_id"));
                } catch(NotFound ex){
                    return null;
                }
                
            }else{
                return null;
            }
            
        } catch (SQLException ex) {
            errorHandler.report("Category Manager Class", ex.getMessage());
            errorHandler.terminate();
        }
        
        //Null the selectorStatemen
        MysqlHandler.getInstance().closePreparedStatement(statementSQL);
        statementSQL = null;
        
        //will never reach this statement
        return null;
    }

    @Override
    public ICategory getCategoryById(int id) {
        try{
            //create and return the category if possible
            return new Category(id);
        } catch(NotFound ex){
            return null;
        }
    }

    @Override
    public ArrayList<ICategory> getAllCategories() {
        
        String sqlQuery = "SELECT category_id FROM `Categories`"; 
        
        ArrayList<ICategory> toReturn = new ArrayList<>();
        statementSQL = MysqlHandler.getInstance().getPreparedStatement(sqlQuery);
        ResultSet data = null;
        
        try {
            
            statementSQL.execute();
            
            data = statementSQL.getResultSet();
            while(data.next()){
                try{
                    //create and return the category
                    toReturn.add(new Category(data.getInt("category_id")));
                } catch(NotFound ex){
                    //means that the category got deleted
                    //skip it.
                }
            }
            
        } catch (SQLException ex) {
            errorHandler.report("Category Manager Class", ex.getMessage());
            errorHandler.terminate();
        }
        
        //Null the selectorStatemen
        MysqlHandler.getInstance().closePreparedStatement(statementSQL);
        statementSQL = null;
        
        //will never reach this statement
        return toReturn;
    }

    @Override
    public boolean removeCategory(ICategory category) {
        //try to get the id
        int categoryId;
        try{
            categoryId = category.getId();
        //No Category = already deleted
        } catch (NotFound ex) {
            return true;
        }
        
        //update the entry
        String sqlQuery = "DELETE Categories WHERE category_id = ?";
        statementSQL = MysqlHandler.getInstance().getPreparedStatement(sqlQuery);
        
        try {
        
            statementSQL.setInt(1, categoryId);
            statementSQL.executeUpdate();
	    
        } catch (SQLException ex) {
            errorHandler.report("Category Manager Class", ex.getMessage());
            return false;
        }
        
        //Null the statementSQL
        MysqlHandler.getInstance().closePreparedStatement(statementSQL);
        statementSQL = null;
        
        return true;
    }

    @Override
    public ICategory addCategory(String categoryName) {
        
        //update the entry
        String sqlQuery = "INSERT INTO Categories (Category_Name) VALUES (?)";
        statementSQL = MysqlHandler.getInstance().getPreparedStatementWithKeys(sqlQuery);
        int categoryId;
        
        try {
        
            statementSQL.setString(1, categoryName);
            statementSQL.executeUpdate();
	    
            ResultSet key = statementSQL.getGeneratedKeys();
	    if(!key.next()){
                return null;
            }
            
            categoryId = key.getInt("category_id");
            
        } catch (SQLException ex) {
            errorHandler.report("Category Manager Class", ex.getMessage());
            return null;
        }
        
        //Null the statementSQL
        MysqlHandler.getInstance().closePreparedStatement(statementSQL);
        statementSQL = null;
        
        return this.getCategoryById(categoryId);
    }
    
}