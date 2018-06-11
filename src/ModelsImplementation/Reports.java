package ModelsImplementation;

import HelperClasses.ErrorHandler;
import HelperClasses.NotFound;
import ModelsInterfaces.IBook;
import ModelsInterfaces.IUser;
import Mysql.MysqlHandler;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fairous
 */
public class Reports {
   //Mysql handler
    private PreparedStatement statementSQL;
    private ErrorHandler errorHandler;
    
    public Reports(){
        errorHandler = new ErrorHandler();
    }
    
    public int TotalSalesLastMonth(){
        
        String sqlQuery = "SELECT SUM(purchases.TotalPrice) AS TOTAL FROM purchases " +
            "INNER JOIN orders ON orders.Id = purchases.Order_id " +
            "WHERE month(orders.Order_Time) = (((month(NOW())+10) mod 12)+1) " +
            "AND ((year(orders.Order_Time) = year(NOW()) AND MONTH(NOW()) != 1) OR (year(orders.Order_Time) = year(NOW())-1 AND MONTH(NOW()) = 1))";

        statementSQL = MysqlHandler.getInstance().getPreparedStatement(sqlQuery);
        ResultSet data = null;
        
        try{
            
            statementSQL.execute();
            
            data = statementSQL.getResultSet();
            if(data.next()){
                return data.getInt("TOTAL");
            }
            
        } catch (SQLException ex) {
            errorHandler.report("Reports Class", ex.getMessage());
            errorHandler.terminate();
        } finally {
            //Close && nullify the statement
            MysqlHandler.getInstance().closePreparedStatement(statementSQL);
            statementSQL = null;
        }
        
        return 0;
    }
    
    public ArrayList<IUser> top5CustomersLast3Months(){
        
        String sqlQuery = "SELECT Users.User_id FROM Users " +
            "INNER JOIN orders on orders.User_id = Users.User_id " +
            "INNER JOIN purchases ON orders.Id = purchases.Order_id " +
            "WHERE month(orders.Order_Time) >= (((month(NOW())+8) mod 12)+1) AND month(orders.Order_Time) <= (((month(NOW())+10) mod 12)+1) " +
            "AND ((year(orders.Order_Time) = year(NOW()) AND MONTH(NOW()) != 1) OR (year(orders.Order_Time) = year(NOW())-1 AND MONTH(NOW()) = 1)) " +
            "GROUP BY Users.User_id  " +
            "ORDER BY SUM(purchases.TotalPrice) DESC " +
            "LIMIT 5";

        statementSQL = MysqlHandler.getInstance().getPreparedStatement(sqlQuery);
        ResultSet data = null;
        
        ArrayList<IUser> userArrayList = new ArrayList<>();
        
        try{
            
            statementSQL.execute();
            
            data = statementSQL.getResultSet();
            while(data.next()){
                try {
                    userArrayList.add(new User(data.getInt("User_id")));
                } catch (NotFound ex) {
                    //user is deleted
                    //continue
                }
            }
            
        } catch (SQLException ex) {
            errorHandler.report("Reports Class", ex.getMessage());
            errorHandler.terminate();
        } finally {
            //Close && nullify the statement
            MysqlHandler.getInstance().closePreparedStatement(statementSQL);
            statementSQL = null;
        }
        
        return userArrayList;
    }
    
    public ArrayList<IBook> top10BooksLast3Months(){
        
        String sqlQuery = "SELECT Books_ISBNs.ISBN FROM Books_ISBNs " +
            "INNER JOIN purchases ON purchases.ISBN = Books_ISBNs.ISBN " +
            "INNER JOIN orders ON orders.Id = purchases.Order_id " +
            "WHERE month(orders.Order_Time) >= (((month(NOW())+8) mod 12)+1) AND month(orders.Order_Time) <= (((month(NOW())+10) mod 12)+1) " +
            "AND ((year(orders.Order_Time) = year(NOW()) AND MONTH(NOW()) != 1) OR (year(orders.Order_Time) = year(NOW())-1 AND MONTH(NOW()) = 1)) " +
            "GROUP BY Books_ISBNs.ISBN " +
            "ORDER BY SUM(purchases.no_of_copies) DESC " +
            "LIMIT 10";

        statementSQL = MysqlHandler.getInstance().getPreparedStatement(sqlQuery);
        ResultSet data = null;
        
        ArrayList<IBook> topBookList = new ArrayList<>();
        
        try{
            
            statementSQL.execute();
            
            data = statementSQL.getResultSet();
            while(data.next()){
                try {
                    topBookList.add(new Book(data.getString("ISBN")));
                } catch (NotFound ex) {
                    //book is deleted
                    //continue
                }
            }
            
        } catch (SQLException ex) {
            errorHandler.report("Reports Class", ex.getMessage());
            errorHandler.terminate();
        } finally {
            //Close && nullify the statement
            MysqlHandler.getInstance().closePreparedStatement(statementSQL);
            statementSQL = null;
        }
        
        return topBookList;
    }
    
    public static void main(String argc[]){
        Reports r = new Reports();
        System.out.println(r.TotalSalesLastMonth());
        System.out.println(r.top10BooksLast3Months().size());
        System.out.println(r.top5CustomersLast3Months().size());
        MysqlHandler.getInstance().state();
    }
}
