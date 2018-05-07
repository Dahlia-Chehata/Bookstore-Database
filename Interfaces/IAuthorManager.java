
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
public interface IAuthorManager {
    
    IAuthor getOrAddAuthor(String authorName);

    ArrayList<IAuthor> getAllAuthors();
}