package ModelsImplementation;

import HelperClasses.ErrorHandler;
import HelperClasses.NotFound;
import ModelsInterfaces.IBook;
import ModelsInterfaces.IOrder;
import ModelsInterfaces.IOrdersGetter;
import ModelsInterfaces.IUser;
import Mysql.MysqlHandler;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author Fares
 */
public class OrdersGetter implements IOrdersGetter{

    //column names
    private final ArrayList<String> columnsNames;
    
    //Argument given in the conditions
    private final ArrayList<Integer> intArguments;
    private final ArrayList<Double> doubleArguments;
    private final ArrayList<String> stringArguments;
    private boolean invalid;
    
    //Mysql handler
    private PreparedStatement statementSQL;
    private final ErrorHandler errorHandler;

    void reset(){
        columnsNames.clear();
        intArguments.clear();
        doubleArguments.clear();
        stringArguments.clear();
        invalid = false;
    }
    
    public OrdersGetter(){
        errorHandler = new ErrorHandler();
        columnsNames = new ArrayList<>();
        intArguments = new ArrayList<>();
        doubleArguments = new ArrayList<>();
        stringArguments = new ArrayList<>();
        invalid = false;
    }
    
    @Override
    public IOrdersGetter getOrdersByUser(IUser user) {
        try {
            columnsNames.add("orders.User_id");
            intArguments.add(user.getId());
        } catch (NotFound ex) {
            invalid = true;
        }
        return this;
    }

    @Override
    public IOrdersGetter getOrdersByCreditcardNo(String creditcardNo) {
        columnsNames.add("orders.credit_card_no");
        stringArguments.add(creditcardNo);
        return this;
    }

    @Override
    public IOrdersGetter getOrdersByTime(Timestamp startingDate, Timestamp endingDate) {
        columnsNames.add("orders.Order_Time");
        stringArguments.add(startingDate.toString().substring(0, "yyyy-mm-dd hh:mm:ss".length()));
        stringArguments.add(endingDate.toString().substring(0, "yyyy-mm-dd hh:mm:ss".length()));
        return this;
    }

    @Override
    public IOrdersGetter getOrdersByTotalPrice(double startingPrice, double endingPrice) {
        columnsNames.add("orderTotalPrice");
        doubleArguments.add(startingPrice);
        doubleArguments.add(endingPrice);
        return this;
    }

    String getCondition(){
        
        String condition = new String();
        
        for(int i=0;i<columnsNames.size();i++){
            
            if(i != 0){
                condition += " AND ";
            }

            if(columnsNames.get(i).equals("orders.Order_Time")){
                condition += columnsNames.get(i) + " >= ? ";
                condition += " AND ";
                condition += columnsNames.get(i) + " <= ? ";
            }
            else if(columnsNames.get(i).equals("orderTotalPrice")){
                condition += " EXISTS ( SELECT SUM(`TotalPrice`) AS `totalPrice` FROM `purchases` WHERE `purchases`.`Order_id` = `orders`.`id` GROUP BY Order_id HAVING totalPrice >= ? AND totalPrice <= ? ) ";
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
        int argPointer = 1;
        
        for(int i=0;i<columnsNames.size();i++){

            switch (columnsNames.get(i)) {
                case "orderTotalPrice":
                    statementSQL.setDouble(argPointer++,doubleArguments.get(doublePointer++));
                    statementSQL.setDouble(argPointer++,doubleArguments.get(doublePointer++));
                    break;
                case "orders.credit_card_no":
                    statementSQL.setString(argPointer++,stringArguments.get(stringPointer++));
                    break;
                case "orders.User_id":
                    statementSQL.setInt(argPointer++,intArguments.get(intPointer++));
                    break;
                case "orders.Order_Time":
                    System.out.println(stringArguments.get(stringPointer++));
                    System.out.println(stringArguments.get(stringPointer++));
                    stringPointer--;
                    stringPointer--;
                    statementSQL.setString(argPointer++,stringArguments.get(stringPointer++));
                    statementSQL.setString(argPointer++,stringArguments.get(stringPointer++));
                     break;
                default:
                    break;
            }
            
        }
        
    }

    @Override
    public ArrayList<IOrder> get() {
        
        ArrayList<IOrder> orderList = new ArrayList<>();
        String sqlQuery = "SELECT DISTINCT `orders`.`Id` FROM `orders` WHERE " + getCondition();

        statementSQL = MysqlHandler.getInstance().getPreparedStatement(sqlQuery);
        
        try {
            
            //do parameter binding
            binding();
            
            statementSQL.execute();
            ResultSet orders = statementSQL.getResultSet();

            while(orders.next()){

                //Retrieve the data
                int orderId  = orders.getInt("Id");
                
                //add the author to the authorsList
                try{
                    orderList.add(new Order(orderId));
                } catch(NotFound ex){
                    //This means the order was deleted.
                    //So no need to add it.
                }
            }
            
        } catch (SQLException ex) {
            errorHandler.report("OrdersGetter Class", ex.getMessage());
            errorHandler.terminate();
        } finally{
            //Null the selectorStatemen
            MysqlHandler.getInstance().closePreparedStatement(statementSQL);
            statementSQL = null;
        }

        reset();
        return orderList;
    }

    
    @Override
    public double salesSum() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int salesCount() {
        int val =  get().size();
        reset();
        return val;
    }
    
}