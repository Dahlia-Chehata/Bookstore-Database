package HelperClasses;

/**
 *
 * @author Fares
 */
public class ErrorHandler {

    public ErrorHandler() {
        
    }
    
    public void report(String errorOrigin, String errorMessage){
        System.out.println("Error From : " + errorOrigin + ", " + errorMessage);
    }
    
    public void terminate(){
        report("SYSTEM","Terminating due to unrecoverable fault.");
        System.exit(0);
    }
}
