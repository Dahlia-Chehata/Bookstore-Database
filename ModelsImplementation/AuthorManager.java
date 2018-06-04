package ModelsImplementation;

import HelperClasses.NotFound;
import HelperClasses.ErrorHandler;
import ModelsInterfaces.IAuthor;
import ModelsInterfaces.IAuthorManager;
import Mysql.MysqlHandler;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 *
 * @author Fares
 */
public class AuthorManager implements IAuthorManager{

    private final ErrorHandler errorHandler;
    private PreparedStatement selectorStatement;
    
    AuthorManager(){
        //set the data
        errorHandler = new ErrorHandler();
        selectorStatement = null;
    }
    
    @Override
    public IAuthor getOrAddAuthor(String authorName) {
        
        try {
            return new Author(authorName);
        } catch (NotFound ex) {
            errorHandler.report("Author Manager Class",ex.getMessage());
            return null;
        }
        
    }

    @Override
    public ArrayList<IAuthor> getAllAuthors() {
        
        ArrayList<IAuthor> authorsList = new ArrayList<>();
        
        String sqlQuery = "SELECT DISTINCT `Author_Name` FROM `Authors`";
        selectorStatement = MysqlHandler.getInstance().getPreparedStatement(sqlQuery);
        
        try {
        
            selectorStatement.execute();
            ResultSet authors = selectorStatement.getResultSet();

            while(authors.next()){

                //Retrieve the data
                String authorName  = authors.getString("Author_Name");
                
                //add the author to the authorsList
                try{
                    authorsList.add(new Author(authorName));
                } catch(NotFound ex){
                    //This means the author was deleted.
                    //So no need to add it.
                }
            }
            
        } catch (SQLException ex) {
            errorHandler.report("Author Class", ex.getMessage());
            errorHandler.terminate();
        }
        
        //Close && nullify the selectorStatement
        MysqlHandler.getInstance().closePreparedStatement(selectorStatement);
        selectorStatement = null;
    
        return authorsList;
    }
    
    @Override
    public void finalize(){
        if(selectorStatement != null){
            MysqlHandler.getInstance().closePreparedStatement(selectorStatement);
        }
        
    }
}
