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
    private ErrorHandler errorHandler;
    
    int categoryId;
    
    public Category(int categoryId) throws NotFound{
        //set the id
        this.categoryId = categoryId;
        
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
        
        //Null the selectorStatemen
        MysqlHandler.getInstance().closePreparedStatement(statementSQL);
        statementSQL = null;
        
        return data;
    }
    
    
    @Override
    public int getId() throws NotFound {
        ResultSet data = dbCategoryGetter();
        try{
            if(data.next()){
                return data.getInt("category_id");
            }else{
                throw new NotFound();
            }
        } catch (SQLException ex){
            errorHandler.report("Category Class", ex.getMessage());
            errorHandler.terminate();
            return -1;
        }
    }

    @Override
    public String getName() throws NotFound {
        ResultSet data = dbCategoryGetter();
        try{
            if(data.next()){
                return data.getString("Category_Name");
            }else{
                throw new NotFound();
            }
        } catch (SQLException ex){
            errorHandler.report("Category Class", ex.getMessage());
            errorHandler.terminate();
            return "";
        }
    }

    @Override
    public boolean changeName(String newName) throws NotFound {
        
        //update the entry
        String sqlQuery = "UPDATE " + "Categories" + " SET " + " Category_Name " + " = ?";
        statementSQL = MysqlHandler.getInstance().getPreparedStatementWithKeys(sqlQuery);
        
        try {
        
            statementSQL.setString(1, newName);
            statementSQL.executeUpdate();

            ResultSet key = statementSQL.getGeneratedKeys();
	    if(!key.next()){
                throw new NotFound();
            }
	    
        } catch (SQLException ex) {
    
            errorHandler.report("Category Class", ex.getMessage());
            
            // Null the selectorStatemen
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
