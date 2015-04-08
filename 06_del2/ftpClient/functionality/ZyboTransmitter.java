package functionality;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ZyboTransmitter {
	private PrintWriter out;
	private BufferedReader in;
	
	public void setConnection(PrintWriter out, BufferedReader in){
		this.out = out;
		this.in = in;
	}
	
	public String sendCommand(String command, int sensor, String parameter) throws IOException{
		String message = command + " " + sensor;
		String reply = null;
		switch (command){
		case "S":{ // Start måling
			out.println(message);
			reply = in.readLine();
		}
		break;
		case "B":{ // Stop måling
			out.println(message);
			reply = in.readLine();
		}
		break;
		case "T":{ // Returner måling
			out.println(message);
			reply = in.readLine().substring(2); // Antager svaret er noget lignende "T 1.54232"
		}
		break;
		case "I":{ // Sæt samplingsinterval
			out.println(message + " " + parameter); // F.eks. "I 222 12000" betyder at sensor 222 skal ændre samplingsinterval til 120000
			reply = in.readLine();
		}
		break;
		default:{
			reply = "Forkert kommando modtaget";
		}
		}
		
		return reply;
	}

}
