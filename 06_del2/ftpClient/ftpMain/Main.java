package ftpMain;

import java.util.Scanner;

import functionality.ZyboTransmitter;

public class Main {

	public static void main(String[] args) {
		int port;
		String host;
		
		if (args.length == 2){
			port = Integer.parseInt(args[1]);
			host = args[0];
		}
		else {
			port = 8000;
			host = "localhost";			
		}
		
		Scanner menuScan = new Scanner(System.in);
		
		IMenu menu = new Menu(menuScan);
		IZyboTransmitter zbtr = new ZyboTransmitter();
		IMenuController menuCon = new MenuController(menu, zbtr, host, port);		
		
	}
}
