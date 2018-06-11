package controller;

import ModelsImplementation.PublisherManager;
import ModelsInterfaces.IPublisherManager;
import gui.books_handling.AddPublisher;

public class ManagerAddPublisherController {

	private Manager_controller manager_controller;
	private IPublisherManager publisher_manager;
	private AddPublisher addpublisher_page;

	public ManagerAddPublisherController(Manager_controller manager_controller) {
		this.manager_controller = manager_controller;
		addpublisher_page = new AddPublisher(this);
		
	}


	public void add_publisher(String name, String address, String telephone) {
		publisher_manager = new PublisherManager();
		publisher_manager.addPublisher(name, address, telephone);

	}

}
