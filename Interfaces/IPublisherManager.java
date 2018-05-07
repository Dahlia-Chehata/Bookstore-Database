
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
public interface IPublisherManager {
    
    IPublisher getById(int id);
    ArrayList<IPublisher> getByName(String name);   
    ArrayList<IPublisher> getAllPublishers();
 
    IPublisher addPublisher(String name, String address,
                            String telephone);
}
