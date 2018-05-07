/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Fairous
 */
public interface IStatus {
    
    int getId();
    String getName();
    
    boolean changeName(String newName);
}
