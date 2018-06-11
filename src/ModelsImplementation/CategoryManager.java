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
    private final ErrorHandler errorHandler;
    
    public CategoryManager(){
        errorHandler = new ErrorHandler();
    }
    
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
        } finally {
            //Null the selectorStatemen
            MysqlHandler.getInstance().closePreparedStatement(statementSQL);
            statementSQL = null; 
        }

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
        } finally {
            //Null the statement
            MysqlHandler.getInstance().closePreparedStatement(statementSQL);
            statementSQL = null;
        }

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
        
        try {
            
            statementSQL = MysqlHandler.getInstance().getPreparedStatement(sqlQuery);
            statementSQL.setInt(1, categoryId);
            statementSQL.executeUpdate();
	    
        } catch (SQLException ex) {
            errorHandler.report("Category Manager Class", ex.getMessage());
            return false;
        } finally {
            //Null the statementSQL
            MysqlHandler.getInstance().closePreparedStatement(statementSQL);
            statementSQL = null;
        }

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
            
            categoryId = key.getInt(1);
            
        } catch (SQLException ex) {
            errorHandler.report("Category Manager Class", ex.getMessage());
            return null;
        } finally {
            //Null the statementSQL
            MysqlHandler.getInstance().closePreparedStatement(statementSQL);
            statementSQL = null;
        }

        return this.getCategoryById(categoryId);
    }
    
    public static void main(String[] args) throws NotFound{
        CategoryManager mng = new CategoryManager();
        //mng.addStatus("normal");
        
        System.out.println(mng.addCategory("Science"));
        System.out.println(mng.getAllCategories().size());
        System.out.println(mng.getCategoryById(1).getName());
        System.out.println(mng.getCategoryByName("Science"));
    
        ICategory cat = mng.getCategoryById(1);
        System.out.println(cat.getId());
        System.out.println(cat.getName());
        System.out.println(cat.changeName("History"));
        System.out.println(cat);
        System.out.println(cat);
                Mysql.MysqlHandler.getInstance().state();

    }
    
}
