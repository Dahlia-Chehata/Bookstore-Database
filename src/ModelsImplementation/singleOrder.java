package ModelsImplementation;

import ModelsInterfaces.IBook;
/**
 * 
 * @author Dahlia
 *
 */
public class singleOrder{
	int managerOrderId;
	int quantity;
	IBook book;
	
	public singleOrder(int managerOrderId,int quantity,IBook book) {
		this.managerOrderId = managerOrderId;
		this.quantity=quantity;
		this.book=book;
	}
}