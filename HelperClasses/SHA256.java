package HelperClasses;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Fares
 */
public class SHA256 {
    
    private ErrorHandler errorHandler;
    
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < hash.length; i++) {
        String hex = Integer.toHexString(0xff & hash[i]);
        if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
    
    private String sha265Password(String passwordToBeHashed){
        try {
            
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(
                    passwordToBeHashed.getBytes(StandardCharsets.UTF_8));
            
            return bytesToHex(encodedhash);
            
        } catch (NoSuchAlgorithmException ex) {
            errorHandler.report("SHA256 Class", ex.getMessage());
            errorHandler.terminate();
            return null;
        }
    }
    
    public String hash(String textToBeHashed){
        return sha265Password(textToBeHashed);
    }
}
