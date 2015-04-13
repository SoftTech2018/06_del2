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
	
	public String sensorOverblik() {
		String out;
		System.out.println("Sensor overblik for alle sensorere i systemet");
		System.out.println("Indtast tal for hvilken sensor du vil bruge");
		System.out.println("Tast 1 for sensor 1");
		System.out.println("Tast 4 for sensor 4");
		System.out.println("Tast 222 for sensor 222");
		System.out.println("Tast 7 for sensor 7");
		System.out.println("Tast e for at g� tilbage til hovedmenu");
		
		out = menuScan.nextLine();
		return out;
	}
	
	public String sensorMenu() {
		String out;
		System.out.println("Indtast hvilken funktion der skal udf�res");
		System.out.println("I: Bed sensor om at ændre sit samplingsinterval.");
		System.out.println("S: Bed sensor om at begynde m�ling.");
		System.out.println("B: Bed sensor om at stoppe m�ling.");
		System.out.println("e: G� tilbage til hovedmenu.");
		out = menuScan.nextLine();
		return out;
	}
	public String setSampling() {
		String out;
		System.out.println("Hvor meget vil du �ndre samplingsintervallet? (eks: 12300)");
		out = menuScan.nextLine();
		return out;
	}
	
	public String downloadFileName(){
		String output;
		System.out.print("Skriv navnet paa filen du oensker at hente: ");
		output = menuScan.nextLine();
		return output;
	}
	
	public String downloadDestination(){
		String output;
		System.out.println("Hvor skal filen placeres paa harddisken?");
		System.out.println("Eksempel: C:/Users/JACOB/Desktop/Test/\r\n"+"NB: slut af med /");
		output = menuScan.nextLine();
		return output;
	}
	public void udskriv(String print) {
		System.out.println(print);
	}

	
	
}
