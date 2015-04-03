package wcuMain;

import java.io.FileNotFoundException;

import functionality.IReadFiles;
import functionality.ReadFiles;

import java.util.*;

public class Main {

	
	public static void main(String[] args) {
		IReadFiles rf = null;
		Menu menu = new Menu();
		try {
			rf = new ReadFiles();
			System.out.println("Test af ReadFiles.getProductName (produktnr 10): " + rf.getProductName(10));
			System.out.println("Test af ReadFiles.getProductName (produktnr 0): " + rf.getProductName(0));
			System.out.println("Skriver til log - OprNr 10, VareNr 1234, afvejet 1.478, lagerbeholdning 0.719");
			rf.writeLog(10, 1234, 1.478, 0.719);
			System.out.println("Opdaterer text-filen store.txt for produktnummer 10: " + rf.updProductInventory(10, 2.14));

		} catch (FileNotFoundException e) {
			System.out.println("Noget i filbehandlingen gik grueligt galt :-( Kontakt udvikleren.");
			e.printStackTrace();
			System.exit(1);
		}
		MenuController menuCon = new MenuController(menu,rf);
		
		menuCon.start();	
		
		
		
	
		
	}

}
