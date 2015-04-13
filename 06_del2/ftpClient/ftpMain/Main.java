package ftpMain;

import java.io.IOException;
import java.util.Scanner;

import functionality.IZyboTransmitter;
import functionality.ZyboTransmitter;

public class Main {

	public static void main(String[] args) throws NumberFormatException, IOException {
		int port;
		String host, user, pass;
		
		if (args.length == 4) {
			host = args[0];
			port = Integer.parseInt(args[1]);
			user = args[2];
			pass = args[3];
		}
		else {
			port = 21;
			host = "ftp.missekat.dk";
			user = "missekat.dk";
			pass = "jakobmedc";
		}
		
		Scanner menuScan = new Scanner(System.in);
		
		IFTPclient ftpC = new FTPclient();
		IMenu menu = new Menu(menuScan);
		IZyboTransmitter zbtr = new ZyboTransmitter();
		IMenuController menuCon = new MenuController(menu, zbtr, ftpC, host, port, user, pass);
		
		
	}
}
