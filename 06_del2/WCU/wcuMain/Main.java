package wcuMain;
import java.io.FileNotFoundException;

import functionality.IReadFiles;
import functionality.ITransmitter;
import functionality.ReadFiles;
import functionality.Transmitter;

import java.util.*;

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
		
		IReadFiles rf = null;
		IMenu menu = new Menu();

		try {

			rf = new ReadFiles();
			ITransmitter trans = new Transmitter();
			IMenuController menuCon = new MenuController(menu,rf, host, port, trans);
			
		} catch (FileNotFoundException e) {
			System.out.println("Noget i filbehandlingen gik grueligt galt :-( Kontakt udvikleren.");
			e.printStackTrace();
			System.exit(1);
		}
		System.exit(0);
	}
}
