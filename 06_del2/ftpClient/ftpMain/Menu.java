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
	
	//SKAL SANDSYNLIGVIS FJERNES
	public void list(String list, String fileArray[]) { //String list returnerer listen af filer pï¿½ FTP-serveren, omdannet til strings
		System.out.println("Liste over filer pï¿½ ftp-server: ");
		for (int i = 0; i < fileArray.length; i++) {
			System.out.println(fileArray[i]);
		}
		System.out.println(list); //Her skal der laves en for lï¿½kke der printer alle filtitlerne ud
	
	}
	
	//SKAL SANDSYNLIGVIS FJERNES
	public void retrieve(File file, String fileArray[]) { //Hvilken type skal filen vï¿½re??, fileArray bruges i menuControlleren til at holde styr pï¿½ filerne
		for (int i = 0; i < fileArray.length; i++) {
			System.out.println(fileArray[i]);
		}
		System.out.println("Indtast hvilket filnumer du vil hente");
		String out;
		out = menuScan.nextLine();
		for (int i = 0; i < fileArray.length; i++) {
			if (out == fileArray[i]) { //Hvad skal der stï¿½ i stedet for?
			System.out.println("Du har hentet fil nr" + fileArray[i]); //hï¿½jst sandsynligt ikke den optimale lï¿½sning
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
		System.out.println("Tast e for at gï¿½ tilbage til hovedmenu");
		
		out = menuScan.nextLine();
		return out;
	}
	
	public String sensorMenu() {
		String out;
		System.out.println("Indtast tallet for hvilken funktion der skal udfï¿½res");
		System.out.println("1: Bed sensor om at Ã¦ndre sit samplingsinterval.");
		System.out.println("2: Bed sensor om at begynde mï¿½ling.");
		System.out.println("3: Bed sensor om at stoppe mï¿½ling.");
		System.out.println("e: Gï¿½ tilbage til hovedmenu.");
		out = menuScan.nextLine();
		return out;
	}
	public String setSampling() {
		String out;
		System.out.println("Hvor meget vil du ï¿½ndre samplingsintervallet med?");
		out = menuScan.nextLine();
		return out;
	}
	
	public String downloadFile(){
		String output;
		System.out.print("Skriv navnet pï¿½ filen du ï¿½nsker at hente: ");
		output = menuScan.next();
		return output;
	}
	
	public String downloadDestination(){
		String output;
		System.out.println("Hvor skal filen placeres på harddisken?");
		System.out.println("Eksempel: C:/Users/JACOB/Desktop/Test/");
		output = menuScan.next();
		return output;
	}

	//SKAL SANDSYNLIGVIS FJERNES
	@Override
	public void list() {
		// TODO Auto-generated method stub
		
	}
	//SKAL SANDSYNLIGVIS FJERNES
	@Override
	public void retrieve() {
		// TODO Auto-generated method stub
		
	}

	
	
}
