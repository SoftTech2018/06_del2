package ftpMain;

import java.util.ArrayList;

public interface IMenu {

	/**
	 * Viser hovedmenu
	 * @return : String af valg
	 */
	String showMenu();

	/**
	 * Menu der viser hvilken funktion der skal udføres på den valgte sensor
	 * @return String af valg
	 */
	String sensorMenu();

	/**
	 * Menu der viser de eksisterende sensorer
	 * @return String af valg
	 */
	String sensorOverblik();

	/**
	 * Spørger bruger hvor meget samplingsintervallet skal ændres med.
	 * @return String
	 */
	String setSampling();
	
	/**
	 * Spørger bruger hvilken fil der ønskes downloadet.
	 * @return String indeholdende filens navn
	 */
	String downloadFileName();
	
	/**
	 * Spørger bruger hvor filen skal ligges.
	 * @return String indeholdende f.eks.: C:/Users/JACOB/Desktop/
	 */
	String downloadDestination();

	/**
	 * Udskriver i consollen
	 * @param sendCommand: Hvad der skal udskrives
	 * @return 
	 */
	void udskriv(String sendCommand);
	

	/**
	 * Udskriver en liste af Strings
	 * @param fileList Listen der skal udskrives
	 */
	void showList(ArrayList<String> fileList);

	

	


	

	

}
