package ftpMain;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu implements IMenu {

	private Scanner menuScan;
	
	public Menu(Scanner menuScan) {
		this.menuScan = menuScan;
	}
	
	@Override
	public String showMenu() {
		String out;
		System.out.println("**************************************");
		System.out.println("Indtast 1 for list-kommando");
		System.out.println("Indtast 2 for retrieve-kommando");
		System.out.println("Indtast 3 for sensor-menu");
		System.out.println("Indtast 4 for at afslutte programmet");
		System.out.println("**************************************");
		System.out.println("");
		out = menuScan.nextLine();
		return out;
		
	}
	
	@Override
	public String sensorOverblik() {
		String out;
		System.out.println("**************************************");
		System.out.println("Sensor overblik for alle sensorere i systemet");
		System.out.println("Indtast tal for hvilken sensor du vil bruge");
		System.out.println("Tast 1 for sensor 1");
		System.out.println("Tast 4 for sensor 4");
		System.out.println("Tast 222 for sensor 222");
		System.out.println("Tast 7 for sensor 7");
		System.out.println("Tast e for at gå tilbage til hovedmenu");
		System.out.println("**************************************");
		System.out.println("");
		out = menuScan.nextLine();
		return out;
	}
	
	@Override
	public String sensorMenu() {
		String out;
		System.out.println("**************************************");
		System.out.println("Indtast hvilken funktion der skal udføres");
		System.out.println("I: Bed sensor om at ændre sit samplingsinterval.");
		System.out.println("S: Bed sensor om at begynde måling.");
		System.out.println("B: Bed sensor om at stoppe måling.");
		System.out.println("e: Gå tilbage til hovedmenu.");
		System.out.println("**************************************");
		System.out.println("");
		out = menuScan.nextLine();
		return out;
	}
	
	@Override
	public String setSampling() {
		String out;
		System.out.println("**************************************");
		System.out.println("Hvor meget vil du ændre samplingsintervallet? (eks: 12300)");
		System.out.println("**************************************");
		System.out.println();
		out = menuScan.nextLine();
		return out;
	}
	
	@Override
	public String downloadFileName(){
		String output;
		System.out.println("**************************************");
		System.out.println("Skriv navnet på filen du ønsker at hente: ");
		System.out.println("**************************************");
		System.out.println();
		output = menuScan.nextLine();
		return output;
	}
	
	@Override
	public String downloadDestination(){
		String output;
		System.out.println("**************************************");
		System.out.println("Hvor skal filen placeres på harddisken?");
		System.out.println("Eksempel: C:/Users/JACOB/Desktop/Test/\r\n"+"NB: slut af med /");
		System.out.println("**************************************");
		System.out.println();
		output = menuScan.nextLine();
		return output;
	}
	
	@Override
	public void udskriv(String print) {
		System.out.println("**************************************");
		System.out.println(print);
		System.out.println("**************************************");
		System.out.println();
	}

	@Override
	public void showList(ArrayList<String> fileList) {
		for (String files : fileList){
			System.out.println(files);
		}
		System.out.println();
	}
}