package ftpMain;

import java.io.IOException;
import java.util.Scanner;

import functionality.IZyboTransmitter;
import functionality.ZyboTransmitter;

public class Main {

	public static void main(String[] args) throws NumberFormatException, IOException {
//		int port;
//		String host;
//		
//		if (args.length == 2){
//			port = Integer.parseInt(args[1]);
//			host = args[0];
//		}
//		else {
//			port = 8000;
//			host = "localhost";			
//		}
		
		Scanner menuScan = new Scanner(System.in);
		
		System.out.println("indtast host");
		String host = menuScan.next();
		System.out.println("indtast port");
		int port = menuScan.nextInt();
		
		FTPclient ftpC = new FTPclient();
		IMenu menu = new Menu(menuScan);
		IZyboTransmitter zbtr = new ZyboTransmitter();
		IMenuController menuCon = new MenuController(menu, zbtr, ftpC, host, port);
		
		
	}
}
