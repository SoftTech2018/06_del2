package ftpMain;

import java.io.IOException;
import java.util.Scanner;

import functionality.IZyboTransmitter;
import functionality.ZyboTransmitter;

public class Main {

	public static void main(String[] args) throws NumberFormatException, IOException {
		int port = 0, zyboPort = 0;
		String host = null, user = null, pass = null, zyboHost = null;
		
		if (args.length == 6) {
			host = args[0];
			port = Integer.parseInt(args[1]);
			user = args[2];
			pass = args[3];
			zyboHost = args[4];
			zyboPort = Integer.parseInt(args[5]);
		}
		else {
			System.out.println("******************************");
			System.out.println("Du skal angive 6 parametre når programmet startes:");
			System.out.println("FTPhost FTPport FTPusername FTPpassword ZYBOhost ZYBOport");
			System.out.println("******************************");
			System.out.println("Programmet afsluttes.");
			System.exit(0);
		}
		
		Scanner menuScan = new Scanner(System.in);
		IFTPclient ftpC = new FTPclient();
		IMenu menu = new Menu(menuScan);
		IZyboTransmitter zbtr = new ZyboTransmitter();
		IMenuController menuCon = new MenuController(menu, zbtr, ftpC, host, port, user, pass, zyboHost, zyboPort);
		
	}
}
