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
	
	public int list(String list) { //String list returnerer listen af filer p� FTP-serveren, omdannet til strings
		System.out.println("Du har valgt list-kommandoen, du har nu f�lgende muligheder: ");
		System.out.println(list); //Her skal der laves en for l�kke der printer alle filtitlerne ud
		System.out.println("Indtast 2 for retrieve-kommando");
		
		int out;
		
		out = menuScan.nextInt();
		return out;
	}
	
	public void retrieve(File file, int fileArray[]) { //Hvilken type skal filen v�re??, fileArray bruges i menuControlleren til at holde styr p� filerne
		for (int i = 0; i < fileArray.length; i++) {
			System.out.println(fileArray[i]);
		}
		System.out.println("Indtast hvilket filnumer du vil hente");
		int out;
		out = menuScan.nextInt();
		for (int i = 0; i < fileArray.length; i++) {
			if (out = fileArray[i]) {
			System.out.println("Du har hentet fil nr" + fileArray[i]); //h�jst sandsynligt ikke den optimale l�sning
			}
		} 
		
	
	}
	}
	public String sensorMenu() {
		String out;
		System.out.println("Indtast tallet for hvilken funktion der skal udf�res");
	
		out = menuScan.next();
		return out;
}
