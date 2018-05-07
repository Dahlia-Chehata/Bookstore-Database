
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Fairous
 */
public interface IUserManager {
    
    IUser getById(int id);
    ArrayList<IUser> getByUsername(String username);
    IUser getByEmail(String email);
    IUser getByEmailAndPassword(String email, String password);
    
    IUser addUser(String username, String email, String password,
                  String fname, String lname, String sefaultShippingAddress,
                  String phoneNumber);
}
