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
	
	public void connect(String host, int port){
		try (Socket	socket = new Socket(host, port);
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));){

			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String sendCommand(int sensor, String command, String parameter) throws IOException{
		String message = command + " " + sensor;
		switch (command){
		case "I":{ // Sæt samplingsinterval
			message = message + " " + parameter; // F.eks. "S 222 12000"
		}
		break;
		default:{
			// S = start med at måle
			// B = stop med at måle
			// T = returner vægt
		}
		}
		out.println(message);
		return in.readLine(); // Ok eller værdi af måling fra Zyboboardet
		
	}

}
