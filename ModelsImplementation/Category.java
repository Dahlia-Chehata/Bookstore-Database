package ModelsImplementation;

import HelperClasses.ErrorHandler;
import HelperClasses.NotFound;
import ModelsInterfaces.ICategory;
import Mysql.MysqlHandler;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Fairous
 */
public class Category implements ICategory{

    //Mysql handler
    private PreparedStatement statementSQL;
    private final ErrorHandler errorHandler;
    
    int categoryId;
    
    public Category(int categoryId) throws NotFound{
        
        //set the id
        this.categoryId = categoryId;
        errorHandler = new ErrorHandler();
        
        //Validate the Id
        getId();
    }
    
    private ResultSet dbCategoryGetter() throws NotFound{
        
        String sqlQuery = "SELECT * FROM `Categories` " + 
                " WHERE `Categories`.`category_id` = ?";
        
        statementSQL = MysqlHandler.getInstance().getPreparedStatement(sqlQuery);
        ResultSet data = null;
        
        try {
            
            statementSQL.setInt(1,categoryId);
            statementSQL.execute();
            
            data = statementSQL.getResultSet();
            
        } catch (SQLException ex) {
            errorHandler.report("Category Class", ex.getMessage());
            errorHandler.terminate();
        }
        
        return data;
    }
    
    
    @Override
    public int getId() throws NotFound {
        
        ResultSet data = dbCategoryGetter();
        int id = -1;
        
        try{
            if(data.next()){
                id = data.getInt("category_id");
            }else{
                throw new NotFound();
            }
        } catch (SQLException ex){
            errorHandler.report("Category Class", ex.getMessage());
            errorHandler.terminate();
            return -1;
        } finally {
            //Close && nullify the statement
            MysqlHandler.getInstance().closePreparedStatement(statementSQL);
            statementSQL = null;
        }
        
        return id;
    }

    @Override
    public String getName() throws NotFound {
        
        ResultSet data = dbCategoryGetter();
        String name = "";
        
        try{
            if(data.next()){
                name = data.getString("Category_Name");
            }else{
                throw new NotFound();
            }
        } catch (SQLException ex){
            errorHandler.report("Category Class", ex.getMessage());
            errorHandler.terminate();
        } finally {
            //Close && nullify the statement
            MysqlHandler.getInstance().closePreparedStatement(statementSQL);
            statementSQL = null;
        }
        
        return name;
    }

    @Override
    public boolean changeName(String newName) throws NotFound {
        
        //update the entry
        String sqlQuery = "UPDATE " + "Categories" + " SET " + " Category_Name " + " = ? WHERE `Categories`.`category_id` = ?";
        statementSQL = MysqlHandler.getInstance().getPreparedStatementWithKeys(sqlQuery);
        boolean toReturn = true;
        
        try {
        
            statementSQL.setString(1, newName);
            statementSQL.setInt(1, categoryId);
            int rows = statementSQL.executeUpdate();

	    if(rows == 0){
                throw new NotFound();
            }
	    
        } catch (SQLException ex) {
    
            errorHandler.report("Category Class", ex.getMessage());
            
            // Null the selectorStatemen
            MysqlHandler.getInstance().closePreparedStatement(statementSQL);
            statementSQL = null;

            toReturn = false;
        } finally {
            //Null the statement
            MysqlHandler.getInstance().closePreparedStatement(statementSQL);
            statementSQL = null;
        }
                
        return toReturn;
    }
    
}
