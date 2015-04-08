package ftpMain;

import java.util.Scanner;

import functionality.ZyboTransmitter;

public class Main {

	public static void main(String[] args) {
		
		Scanner menuScan = new Scanner(System.in);
		
		IMenu menu = new Menu(menuScan);
		IZyboTransmitter zbtr = new ZyboTransmitter();
		IMenuController menuCon = new MenuController(menu, zbtr);
		
		
		menuCon.choice();
		
		
	}

}
