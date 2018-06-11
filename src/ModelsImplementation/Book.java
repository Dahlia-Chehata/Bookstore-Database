package ModelsImplementation;

import HelperClasses.NotFound;
import HelperClasses.ErrorHandler;
import ModelsInterfaces.IAuthor;
import ModelsInterfaces.IBook;
import ModelsInterfaces.ICategory;
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
public class Book implements IBook{

    private final String bookISBN;
    private final int bookId;
    private PreparedStatement statementSQL;
    private final ErrorHandler errorHandler;
    
    public Book(String bookISBN) throws NotFound{
        //set Data
        this.bookISBN = bookISBN;
        errorHandler = new ErrorHandler();
        statementSQL = null;
        
        //vlidate the ISBN
        this.bookId = getID();
    }
    
    private ResultSet dbBookGetter(){
        
        String sqlQuery = "SELECT * FROM `Books_ISBNs` INNER JOIN `books` " + 
                " ON `Books_ISBNs`.`Book_id` = `books`.`Book_id` " +
                " WHERE `Books_ISBNs`.`ISBN` = ?";
        
        statementSQL = MysqlHandler.getInstance().getPreparedStatement(sqlQuery);
        ResultSet data = null;
        
        try {
            statementSQL.setString(1,bookISBN);
            
            statementSQL.execute();
            data = statementSQL.getResultSet();
            
        } catch (SQLException ex) {
            errorHandler.report("Book Class", ex.getMessage());
            errorHandler.terminate();
        }
        
        return data;
    }
    
    @Override
    public int getID() throws NotFound{
        
        ResultSet data = dbBookGetter();
        int bookId = -1;
        
        try{
            if(data.next()){
                bookId = data.getInt("Book_id");
            }else{
                throw new NotFound();
            }
        } catch (SQLException ex){
            errorHandler.report("Book Class", ex.getMessage());
            errorHandler.terminate();
        } finally {
            //Close && nullify the statement
            MysqlHandler.getInstance().closePreparedStatement(statementSQL);
            statementSQL = null;
        }
        
        return bookId;
    }

    @Override
    public String getISBN() throws NotFound{
        
        ResultSet data = dbBookGetter();
        String ISBN = "";
        
        try{
            if(data.next()){
                ISBN = data.getString("ISBN");
            }else{
                throw new NotFound();
            }
        } catch (SQLException ex){
            errorHandler.report("Book Class", ex.getMessage());
            errorHandler.terminate();
        } finally {
            //Close && nullify the statement
            MysqlHandler.getInstance().closePreparedStatement(statementSQL);
            statementSQL = null;
        }
        
        return ISBN;
    }

    @Override
    public String getTitle() throws NotFound{
        
        ResultSet data = dbBookGetter();
        String title = "";
        
        try{
            if(data.next()){
                title = data.getString("Title");
            }else{
                throw new NotFound();
            }
        } catch (SQLException ex){
            errorHandler.report("Book Class", ex.getMessage());
            errorHandler.terminate();
        } finally {
            //Close && nullify the statement
            MysqlHandler.getInstance().closePreparedStatement(statementSQL);
            statementSQL = null;
        }
        
        return title;
    }

    @Override
    public int getPublicationYear() throws NotFound{
        
        ResultSet data = dbBookGetter();
        int year = -1;
        
        try{
            if(data.next()){
                year = data.getInt("Publication_Year");
            }else{
                throw new NotFound();
            }
        } catch (SQLException ex){
            errorHandler.report("Book Class", ex.getMessage());
            errorHandler.terminate();
        } finally {
            //Close && nullify the statement
            MysqlHandler.getInstance().closePreparedStatement(statementSQL);
            statementSQL = null;
        }
        
        return year;
    }

    @Override
    public double getPrice() throws NotFound{
        
        ResultSet data = dbBookGetter();
        double price = -1;
        
        try{
            if(data.next()){
                price = data.getInt("Selling_price");
            }else{
                throw new NotFound();
            }
        } catch (SQLException ex){
            errorHandler.report("Book Class", ex.getMessage());
            errorHandler.terminate();
        } finally {
            //Close && nullify the statement
            MysqlHandler.getInstance().closePreparedStatement(statementSQL);
            statementSQL = null;
        }
        
        return price;
    }

    @Override
    public int getAvailableQuantity() throws NotFound{
        
        ResultSet data = dbBookGetter();
        int quantity = -1;
        
        try{
            if(data.next()){
                quantity = data.getInt("Available_Quantity");
            }else{
                throw new NotFound();
            }
        } catch (SQLException ex){
            errorHandler.report("Book Class", ex.getMessage());
            errorHandler.terminate();
        } finally {
            //Close && nullify the statement
            MysqlHandler.getInstance().closePreparedStatement(statementSQL);
            statementSQL = null;
        }
        
        return quantity;
    }

    @Override
    public int getThreshold() throws NotFound{
        
        ResultSet data = dbBookGetter();
        int threshold = -1;
        
        try{
            if(data.next()){
                threshold = data.getInt("Threshold");
            }else{
                throw new NotFound();
            }
        } catch (SQLException ex){
            errorHandler.report("Book Class", ex.getMessage());
            errorHandler.terminate();
        } finally {
            //Close && nullify the statement
            MysqlHandler.getInstance().closePreparedStatement(statementSQL);
            statementSQL = null;
        }
        
        return threshold;
    }

    @Override
    public ICategory getCategory() throws NotFound{
        
        ResultSet data = dbBookGetter();
        ICategory category = null;
        
        try{
            if(data.next()){
                //This can also throw NotFound
                try{
                    category = new Category(data.getInt("category_id"));
                } catch(NotFound ex){
                    return getCategory();
                }
            }else{
                throw new NotFound();
            }
        } catch (SQLException ex){
            errorHandler.report("Book Class", ex.getMessage());
            errorHandler.terminate();
        } finally {
            //Close && nullify the statement
            if(statementSQL != null){
                MysqlHandler.getInstance().closePreparedStatement(statementSQL);
                statementSQL = null;
            }
        }
        
        return category;
    }

    @Override
    public ArrayList<IAuthor> getAuthors() throws NotFound{
        
        String sqlQuery = "SELECT Author_Name FROM `Authors` WHERE `Authors`.`Book_id` = ?";
        
        //data to be returned
        ArrayList<IAuthor> toBeReturned = new ArrayList<>();
        AuthorManager authorGetter = new AuthorManager();
        
        statementSQL = MysqlHandler.getInstance().getPreparedStatement(sqlQuery);
        ResultSet dataHandler;
        
        try {
            
            statementSQL.setInt(1, bookId);
            statementSQL.execute();
            dataHandler = statementSQL.getResultSet();

            while(dataHandler.next()){
                IAuthor author = authorGetter.getOrAddAuthor(dataHandler.getString("Author_Name"));
                
                if(author != null){
                    toBeReturned.add(author);
                }
            }
            
        } catch (SQLException ex) {
            errorHandler.report("Book Class", ex.getMessage());
            errorHandler.terminate();
        }
        
        //Null the statement
        MysqlHandler.getInstance().closePreparedStatement(statementSQL);
        statementSQL = null;
        
        return toBeReturned;
    }

    @Override
    public IPublisher getPublisher() throws NotFound{
        
        ResultSet data = dbBookGetter();
        IPublisher publisher = null;
        
        try{
            if(data.next()){
                //This can also throw NotFound
                try{
                    publisher = new Publisher(data.getInt("Publisher_id"));
                } catch(NotFound ex){
                    return getPublisher();
                }
            }else{
                throw new NotFound();
            }
        } catch (SQLException ex){
            errorHandler.report("Book Class", ex.getMessage());
            errorHandler.terminate();
        } finally {
            //Close && nullify the statement
            if(statementSQL != null){
                MysqlHandler.getInstance().closePreparedStatement(statementSQL);
                statementSQL = null;
            }
        }
        
        return publisher;
    }
    
    private boolean dbBookUpdater(String dbName, String colName, Object newData) throws NotFound{

        //update the entry
        String sqlQuery;
        if(dbName.equals("books")){
            sqlQuery = "UPDATE " + dbName + " SET " + colName + " = ? WHERE Book_id = ?";
        }else{
            sqlQuery = "UPDATE " + dbName + " SET " + colName + " = ? WHERE ISBN = ?";
        }
        System.out.println(sqlQuery);
        statementSQL = MysqlHandler.getInstance().getPreparedStatementWithKeys(sqlQuery);
        
        try {
            
            //set the first parameter
            if(newData instanceof Double){
                statementSQL.setDouble(1, ((Double) newData).doubleValue());
            }else if(newData instanceof Integer){
                statementSQL.setInt(1, ((Integer) newData).intValue());
            }else if (newData instanceof String){
                statementSQL.setString(1, ((String) newData));
            }else {
                throw new RuntimeException("Error in the bookManager Update function");
            }
            
            if(dbName.equals("books")){
                statementSQL.setInt(2, bookId);
            }else{
                statementSQL.setString(2, bookISBN);
            }
            
            int rows = statementSQL.executeUpdate();

	    if(rows == 0){
                throw new NotFound();
            }
	    
        } catch (SQLException ex) {
            errorHandler.report("bookUpdater Class", ex.getMessage());
            return false;
        } finally {
            //Null the selectorStatemen
            MysqlHandler.getInstance().closePreparedStatement(statementSQL);
            statementSQL = null;  
        }

        return true;
    }
    
    @Override
    public boolean changeISBN(String newISBN) throws NotFound{
        
        if(newISBN == null || newISBN.length() == 0 || newISBN.length() > 40){
            return false;
        }
        
        return dbBookUpdater("Books_ISBNs","ISBN",newISBN);
    }

    @Override
    public boolean changeTitle(String newTitle) throws NotFound{
        
        if(newTitle == null || newTitle.length() == 0 || newTitle.length() > 40){
            return false;
        }
        
        return dbBookUpdater("books","Title",newTitle);
    }

    @Override
    public boolean changePublicationYear(int newPublicationYear) throws NotFound{
        
        if(newPublicationYear < 0){
            return false;
        }
        
        return dbBookUpdater("Books_ISBNs","Publication_Year",newPublicationYear);
    }

    @Override
    public boolean changePrice(double newPrice) throws NotFound{
        
        if(newPrice < 0){
            return false;
        }
        
        return dbBookUpdater("Books_ISBNs","Selling_price",newPrice);
    }

    @Override
    public boolean setThreshold(int newThreshold) throws NotFound{
        if(newThreshold < 0){
            return false;
        }
        
        return dbBookUpdater("Books_ISBNs","Threshold",newThreshold);
    }

    @Override
    public boolean changeCategory(ICategory newCategory) throws NotFound{
        
        if(newCategory == null){
            return false;
        }
        
        int categoryId;
        try{
            categoryId = newCategory.getId();
        }catch (NotFound ex){
            return false;
        }
        
        return dbBookUpdater("books","category_id",categoryId);
    }

    @Override
    public boolean changePublisher(IPublisher newPublisher) throws NotFound{
        
        if(newPublisher == null){
            return false;
        }
        
        int publisherId;
        try{
            publisherId = newPublisher.getId();
        }catch (NotFound ex){
            return false;
        }
        
        return dbBookUpdater("Books_ISBNs","Publisher_id",publisherId);
    }
    @Override
    public boolean changeAvailableQuantity(int quantity) throws NotFound{
        
      
    	  if(quantity < 0){
              return false;
          }
          
          return dbBookUpdater("Books_ISBNs","Available_Quantity",quantity);
    }

    @Override
    public boolean addAuthor(IAuthor newAuthour) throws NotFound{
        return newAuthour.addBook(this);
    }

    @Override
    public boolean removeAuthour(IAuthor currAuthour) throws NotFound{
        return currAuthour.removeBook(this);
    }
    
    @Override
    public void finalize(){
        
        if(statementSQL != null){
            MysqlHandler.getInstance().closePreparedStatement(statementSQL);
        }

    }
    
}
