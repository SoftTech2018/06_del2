package ftpMain;

import java.io.File;
import java.util.Scanner;

public class Menu implements IMenu {

	private Scanner menuScan;
	
	public Menu(Scanner menuScan) {
		this.menuScan = menuScan;
	}
	
	public String showMenu() {
		
		String out;
		System.out.println("Indtast 1 for list-kommando");
		System.out.println("Indtast 2 for retrieve-kommando");
		System.out.println("Indtast 3 for sensor-menu");
		System.out.println("Indtast 4 for at afslutte programmet");
		out = menuScan.next();
		return out;
		
	}
	
	public String list(String list) { //String list returnerer listen af filer på FTP-serveren, omdannet til strings
		System.out.println("Du har valgt list-kommandoen, du har nu følgende muligheder: ");
		System.out.println(list); //Her skal der laves en for løkke der printer alle filtitlerne ud
		System.out.println("Indtast 2 for retrieve-kommando");
		System.out.println("Indtast 3 for overblik over sensorere i systemet");
		System.out.println("Indtast 4 for at afslutte programmet");
		
		String out;
		
		out = menuScan.next();
		return out;
	}
	
	public void retrieve(File file, int fileArray[]) { //Hvilken type skal filen være??, fileArray bruges i menuControlleren til at holde styr på filerne
		for (int i = 0; i < fileArray.length; i++) {
			System.out.println(fileArray[i]);
		}
		System.out.println("Indtast hvilket filnumer du vil hente");
		String out;
		out = menuScan.next();
		for (int i = 0; i < fileArray.length; i++) {
			if (out == fileArray[i]) { //Hvad skal der stå i stedet for?
			System.out.println("Du har hentet fil nr" + fileArray[i]); //højst sandsynligt ikke den optimale løsning
			}
		} 
		
	
	}
	public String sensorOverblik() {
		String out;
		System.out.println("Senson overblik for alle sensorere i systemet");
		System.out.println("Indtast tal for hvilken sensor du vil bruge");
		System.out.println("Tast 1 for sensor 1");
		System.out.println("Tast 2 for sensor 4");
		System.out.println("Tast 3 for sensor 222");
		System.out.println("Tast 4 for sensor 7");
		
		out = menuScan.next();
		return out;
	}
	
	public String sensorMenu() {
		String out;
		System.out.println("Indtast tallet for hvilken funktion der skal udføres");
		System.out.println("1: Bed sensor om at øge sit samplingsinterval.");
		System.out.println("2: Bed sensor om at mindske sit samplingsinterval.");
		System.out.println("3: Bed sensor om at begynde måling.");
		System.out.println("4: Bed sensor om at stoppe måling.");
		
		out = menuScan.next();
		return out;
}
}
