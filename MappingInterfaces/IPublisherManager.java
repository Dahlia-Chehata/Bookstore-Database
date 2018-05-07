import java.util.ArrayList;

/**
 *
 * @author Fares
 */
public interface IPublisherManager {
    
    /**
     *
     * @param id the id of the wanted publisher.
     * @return object of the Publisher if found, null otherwise.
     */
    IPublisher getById(int id);

    /**
     *
     * @param name the name of the wanted publishers.
     * @return ArrayList of all publishers with this name - the array could
     * be empty.
     */
    ArrayList<IPublisher> getByName(String name);   

    /**
     *
     * @return ArrayList of all the publisher existing in the application.
     */
    ArrayList<IPublisher> getAllPublishers();
 
    /**
     *
     * @param name of the publisher to be added.
     * @param address of the publisher to be added.
     * @param telephone of the publisher to be added.
     * @return true incase the addition succeeded, false otherwise. 
     */
    IPublisher addPublisher(String name, String address,
                            String telephone);
}
