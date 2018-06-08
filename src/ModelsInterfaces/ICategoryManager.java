package ModelsInterfaces;

import java.util.ArrayList;

/**
 *
 * @author Fares
 */
public interface ICategoryManager {
    
    /**
     *
     * @param categoryName, get the wanted category by name.
     * @return Object implements ICategory if found, null otherwise.
     */
    ICategory getCategoryByName(String categoryName);

    /**
     *
     * @param id, the id of the wanted category.
     * @return Object implements ICategory if found, null otherwise.
     */
    ICategory getCategoryById(int id);

    /**
     *
     * @return ArrayList of all the categories in the application.
     */
    ArrayList<ICategory> getAllCategories();
    
    /**
     *
     * @param category that wanted to be deleted.
     * @return true if the category no longer existed, false otherwise.
     */
    boolean removeCategory(ICategory category);

    /**
     *
     * @param categoryName of the new category.
     * @return object Category if addition done successfully, null otherwise.
     */
    ICategory addCategory(String categoryName);
}