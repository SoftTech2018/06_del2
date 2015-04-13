package ftpMain;

import java.io.IOException;

public interface IMenuController {
	
	/**
	 * Skaber forbindelse til ZyBo og starter Hoved menuen.
	 * @param host -
	 * @param port
	 */
	void connectZybo(String host, int port);

	/**
	 * Kører hovedmenuen og switcher på brugerens input
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	void choice() throws NumberFormatException, IOException;

	/**
	 * Finder ud af hvilken sensor der skal ændres i og hvad der skal ske med denne - eksikverer dernæst sendCommand i ZyBoTransmitter
	 * @param Sensor - hvilken sensor der vælges.
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	void specificSensor(String sensor) throws NumberFormatException, IOException;

}
