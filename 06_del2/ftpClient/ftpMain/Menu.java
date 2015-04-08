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
		out = menuScan.nextLine();
		return out;
		
	}
	
	public void list(String list, String fileArray[]) { //String list returnerer listen af filer på FTP-serveren, omdannet til strings
		System.out.println("Liste over filer på ftp-server: ");
		for (int i = 0; i < fileArray.length; i++) {
			System.out.println(fileArray[i]);
		}
		System.out.println(list); //Her skal der laves en for løkke der printer alle filtitlerne ud
	
	}
	
	public void retrieve(File file, String fileArray[]) { //Hvilken type skal filen være??, fileArray bruges i menuControlleren til at holde styr på filerne
		for (int i = 0; i < fileArray.length; i++) {
			System.out.println(fileArray[i]);
		}
		System.out.println("Indtast hvilket filnumer du vil hente");
		String out;
		out = menuScan.nextLine();
		for (int i = 0; i < fileArray.length; i++) {
			if (out == fileArray[i]) { //Hvad skal der stå i stedet for?
			System.out.println("Du har hentet fil nr" + fileArray[i]); //højst sandsynligt ikke den optimale løsning
			}
		} 
		
	
	}
	public String sensorOverblik() {
		String out;
		System.out.println("Sensor overblik for alle sensorere i systemet");
		System.out.println("Indtast tal for hvilken sensor du vil bruge");
		System.out.println("Tast 1 for sensor 1");
		System.out.println("Tast 2 for sensor 4");
		System.out.println("Tast 3 for sensor 222");
		System.out.println("Tast 4 for sensor 7");
		System.out.println("Tast e for at gå tilbage til hovedmenu");
		
		out = menuScan.nextLine();
		return out;
	}
	
	public String sensorMenu() {
		String out;
		System.out.println("Indtast tallet for hvilken funktion der skal udføres");
		System.out.println("1: Bed sensor om at øge sit samplingsinterval.");
		System.out.println("2: Bed sensor om at mindske sit samplingsinterval.");
		System.out.println("3: Bed sensor om at begynde måling.");
		System.out.println("4: Bed sensor om at stoppe måling.");
		System.out.println("e: Gå tilbage til hovedmenu.");
		out = menuScan.nextLine();
		return out;
}
	public String setSampling() {
		String out;
		System.out.println("Hvor meget vil du ændre samplingsintervallet med?");
		out = menuScan.nextLine();
		return out;
	}

	@Override
	public void list() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void retrieve() {
		// TODO Auto-generated method stub
		
	}

	
	
}
