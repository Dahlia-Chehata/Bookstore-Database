package ModelsInterfaces;


/**
 *
 * @author
 */
public interface IStatus {

    int getId();
    String getName();

    boolean changeName(String newName);
}
