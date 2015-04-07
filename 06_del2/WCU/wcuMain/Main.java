package wcuMain;

import java.io.FileNotFoundException;

import functionality.IReadFiles;
import functionality.ReadFiles;

import java.util.*;

public class Main {

	
	public static void main(String[] args) {
		System.out.println("test");
		IReadFiles rf = null;
		IMenu menu = new Menu();
		System.out.println("test");
		try {
			System.out.println("test");
			rf = new ReadFiles();
			IMenuController menuCon = new MenuController(menu,rf);
			menuCon.start();
		} catch (FileNotFoundException e) {
			System.out.println("Noget i filbehandlingen gik grueligt galt :-( Kontakt udvikleren.");
			e.printStackTrace();
			System.exit(1);
		}
		System.exit(0);

//		
//		System.out.println("Test af ReadFiles.getProductName (produktnr 10): " + rf.getProductName(10));
//		System.out.println("Test af ReadFiles.getProductName (produktnr 0): " + rf.getProductName(0));
//		System.out.println("Skriver til log - OprNr 10, VareNr 1234, afvejet 1.478, lagerbeholdning 0.719");
//		rf.writeLog(10, 1234, 1.478, 0.719);
//		System.out.println("Opdaterer text-filen store.txt for produktnummer 10: " + rf.updProductInventory(10, 2.14));
	}

}
