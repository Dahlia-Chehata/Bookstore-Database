package Mysql;

import HelperClasses.ErrorHandler;
import java.sql.*;
import java.util.Set;
import java.util.HashSet;

/**
 *
 * @author Fares
 */
final public class MysqlHandler{

    //  Database credentials
    private String USER = "root";
    private String PASS = "123456";
    private String DB_NAME = "book_store";
    
    // JDBC driver name and database URL
    private String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    private String DB_URL = "jdbc:mysql://localhost/"+DB_NAME+"?autoReconnect=true&useSSL=false";
    
    //Set of used statements
    Set<Statement> used_Statement;
    //Set of used statements
    Set<PreparedStatement> used_Prepared_Statement;
    //Set of used statements
    Set<Connection> used_Connection;

    //connection to the database
    Connection conn;

    //error handler
    private final ErrorHandler errorHandler;

    //Singeleton
    private static final MysqlHandler instance = new MysqlHandler();
    public static MysqlHandler getInstance(){
        return instance;
    }
    
    private MysqlHandler(){
        
        this.errorHandler = new ErrorHandler();
        this.used_Statement = new HashSet<>();
        this.used_Prepared_Statement = new HashSet<>();
        this.used_Connection = new HashSet<>();
        
        if(this.registerJDBC() == true){
            this.conn = getConnection();
        }
    }
    
    private boolean registerJDBC(){
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            this.errorHandler.report("JDBC", "Error in registering the MYSQL driver");
            return false;
        }
        
        return true;
    }
    
    public Connection getConnection(){
        
        Connection toReturn = null;
        
        try {
            toReturn = DriverManager.getConnection(this.DB_URL,this.USER,this.PASS);
        } catch (SQLException ex) {
            this.errorHandler.report("JDBC", ex.getMessage());
        }
        
        if(toReturn != null){
            used_Connection.add(toReturn);
        }
        
        return toReturn;
    }
    
    public Statement getStatement(){
        
        //check conn
        if(this.conn == null){
            return null;
        }
        
        Statement toReturn = null;
        try {
            toReturn = this.conn.createStatement();
        } catch (SQLException ex) {
            this.errorHandler.report("JDBC", ex.getMessage());
        }
        
        if(toReturn != null){
            used_Statement.add(toReturn);
        }
        
        return toReturn;
    }
    
    public PreparedStatement getPreparedStatementWithKeys(String statement){
        
        //check conn
        if(this.conn == null){
            return null;
        }
        
        PreparedStatement toReturn = null;
        try {
            toReturn = this.conn.prepareStatement(statement,PreparedStatement.RETURN_GENERATED_KEYS);
        } catch (SQLException ex) {
            this.errorHandler.report("JDBC", ex.getMessage());
        }

        if(toReturn != null){
            used_Prepared_Statement.add(toReturn);
        }

        return toReturn;
    }
    
    public PreparedStatement getPreparedStatement(String statement){
        
        //check conn
        if(this.conn == null){
            return null;
        }
        
        PreparedStatement toReturn = null;
        try {
            toReturn = this.conn.prepareStatement(statement);
        } catch (SQLException ex) {
            this.errorHandler.report("JDBC", ex.getMessage());
        }
        
        if(toReturn != null){
            used_Prepared_Statement.add(toReturn);
        }
        
        return toReturn;
    }
    
    public void closeConnection(Connection connectionToBeClosed){
        
        if(connectionToBeClosed == null){
            return;
        }
        
        try {
            connectionToBeClosed.close();
        } catch (SQLException ex) {
            this.errorHandler.report(this.toString(), ex.getMessage());
        }
        
        used_Connection.remove(connectionToBeClosed);
    }
    
    public void closeStatement(Statement stmToBeClosed){
        
        if(stmToBeClosed == null){
            return;
        }
        
        try {
            stmToBeClosed.close();
        } catch (SQLException ex) {
            this.errorHandler.report("JDBC", ex.getMessage());
        }
        
        used_Statement.remove(stmToBeClosed);
    }

    public void closePreparedStatement(PreparedStatement stmToBeClosed){
        
        if(stmToBeClosed == null){
            return;
        }
        
        try {
            stmToBeClosed.close();
        } catch (SQLException ex) {
            this.errorHandler.report("JDBC", ex.getMessage());
        }
        
        used_Prepared_Statement.remove(stmToBeClosed);
    }
    
    public void cleanUp(){
        used_Statement.forEach((singleStatement) -> {
	    try {
		singleStatement.close();
	    } catch (SQLException ex) {
		this.errorHandler.report("JDBC", ex.getMessage());
	    }
	});
        
        used_Prepared_Statement.forEach((singleStatement) -> {
	    try {
		singleStatement.close();
	    } catch (SQLException ex) {
		this.errorHandler.report("JDBC", ex.getMessage());
	    }
	});
        
        used_Connection.forEach((singleConnection) ->{
	    try {
		singleConnection.close();
	    } catch (SQLException ex) {
		this.errorHandler.report("JDBC", ex.getMessage());
	    }
	});
    }
    
    @Override
    public void finalize(){
        this.cleanUp();
    }
}
