
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
public interface ICategoryManager {
    
    ICategory getCategoryByName(String categoryName);
    ICategory getCategoryById(int id);
    ArrayList<ICategory> getAllCategories();
    
    boolean removeCategory(ICategory category);
    ICategory addCategory(String categoryName);
}
