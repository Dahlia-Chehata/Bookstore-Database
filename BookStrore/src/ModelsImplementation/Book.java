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

    private String bookISBN;
    private PreparedStatement statementSQL;
    private ErrorHandler errorHandler;

    public Book(String bookISBN) throws NotFound{
        //set Data
        this.bookISBN = bookISBN;
        errorHandler = new ErrorHandler();
        statementSQL = null;

        //vlidate the ISBN
        getISBN();
    }

    private ResultSet dbBookGetter() throws NotFound{

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

        //Null the selectorStatemen
        MysqlHandler.getInstance().closePreparedStatement(statementSQL);
        statementSQL = null;

        return data;
    }

    @Override
    public int getID() throws NotFound{
        ResultSet data = dbBookGetter();
        try{
            if(data.next()){
                return data.getInt("Book_id");
            }else{
                throw new NotFound();
            }
        } catch (SQLException ex){
            errorHandler.report("Book Class", ex.getMessage());
            errorHandler.terminate();
            return -1;
        }
    }

    @Override
    public String getISBN() throws NotFound{
        ResultSet data = dbBookGetter();
        try{
            if(data.next()){
                return data.getString("ISBN");
            }else{
                throw new NotFound();
            }
        } catch (SQLException ex){
            errorHandler.report("Book Class", ex.getMessage());
            errorHandler.terminate();
            return "";
        }
    }

    @Override
    public String getTitle() throws NotFound{
        ResultSet data = dbBookGetter();
        try{
            if(data.next()){
                return data.getString("Title");
            }else{
                throw new NotFound();
            }
        } catch (SQLException ex){
            errorHandler.report("Book Class", ex.getMessage());
            errorHandler.terminate();
            return "";
        }
    }

    @Override
    public int getPublicationYear() throws NotFound{
        ResultSet data = dbBookGetter();
        try{
            if(data.next()){
                return data.getInt("Publication_Year");
            }else{
                throw new NotFound();
            }
        } catch (SQLException ex){
            errorHandler.report("Book Class", ex.getMessage());
            errorHandler.terminate();
            return -1;
        }
    }

    @Override
    public double getPrice() throws NotFound{
        ResultSet data = dbBookGetter();
        try{
            if(data.next()){
                return data.getInt("Selling_price");
            }else{
                throw new NotFound();
            }
        } catch (SQLException ex){
            errorHandler.report("Book Class", ex.getMessage());
            errorHandler.terminate();
            return -1;
        }
    }

    @Override
    public int getAvailableQuantity() throws NotFound{
        ResultSet data = dbBookGetter();
        try{
            if(data.next()){
                return data.getInt("Available_Quantity");
            }else{
                throw new NotFound();
            }
        } catch (SQLException ex){
            errorHandler.report("Book Class", ex.getMessage());
            errorHandler.terminate();
            return -1;
        }
    }

    @Override
    public int getThreshold() throws NotFound{
        ResultSet data = dbBookGetter();
        try{
            if(data.next()){
                return data.getInt("Threshold");
            }else{
                throw new NotFound();
            }
        } catch (SQLException ex){
            errorHandler.report("Book Class", ex.getMessage());
            errorHandler.terminate();
            return -1;
        }
    }

    @Override
    public int getQuantityToBeOrdered() throws NotFound{
        ResultSet data = dbBookGetter();
        try{
            if(data.next()){
                return data.getInt("QuantityToBeOrdered");
            }else{
                throw new NotFound();
            }
        } catch (SQLException ex){
            errorHandler.report("Book Class", ex.getMessage());
            errorHandler.terminate();
            return -1;
        }
    }

    @Override
    public ICategory getCategory() throws NotFound{
        ResultSet data = dbBookGetter();
        try{
            if(data.next()){
                //This can also throw NotFound
                try{
                    return new Category(data.getInt("category_id"));
                } catch(NotFound ex){
                    return getCategory();
                }
            }else{
                throw new NotFound();
            }
        } catch (SQLException ex){
            errorHandler.report("Book Class", ex.getMessage());
            errorHandler.terminate();
            return null;
        }
    }

    @Override
    public ArrayList<IAuthor> getAuthors() throws NotFound{

        String sqlQuery = "SELECT `Author_Name` FROM `Authors` WHERE `Book_id` = ?";

        //data to be returned
        ArrayList<IAuthor> toBeReturned = new ArrayList<>();
        AuthorManager authorGetter = new AuthorManager();

        statementSQL = MysqlHandler.getInstance().getPreparedStatement(sqlQuery);
        ResultSet data;

        try {

            statementSQL.setInt(1, getID());
            statementSQL.execute();
            data = statementSQL.getResultSet();

            while(data.next()){
                IAuthor author = authorGetter.getOrAddAuthor(data.getString("Author_Name"));

                if(author != null){
                    toBeReturned.add(author);
                }
            }

        } catch (SQLException ex) {
            errorHandler.report("Book Class", ex.getMessage());
            errorHandler.terminate();
        }

        //Null the selectorStatemen
        MysqlHandler.getInstance().closePreparedStatement(statementSQL);
        statementSQL = null;

        return toBeReturned;
    }

    @Override
    public IPublisher getPublisher() throws NotFound{

    	return null;
//        ResultSet data = dbBookGetter();
//        try{
//            if(data.next()){
//                //This can also throw NotFound
//                try{
//                    return Publisher(data.getInt("Publisher_id"));
//                } catch(NotFound ex){
//                    return getPublisher();
//                }
//            }else{
//                throw new NotFound();
//            }
//        } catch (SQLException ex){
//            errorHandler.report("Book Class", ex.getMessage());
//            errorHandler.terminate();
//            return null;
//        }

    }

    private boolean dbBookUpdater(String dbName, String colName, String newData) throws NotFound{

        //update the entry
        String sqlQuery = "UPDATE " + dbName + " SET " + colName + " = ?";
        statementSQL = MysqlHandler.getInstance().getPreparedStatementWithKeys(sqlQuery);

        try {

            statementSQL.setString(1, newData);
            statementSQL.executeUpdate();

            ResultSet key = statementSQL.getGeneratedKeys();
	    if(!key.next()){
                throw new NotFound();
            }

        } catch (SQLException ex) {
            errorHandler.report("Author Class", ex.getMessage());
            return false;
        }

        //Null the statementSQL
        MysqlHandler.getInstance().closePreparedStatement(statementSQL);
        statementSQL = null;
        return true;
    }

    private boolean dbBookUpdater(String dbName, String colName, int newData) throws NotFound{

        //update the entry
        String sqlQuery = "UPDATE " + dbName + " SET " + colName + " = ?";
        statementSQL = MysqlHandler.getInstance().getPreparedStatementWithKeys(sqlQuery);

        try {

            statementSQL.setInt(1, newData);
            statementSQL.executeUpdate();

            ResultSet key = statementSQL.getGeneratedKeys();
	    if(!key.next()){
                throw new NotFound();
            }

        } catch (SQLException ex) {
            errorHandler.report("Author Class", ex.getMessage());
            return false;
        }

        //Null the selectorStatemen
        MysqlHandler.getInstance().closePreparedStatement(statementSQL);
        statementSQL = null;
        return true;
    }

    private boolean dbBookUpdater(String dbName, String colName, double newData) throws NotFound{

        //update the entry
        String sqlQuery = "UPDATE " + dbName + " SET " + colName + " = ?";
        statementSQL = MysqlHandler.getInstance().getPreparedStatementWithKeys(sqlQuery);

        try {

            statementSQL.setDouble(1, newData);
            statementSQL.executeUpdate();

            ResultSet key = statementSQL.getGeneratedKeys();
	    if(!key.next()){
                throw new NotFound();
            }

        } catch (SQLException ex) {
            errorHandler.report("Author Class", ex.getMessage());
            return false;
        }

        //Null the selectorStatemen
        MysqlHandler.getInstance().closePreparedStatement(statementSQL);
        statementSQL = null;
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
    public boolean setQuantityToBeOrdered(int newQuantityToBeOrdered) throws NotFound{
        if(newQuantityToBeOrdered < 0){
            return false;
        }

        return dbBookUpdater("Books_ISBNs","QuantityToBeOrdered",newQuantityToBeOrdered);
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
