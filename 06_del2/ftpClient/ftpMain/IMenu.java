package ftpMain;

import java.util.Scanner;

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
	String downloadFile();
	
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

	

	


	

	

}
