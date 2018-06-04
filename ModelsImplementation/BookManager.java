package ModelsImplementation;

import HelperClasses.NotFound;
import HelperClasses.ErrorHandler;
import ModelsInterfaces.IAuthor;
import ModelsInterfaces.IBook;
import ModelsInterfaces.IBookManager;
import ModelsInterfaces.IBooksGetter;
import ModelsInterfaces.ICategory;
import ModelsInterfaces.IPublisher;
import Mysql.MysqlHandler;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Fares
 */
public class BookManager implements IBookManager{

    private final ErrorHandler errorHandler;
    private PreparedStatement statement;
    
    BookManager(){
        errorHandler = new ErrorHandler();
    }
    
    @Override
    public IBooksGetter getBooks() {
        return new BooksGetter();
    }

    @Override
    public IBook addBook(String title, ICategory category, String ISBN, IPublisher publisher, int publicationYear, double price, int availableQuantity, int threshold, ArrayList<IAuthor> authors) {
        
        int categoryId;
        int publisherId;
        
        //try to get Category and Publisher IDs
        try{
            categoryId = category.getId();
            publisherId = publisher.getId();
        }catch (NotFound ex){
            //if either of them not existing, then return null.
            return null;
        }
        
        //Adding the book require transaction to make sure everything working correctly.
        Connection conn = null;
        try {
            conn = MysqlHandler.getInstance().getConnection();
            conn.setAutoCommit(false);
        } catch (SQLException ex) {
            errorHandler.report("Book Manager Class", ex.getMessage());
            errorHandler.terminate();
        }
        
        //add the entry int books relation
        try {
            String sqlQuery = "INSERT INTO `books` (`Title`,`category_id`) VALUES (?,?);";
            statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, title);
            statement.setInt(2, categoryId);
            statement.executeUpdate();
        } catch (SQLException ex) {
            errorHandler.report("Book Manager Class", ex.getMessage());
            //close the opened connection
            MysqlHandler.getInstance().closeConnection(conn);
            return null;
        }
        
        //Set the id of the book
        try {
            String sqlQuery = "SET @KEY = ( select LAST_INSERT_ID() ) ;";
            statement = conn.prepareStatement(sqlQuery);
            statement.execute();
        } catch (SQLException ex) {
            errorHandler.report("Book Manager Class", ex.getMessage());
            //close the opened connection
            MysqlHandler.getInstance().closeConnection(conn);
            return null;
        }
        
        //add the entry into Books_ISBNs relation
        try {
            String sqlQuery = "INSERT INTO `Books_ISBNs` " +
            " (`ISBN`,`Book_id`,`Publisher_id`,`Publication_Year`,`Selling_price`,`Available_Quantity`,`Threshold`) VALUES (?,@key,?,?,?,?,?);";
            
            statement = conn.prepareStatement(sqlQuery);
            
            statement.setString(1, ISBN);
            statement.setInt(2, publisherId);
            statement.setInt(3, publicationYear);
            statement.setDouble(4, price);
            statement.setInt(5, availableQuantity);
            statement.setInt(6, threshold);

            statement.executeUpdate();
        } catch (SQLException ex) {
            errorHandler.report("Book Manager Class", ex.getMessage());
            //close the opened connection
            MysqlHandler.getInstance().closeConnection(conn);
            return null;
        }

        //add the authors.
        try {
            String sqlQuery = "INSERT INTO `Authors` " +
            "(`Book_id`,`Author_Name`) VALUES (@key,?);";
            
            statement = conn.prepareStatement(sqlQuery);
            
            for(int i=0;i<authors.size();i++){
                statement.setString(1, authors.get(i).getName());
                statement.executeUpdate();
            }
            
        } catch (SQLException ex) {
            errorHandler.report("Book Manager Class", ex.getMessage());
            //close the opened connection
            MysqlHandler.getInstance().closeConnection(conn);
            return null;
        }
        
        
        //try to add the book
        try {            
            conn.commit();
        } catch (SQLException ex) {
            errorHandler.report("Book Manager Class", ex.getMessage());
            //close the opened connection
            MysqlHandler.getInstance().closeConnection(conn);
            return null;
        }
        
        //close the connection
        MysqlHandler.getInstance().closeConnection(conn);
        
        Book toReturn;
        try {
            toReturn = new Book(ISBN);
        } catch (NotFound ex) {
            return null;
        }
        
        return toReturn;
    }

    @Override
    public ArrayList<IBook> getTop(int numOfBooks, String startingDate, String endingDate) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
