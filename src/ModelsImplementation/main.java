package ModelsImplementation;

import HelperClasses.NotFound;
import ModelsInterfaces.IManagerOrder;
import Mysql.MysqlHandler;

public class main {
public static void main(String[]args) throws NotFound {

MysqlHandler y = null ;
//y.getInstance();
IManagerOrder x = new ManagerOrder();

	x.addOrder(new Book("1560"), 12);
	//x.getManagerOrder();
}
}
