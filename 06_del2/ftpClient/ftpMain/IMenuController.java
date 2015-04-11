package ftpMain;

import java.io.IOException;

public interface IMenuController {
	
	void connectZybo(String host, int port);

	void choice() throws NumberFormatException, IOException;

	void specificSensor(String sensor) throws NumberFormatException, IOException;

	String download();

	

}
