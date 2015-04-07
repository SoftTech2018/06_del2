package ftpMain;

import java.awt.Choice;
import java.util.Scanner;

public class MenuController implements IMenuController{

	private IMenu menu;

	
	
	
	public MenuController(IMenu menu) { // hvorfor kan den ikke være public?
		this.menu = menu;
	}
	
	public void choice() {
		
		switch (menu.showMenu()) {
		case "1":
			menu.list(); //Viser fil-liste på ftp server
			// Her skal der tilføjes en kommando til at kontakte ftp serveren
		case "2":
			menu.retrieve(); //Henter fil fra ftp server
			// Her skal der tilføjes en kommando til at kontakte ftp serveren
		case "3":
			menu.sensorMenu();
			sensorChoice();
		case "4":
			//Afslut program
		default:
			System.out.println("Forkert indtastning - prøv igen!");
			choice();
		}	
	}
	
	public void sensorChoice() {
		switch (menu.sensorMenu()) {
		case "1":
			//Her skal der kaldes en metode i den socket-klasse der har kontakt til zybo-boardets sensorere
			//Der skal være flere end 1 switch case
		case "7": //kalder startup-menuen
			choice();
		default:
	
		}
	}
	
	//MenuController skal bruge en måde at modtage fil-listen på og videresende til Menu - evt et array?
}
