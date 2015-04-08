package ftpMain;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class FTPclient{
	
	private Socket socket = null;
	private BufferedReader reader = null;
	private BufferedWriter writer = null;
	private static boolean DEBUG = false;
	
	public FTPclient(){
		
	}
	
	public synchronized void connect(String host, int port, String user, String pass) throws IOException{
		if (socket != null){
			throw new IOException("FTP klienten er allerede forbundet til FTP-serveren");
		}
		
		socket = new Socket(host, port);
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		
		String response = readLine();
		if (!response.startsWith("220 ")){ //220 = Service ready for new user
			throw new IOException("FTP klienten modtog ukendt respons ved oprettelse af forbindelse til server: "+response);
		}
		
		sendLine("USER " + user);
		
		response = readLine();
		if (!response.startsWith("331 ")){ //331 = Brugernavn OK, venter på Password
			throw new IOException("FTP klienten modtog forkert respons fra server efter brugernavn blev indtastet: "+ response);
		}
		
		sendLine("PASS " + pass);
		
		response = readLine();
		if (!response.startsWith("230-User ")){
			throw new IOException("FTP klienten nægtet adgang med det givne password: "+ response);
		}
		
	}
	
	private void sendLine(String line) throws IOException {
		if (socket == null) {
			throw new IOException("FTP klienten har ingen forbindelse til serveren.");
		}
		try {
			writer.write(line + "\r\n");
			writer.flush();
			if (DEBUG) {
				System.out.println("> " + line);
			}
		} catch (IOException e) {
			socket = null;
			throw e;
		}
	}
	
	private String readLine() throws IOException {
		String line = reader.readLine();
		if (DEBUG) {
			System.out.println("< " + line);
		}
		return line;
	}
	
}