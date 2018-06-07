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
public class BooksGetter implements IBooksGetter{

    //column names
    private final ArrayList<String> columnsNames;

    //Argument given in the conditions
    private final ArrayList<Integer> intArguments;
    private final ArrayList<Double> doubleArguments;
    private final ArrayList<String> stringArguments;
    private boolean invalid;

    //Mysql handler
    private PreparedStatement statementSQL;
    private ErrorHandler errorHandler;


    void reset(){
        columnsNames.clear();
        intArguments.clear();
        doubleArguments.clear();
        stringArguments.clear();
        invalid = false;
    }

    public BooksGetter(){
        columnsNames = new ArrayList<>();
        intArguments = new ArrayList<>();
        doubleArguments = new ArrayList<>();
        stringArguments = new ArrayList<>();
        invalid = false;
    }

    @Override
    public IBooksGetter getBooksByTitle(String bookTitle) {
        columnsNames.add("books.Title");
        stringArguments.add(bookTitle);
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

        for(int i=0;i<columnsNames.size();i++){

            if(i != 0){
                condition += " AND ";
            }

            if(columnsNames.get(i).equals("Books_ISBNs.Publication_Year")){
                condition += columnsNames.get(i) + " > ? ";
                condition += " AND ";
                condition += columnsNames.get(i) + " < ? ";
            }
            else if(columnsNames.get(i).equals("Books_ISBNs.Selling_price")){
                condition += columnsNames.get(i) + " > ? ";
                condition += " AND ";
                condition += columnsNames.get(i) + " < ? ";
            }
            else{
                condition += columnsNames.get(i) + " = ? ";
            }

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

        for(int i=0;i<columnsNames.size();i++){

            switch (columnsNames.get(i)) {
                case "Books_ISBNs.Publication_Year":
                    statementSQL.setInt(i+1,intArguments.get(intPointer++));
                    statementSQL.setInt(i+1,intArguments.get(intPointer++));
                    break;
                case "Books_ISBNs.Selling_price":
                    statementSQL.setDouble(i+1,doubleArguments.get(doublePointer++));
                    statementSQL.setDouble(i+1,doubleArguments.get(doublePointer++));
                    break;
                case "books.Title":
                    statementSQL.setString(i+1,stringArguments.get(stringPointer++));
                    break;
                case "books.category_id":
                    statementSQL.setInt(i+1,intArguments.get(intPointer++));
                    break;
                case "Authors.Author_Name":
                    statementSQL.setString(i+1,stringArguments.get(stringPointer++));
                    break;
                case "Books_ISBNs.ISBN":
                    statementSQL.setString(i+1,stringArguments.get(stringPointer++));
                    break;
                case "books.Book_id":
                    statementSQL.setInt(i+1,intArguments.get(intPointer++));
                    break;
                case "Books_ISBNs.Publisher_id":
                    statementSQL.setInt(i+1,intArguments.get(intPointer++));
                    break;
                default:
                    break;
            }

        }

    }

    @Override
    public int booksCount() {
        reset();
        return get().size();
    }

    @Override
    public ArrayList<IBook> get() {

        ArrayList<IBook> booksList = new ArrayList<>();

        String sqlQuery = "SELECT DISTINCT `Books_ISBNs`.`ISBN` FROM `Books_ISBNs` INNER JOIN `books` " +
                " ON `Books_ISBNs`.`Book_id` = `books`.`Book_id` " +
                " INNER JOIN Authors ON `Authors`.`Book_id` = `books`.`Book_id` " +
                " WHERE " + getCondition();

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
            errorHandler.report("Author Class", ex.getMessage());
            errorHandler.terminate();
        }

        //Null the selectorStatemen
        MysqlHandler.getInstance().closePreparedStatement(statementSQL);
        statementSQL = null;

        reset();
        return booksList;
    }

}
