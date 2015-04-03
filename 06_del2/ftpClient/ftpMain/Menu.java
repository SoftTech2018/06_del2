package ftpMain;

import java.util.Scanner;

public class Menu implements IMenu {

	private Scanner menuScan;
	
	public Menu(Scanner menuScan) {
		this.menuScan = menuScan;
	}
	
	public void showMenu() {
		System.out.println("Indtast 1 for list-kommando");
		System.out.println("Indtast 2 for retrieve-kommando");
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
	}
	
	public void list(String list) { //String list returnerer listen af filer på FTP-serveren, omdannet til strings
		System.out.println("Du har valgt list-kommandoen, du har nu følgende muligheder: ");
		System.out.println(list); //Her skal der laves en for løkke der printer alle filtitlerne ud
		System.out.println("Indtast 2 for retrieve, efterfulgt af tallet ud fra den fil du vil hente.");
	}
	
	public void retrieve(File file) { //Hvilken type skal filen være??
		System.out.println("Du har hentet fil " + file);
	}
	
}
