package ModelsImplementation;

import HelperClasses.ErrorHandler;
import HelperClasses.NotFound;
import ModelsInterfaces.IAuthor;
import ModelsInterfaces.IBook;
import ModelsInterfaces.IBooksGetter;
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
public class BooksGetter implements IBooksGetter, Cloneable{

    //column names
    private final ArrayList<String> columnsNames;
    
    //Argument given in the conditions
    private final ArrayList<Integer> intArguments;
    private final ArrayList<Double> doubleArguments;
    private final ArrayList<String> stringArguments;
    
    //to search
    private final ArrayList<String> match;
    private final ArrayList<String> against;
    
    private boolean invalid;
    
    //Mysql handler
    private PreparedStatement statementSQL;
    private final ErrorHandler errorHandler;
    
    @Override
    public BooksGetter clone() throws CloneNotSupportedException{
        return (BooksGetter) super.clone();
    }
    
    public BooksGetter(){
        errorHandler = new ErrorHandler();
        columnsNames = new ArrayList<>();
        intArguments = new ArrayList<>();
        doubleArguments = new ArrayList<>();
        stringArguments = new ArrayList<>();
        match = new ArrayList<>();
        against = new ArrayList<>();
        invalid = false;
    }

    void reset(){
        columnsNames.clear();
        intArguments.clear();
        doubleArguments.clear();
        stringArguments.clear();
        invalid = false;
    }
    
    @Override
    public IBooksGetter getBooksByTitle(String bookTitle) {
        columnsNames.add("books.Title");
        stringArguments.add(bookTitle);
        return this;
    }
    
    @Override
    public IBooksGetter searchBooksByTitle(String bookTitle) {
        match.add("books.Title");
        against.add(bookTitle);
        return this;
    }

    @Override
    public IBooksGetter getBooksById(int bookId) {
        columnsNames.add("books.Book_id");
        intArguments.add(bookId);
        return this;
    }

    @Override
    public IBooksGetter getBooksByISBN(String bookISBN) {
        columnsNames.add("Books_ISBNs.ISBN");
        stringArguments.add(bookISBN);
        return this;
    }

    @Override
    public IBooksGetter getBooksByAuthor(IAuthor author) {
        columnsNames.add("Authors.Author_Name");
        stringArguments.add(author.getName());
        return this;
    }

    @Override
    public IBooksGetter getBooksByCategory(ICategory category) {
        columnsNames.add("books.category_id");
        try{
            intArguments.add(category.getId());
        // If the category not found then  invalidate the query.
        } catch (NotFound ex) {
            invalid = true;
        }
        return this;
    }

    @Override
    public IBooksGetter getBooksByPublicationYear(int publicationYearStart, int publicationYearEnd) {
        columnsNames.add("Books_ISBNs.Publication_Year");
        intArguments.add(publicationYearStart);
        intArguments.add(publicationYearEnd);
        return this;
    }

    @Override
    public IBooksGetter getBooksBySellingPrice(double startingPrice, double endingPrice) {
        columnsNames.add("Books_ISBNs.Selling_price");
        doubleArguments.add(startingPrice);
        doubleArguments.add(endingPrice);
        return this;
    }
    
    public IBooksGetter getBooksByPublisher(IPublisher publisher){
        columnsNames.add("Books_ISBNs.Publisher_id");
        try{
            intArguments.add(publisher.getId());
        // If the publisher not found then invalidate the query.
        } catch (NotFound ex) {
            invalid = true;
        }
        return this;
    }

    
    String getCondition(){
        
        String condition = new String();
        
        int totalConditions = 0;
        
        for(int i=0;i<columnsNames.size();i++){
            
            if(totalConditions != 0){
                condition += " AND ";
            }
            totalConditions++;
            
            if(columnsNames.get(i).equals("Books_ISBNs.Publication_Year") || 
               columnsNames.get(i).equals("Books_ISBNs.Selling_price")    ||
               columnsNames.get(i).equals("Books_ISBNs.Threshold")    ||
               columnsNames.get(i).equals("Books_ISBNs.Available_Quantity")){
                condition += columnsNames.get(i) + " >= ? ";
                condition += " AND ";
                condition += columnsNames.get(i) + " <= ? ";
            }
            else{
                condition += columnsNames.get(i) + " = ? ";
            }
        }
        
        for(int i = 0; i < match.size(); i++){
            
            if(totalConditions != 0){
                condition += " AND ";
            }
            totalConditions++;
            
            condition += " MATCH ( " +  match.get(i) + " ) AGAINST (? IN BOOLEAN MODE) ";
        }
        
        if(condition.equals("")){
            condition = "true";
        }
        
        return condition;
    }
    
    void binding() throws SQLException{

        int intPointer = 0;
        int stringPointer = 0;
        int doublePointer = 0;
        int argPointer = 1;
        
        for(int i=0;i<columnsNames.size();i++){

            switch (columnsNames.get(i)) {
                case "Books_ISBNs.Publication_Year":
                    statementSQL.setInt(argPointer++,intArguments.get(intPointer++));
                    statementSQL.setInt(argPointer++,intArguments.get(intPointer++));
                    break;
                case "Books_ISBNs.Threshold":
                    statementSQL.setInt(argPointer++,intArguments.get(intPointer++));
                    statementSQL.setInt(argPointer++,intArguments.get(intPointer++));
                    break;
                case "Books_ISBNs.Available_Quantity":
                    statementSQL.setInt(argPointer++,intArguments.get(intPointer++));
                    statementSQL.setInt(argPointer++,intArguments.get(intPointer++));
                    break;
                case "Books_ISBNs.Selling_price":
                    statementSQL.setDouble(argPointer++,doubleArguments.get(doublePointer++));
                    statementSQL.setDouble(argPointer++,doubleArguments.get(doublePointer++));
                    break;
                case "books.Title":
                    statementSQL.setString(argPointer++,stringArguments.get(stringPointer++));
                    break;
                case "books.category_id":
                    statementSQL.setInt(argPointer++,intArguments.get(intPointer++));
                    break;
                case "Authors.Author_Name":
                    statementSQL.setString(argPointer++,stringArguments.get(stringPointer++));
                    break;
                case "Books_ISBNs.ISBN":
                    statementSQL.setString(argPointer++,stringArguments.get(stringPointer++));
                    break;
                case "books.Book_id":
                    statementSQL.setInt(argPointer++,intArguments.get(intPointer++));
                    break;
                case "Books_ISBNs.Publisher_id":
                    statementSQL.setInt(argPointer++,intArguments.get(intPointer++));
                    break;
                default:
                    break;
            }
            
        }
        
        for(int i = 0; i < against.size() ; i++){
            statementSQL.setString(argPointer++, against.get(i));
        }
        
    }

    @Override
    public int booksCount() {
        int val =  get(Integer.MAX_VALUE,0).size();
        reset();
        return val;
    }

        @Override
    public ArrayList<IBook> get() {
        
        ArrayList<IBook> booksList = new ArrayList<>();
        
        String sqlQuery = "SELECT DISTINCT `Books_ISBNs`.`ISBN` FROM `Books_ISBNs` INNER JOIN `books` " + 
                " ON `Books_ISBNs`.`Book_id` = `books`.`Book_id` " +
                " INNER JOIN `Categories` ON `Categories`.`category_id` = `books`.`category_id` "+
                " LEFT JOIN `Authors` ON `Authors`.`Book_id` = `books`.`Book_id` " +
                " WHERE " + getCondition();
        System.out.println(sqlQuery);
        statementSQL = MysqlHandler.getInstance().getPreparedStatement(sqlQuery);
        
        try {
            
            //do parameter binding
            binding();
            
            statementSQL.execute();
            ResultSet books = statementSQL.getResultSet();

            while(books.next()){

                //Retrieve the data
                String bookISBN  = books.getString("ISBN");
                
                //add the author to the authorsList
                try{
                    booksList.add(new Book(bookISBN));
                } catch(NotFound ex){
                    //This means the book was deleted.
                    //So no need to add it.
                }
            }
            
        } catch (SQLException ex) {
            errorHandler.report("BooksGetter Class", ex.getMessage());
            errorHandler.terminate();
        } finally{
            //Null the selectorStatement
            MysqlHandler.getInstance().closePreparedStatement(statementSQL);
            statementSQL = null;
        }

        reset();
        return booksList;
    }

    
    @Override
    public ArrayList<IBook> get(int limit, int offset) {
        
        ArrayList<IBook> booksList = new ArrayList<>();
        
        String sqlQuery = "SELECT DISTINCT `Books_ISBNs`.`ISBN` FROM `Books_ISBNs` INNER JOIN `books` " + 
                " ON `Books_ISBNs`.`Book_id` = `books`.`Book_id` " +
                " INNER JOIN `Categories` ON `Categories`.`category_id` = `books`.`category_id` "+
                " LEFT JOIN `Authors` ON `Authors`.`Book_id` = `books`.`Book_id` " +
                " WHERE " + getCondition() + " ORDER BY `Books_ISBNs`.`ISBN` LIMIT " + limit + " OFFSET " + offset;
        System.out.println(sqlQuery);
        statementSQL = MysqlHandler.getInstance().getPreparedStatement(sqlQuery);
        
        try {
            
            //do parameter binding
            binding();
            
            statementSQL.execute();
            ResultSet books = statementSQL.getResultSet();

            while(books.next()){

                //Retrieve the data
                String bookISBN  = books.getString("ISBN");
                
                //add the author to the authorsList
                try{
                    booksList.add(new Book(bookISBN));
                } catch(NotFound ex){
                    //This means the book was deleted.
                    //So no need to add it.
                }
            }
            
        } catch (SQLException ex) {
            errorHandler.report("BooksGetter Class", ex.getMessage());
            errorHandler.terminate();
        } finally{
            //Null the selectorStatement
            MysqlHandler.getInstance().closePreparedStatement(statementSQL);
            statementSQL = null;
        }

        reset();
        return booksList;
    }

    @Override
    public IBooksGetter searchBooksByISBN(String bookISBN) {
        match.add("Books_ISBNs.ISBN");
        against.add(bookISBN);
        return this;
    }

    @Override
    public IBooksGetter searchBooksByAuthor(String authorName) {
        match.add("Authors.Author_Name");
        against.add(authorName);
        return this;
    }

    @Override
    public IBooksGetter searchBooksByPublisher(String publisherName) {
        match.add("Publishers.Publisher_Name");
        against.add(publisherName);
        return this;
    }

    @Override
    public IBooksGetter searchBooksByCategory(String categoryName) {
        match.add("Categories.Category_Name");
        against.add(categoryName);
        return this;
    }

    @Override
    public IBooksGetter getBooksByThreshold(int thresholdStart, int thresholdEnd) {
        columnsNames.add("Books_ISBNs.Threshold");
        intArguments.add(thresholdStart);
        intArguments.add(thresholdEnd);
        return this;
    }

    @Override
    public IBooksGetter getBooksByAvailability(int availableQuantityStart, int availableQuantityEnd) {
        columnsNames.add("Books_ISBNs.Available_Quantity");
        intArguments.add(availableQuantityStart);
        intArguments.add(availableQuantityEnd);
        return this;
    }
    
    public static void main(String argc[]) throws CloneNotSupportedException{
        BooksGetter bg =  new BooksGetter();
        System.out.println(bg.getBooksByISBN("xxxxx").booksCount());
        }
}
