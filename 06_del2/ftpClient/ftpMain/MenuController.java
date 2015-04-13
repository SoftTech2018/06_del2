package ftpMain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import functionality.IZyboTransmitter;

public class MenuController implements IMenuController{

	private IMenu menu;
	private IZyboTransmitter zbtr;
	private IFTPclient ftpC;
	private boolean run=true;
	
	public MenuController(IMenu menu, IZyboTransmitter zbtr, IFTPclient ftpC, String host, int port) throws NumberFormatException, IOException{ 
		this.menu = menu;
		this.zbtr = zbtr;
		this.ftpC = ftpC;
		connectZybo(host, port);
	}
	
	public void connectZybo(String host, int port){
		try (Socket	socket = new Socket(host, port);
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));){
			zbtr.connected(in, out);
			choice();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			menu.udskriv("Kan ikke forbinde til host");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void choice() throws NumberFormatException, IOException {
		while (run) {
			switch (menu.showMenu()) {
			case "1":
				ftpC.connectToServerLIST("ftp.missekat.dk", 21, "missekat.dk", "jakobmedc");
				break;
			case "2":
				
				ftpC.connectToServerRETR("ftp.missekat.dk", 21, "missekat.dk", "jakobmedc");
				break;
			case "3":
				String input = menu.sensorOverblik();
				if (input.equals("e")) {
					choice();
				}
				else { 
					switch (input) {
					case "1":
						specificSensor(input);
						break;
					case "4":
						specificSensor(input);
						break;
					case "222":
						specificSensor(input);
						break;
					case "7":
						specificSensor(input);
						break;
					default:
						menu.udskriv("Forkert input, pr�v igen");
						choice();
					}
				}
				break;
			case "4":
				run=false;
				break;
			default:
			}	
		}
	}	
	
	public void specificSensor(String sensor) throws NumberFormatException, IOException { //Her skal der kaldes en metode i den socket-klasse der har kontakt til zybo-boardets sensorere
		String input = menu.sensorMenu();	
		if (input.equals("e")) {
			choice();
		}		
		if(input.equals("I")) {
			String sampling = menu.setSampling();
			menu.udskriv(zbtr.sendCommand(input, Integer.parseInt(sensor), sampling));
		} else {
			menu.udskriv(zbtr.sendCommand(input, Integer.parseInt(sensor), null));
		}
	}
	
	public String download(){
		return menu.downloadFile();
	}
	
	public String downloadDestination(){
		return menu.downloadDestination();
	}
	public void udskrivMenu(String string) {
		menu.udskriv(string);
	}
}
