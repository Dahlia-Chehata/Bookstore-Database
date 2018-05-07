/**
 *
 * @author Fares
 */
public interface ICategory {
    
    /**
     *
     * @return the id of this category.
     */
    int getId();

    /**
     *
     * @return the name of this category.
     */
    String getName();
    
    /**
     *
     * @param newName for this category.
     * @return true if the name update done successfully, false otherwise.
     */
    boolean changeName(String newName);
}
