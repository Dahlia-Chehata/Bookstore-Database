package ModelsImplementation;

import HelperClasses.NotFound;

import HelperClasses.ErrorHandler;
import ModelsInterfaces.IAuthor;
import ModelsInterfaces.IBook;
import Mysql.MysqlHandler;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 *
 * @author Fares
 */
public class Author implements IAuthor{

    private final String authorName;
    private PreparedStatement statement;
    private final ErrorHandler errorHandler;
        
    public Author(String autorName) throws NotFound{
        //set the data
        this.authorName = autorName;
        statement = null;
        errorHandler = new ErrorHandler();
    }
    
    @Override
    public String getName(){
        return authorName;
    }

    @Override
    public ArrayList<IBook> getWrittenBooks(){
        
        ArrayList<IBook> authorBooks = new ArrayList<>();
        BookManager bookGetter = new BookManager();
        
        String sqlQuery = "SELECT `Book_id` FROM `Authors` WHERE `Author_Name` = ?";
        statement = MysqlHandler.getInstance().getPreparedStatement(sqlQuery);
        
        try {
        
            statement.setString(1, this.authorName);
            statement.execute();
            ResultSet books = statement.getResultSet();

            while(books.next()){

                //Retrieve the data
                int bookId  = books.getInt("Book_id");
                
                //add the book to the authorBooks
                authorBooks.addAll(bookGetter.getBooks().getBooksById(bookId).get());
            }
            
        } catch (SQLException ex) {
            errorHandler.report("Author Class", ex.getMessage());
            errorHandler.terminate();
        }
        
        //Close && nullify the statement
        MysqlHandler.getInstance().closePreparedStatement(statement);
        statement = null;
        
        return authorBooks;
    }

    @Override
    public boolean removeBook(IBook bookToBeRemoved){
        
        //get the book id
        int bookId;
        //if book not found that mean already the book is deleted
        try{
            bookId = bookToBeRemoved.getID();
        } catch(NotFound ex){
            return true;
        }
        
        //remove the entry
        String sqlQuery = "DELETE FROM `Authors` WHERE `Author_Name` = ? AND `Book_id` = ?";
        statement = MysqlHandler.getInstance().getPreparedStatement(sqlQuery);
        
        try {
        
            statement.setString(1, this.authorName);
            statement.setInt(2, bookId);
            statement.executeUpdate();

        } catch (SQLException ex) {
            errorHandler.report("Author Class", ex.getMessage());
            errorHandler.terminate();
        }
        
        //Close && nullify the statement
        MysqlHandler.getInstance().closePreparedStatement(statement);
        statement = null;
        
        return true;
    }

    @Override
    public void finalize(){
        
        if(statement != null){
            MysqlHandler.getInstance().closePreparedStatement(statement);
        }
        
    }

    @Override
    public boolean addBook(IBook bookToBeAdded) {
        
        //add the entry
        String sqlQuery = "INSERT INTO `Authors` (`Book_id`,`Author_Name`) VALUES (?,?)";
        statement = MysqlHandler.getInstance().getPreparedStatement(sqlQuery);
        
        try {
            
            int bookId;
            try{
                bookId = bookToBeAdded.getID();
            //If the book not existed then return false;
            }catch (NotFound ex){
                return false;
            }
            
            statement.setInt(1, bookId);
            statement.setString(2, this.authorName);
            statement.executeUpdate();

        } catch (SQLException ex) {
            errorHandler.report("Author Class", ex.getMessage());
            return false;
        } finally {
            //Close && nullify the statement
            MysqlHandler.getInstance().closePreparedStatement(statement);
            statement = null;
        }
        
        return true;
    }
}
