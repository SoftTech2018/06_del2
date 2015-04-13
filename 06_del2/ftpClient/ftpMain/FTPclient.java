package ftpMain;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

public class FTPclient implements IFTPclient{
	
	
	private static boolean DEBUG = true;
	private boolean fileExists = false;
	
	public FTPclient(){
		
	}
	
	public synchronized void connectToServerLIST(String host, int port, String user, String pass) throws IOException{
		
		Socket socket = new Socket(host, port);
		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		
		String response = readLine(br);
		if (!response.startsWith("220 ")){ //220 = Service ready for new user
			throw new IOException("FTP klienten modtog ukendt respons ved oprettelse af forbindelse til server: "+response);
		}
		
		sendLine("USER " + user, bw);
		
		response = readLine(br);
		
		if (!response.startsWith("331 ")){ //331 = Brugernavn OK, venter p� Password
			throw new IOException("FTP klienten modtog forkert respons fra server efter brugernavn blev indtastet: "+ response);
		}
		
		sendLine("PASS " + pass, bw);
		
		response = readLine(br);
		
		if (!response.startsWith("230-User ")){
			throw new IOException("FTP klienten ingen  adgang med det givne password: "+ response);
		}
		readLine(br);
		
		sendLine("PASV", bw);

		String[] p = parsePASV(readLine(br));
		sendLine("LIST", bw);
		
		getDataLIST(p[0], Integer.parseInt(p[1]));
		
		sendLine("QUIT", bw);
		readLine(br);
		
		socket.close();	
		
	}
	
	public synchronized void connectToServerRETR(String host, int port, String user, String pass, String savePath, String fileName) throws IOException{
		fileExists = false;
		fileExists(host, port, user, pass, fileName);
		
		if(fileExists){
		Socket socket = new Socket(host, port);
		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		
		String response = readLine(br);
		if (!response.startsWith("220 ")){ //220 = Service ready for new user
			throw new IOException("FTP klienten modtog ukendt respons ved oprettelse af forbindelse til server: "+response);
		}
		
		sendLine("USER " + user, bw);
		
		response = readLine(br);
		
		if (!response.startsWith("331 ")){ //331 = Brugernavn OK, venter p� Password
			throw new IOException("FTP klienten modtog forkert respons fra server efter brugernavn blev indtastet: "+ response);
		}
		
		sendLine("PASS " + pass, bw);
		
		response = readLine(br);
		
		if (!response.startsWith("230-User ")){
			throw new IOException("FTP klienten ingen  adgang med det givne password: "+ response);
		}
		readLine(br);
		
		sendLine("TYPE I", bw);
		readLine(br);
		
		sendLine("PASV", bw);
		
		String[] p = parsePASV(readLine(br));
		
		sendLine("RETR license.txt", bw);
		
		getDataRETR(p[0], Integer.parseInt(p[1]), savePath, fileName);
		
		sendLine("QUIT", bw);
		readLine(br);
		
		socket.close();		
		} else {
			System.out.println("******************");
			System.out.println("Filen findes ikke");
			System.out.println("******************");
		}
	}
	
	
	public void sendLine(String line, BufferedWriter bw) throws IOException {
		try {
			bw.write(line + "\r\n");
			bw.flush();
			if (DEBUG) {
				System.out.println("> " + line);
			}
		} catch (IOException e) {
			throw e;
		}
	}
	
	public String readLine(BufferedReader br) throws IOException {
		String line = br.readLine();
		if (DEBUG) {
			System.out.println("< " + line);
		}
		return line;
	}
	
	// Modtager et PASV reply fra en server (f.eks. "227 Entering Passive Mode (195,47,247,238,249,136).") og returnerer ip adressen + portnummeret
	// I ovenstående tilfælde ip = 195.47.247.238, portnummer = 249*256+136. 
	public String[] parsePASV(String pasvReturn){
		int start = 0, end = 0;
		while (pasvReturn.charAt(start) != '('){
			start++;
		}
		end = start +1;
		while (pasvReturn.charAt(end) != ')'){
			end++;
		}
		String[] temp = pasvReturn.substring(start+1, end).split(",");
		String ip = temp[0] + "." + temp[1] + "." + temp[2] + "." + temp[3];
		int port = Integer.parseInt(temp[4]) * 256 + Integer.parseInt(temp[5]);
		if (DEBUG){
			System.out.println("PASV IP: " + ip);
			System.out.println("PASV Port: " + port);	
		}
		return new String[]{ip, Integer.toString(port)};
	}
	
	public void getDataLIST(String host, int port) throws IOException{

		Socket datasocket = new Socket(host, port);
		BufferedReader br = new BufferedReader(new InputStreamReader(datasocket.getInputStream()));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(datasocket.getOutputStream()));
		
		if (DEBUG)
			System.out.println("Tilsluttet Datagram: " + datasocket.isConnected());
		
		System.out.println("**************Start***************");
        
        String line = null;

        while ((line = br.readLine()) != null) {
            System.out.println(line);
            fileExists = true;
        }
        
        System.out.println("**************Slut****************");
   
        datasocket.close();
	}
	
	public void fileExists(String host, int port, String user, String pass, String fileName) throws IOException{

		Socket socket = new Socket(host, port);
		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		
		String response = readLine(br);
		if (!response.startsWith("220 ")){ //220 = Service ready for new user
			throw new IOException("FTP klienten modtog ukendt respons ved oprettelse af forbindelse til server: "+response);
		}
		
		sendLine("USER " + user, bw);
		
		response = readLine(br);
		
		if (!response.startsWith("331 ")){ //331 = Brugernavn OK, venter p� Password
			throw new IOException("FTP klienten modtog forkert respons fra server efter brugernavn blev indtastet: "+ response);
		}
		
		sendLine("PASS " + pass, bw);
		
		response = readLine(br);
		
		if (!response.startsWith("230-User ")){
			throw new IOException("FTP klienten ingen  adgang med det givne password: "+ response);
		}
		readLine(br);
		
		sendLine("PASV", bw);

		String[] p = parsePASV(readLine(br));
		sendLine("LIST "+fileName, bw);
		
		getDataLIST(p[0], Integer.parseInt(p[1]));
		
		sendLine("QUIT", bw);
		readLine(br);
		
		socket.close();	
		
		
	}
	
	public void getDataRETR(String host, int port, String savePath, String fileName) throws IOException{

		Socket datasocket = new Socket(host, port);
		
		InputStream inputStream = datasocket.getInputStream();
		 
		FileOutputStream outputStream = new FileOutputStream(savePath+fileName);
//      FileOutputStream outputStream = new FileOutputStream("/Users/JacobWorckJepsen/Desktop/test/"+"license.txt");
        
        System.out.println("*****Fil downloades - vent*****");
        
        int BufferStoerrelse = 4096;
        byte[] buffer = new byte[BufferStoerrelse];
        int bytesRead = -1;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        outputStream.close();
        inputStream.close();
        datasocket.close();
	}
	
	public void recievePacket() throws IOException{
		byte[] buf = new byte[2048];
		DatagramPacket packet = new DatagramPacket(buf, buf.length);
//        ds.send(packet);
//        if (DEBUG)
//			System.out.println("Sendt packet");
        
        packet = new DatagramPacket(buf, buf.length);
//        ds.receive(packet);
        if (DEBUG)
			System.out.println("Modtaget packet");
        String received = new String(packet.getData(), 0, packet.getLength());
        System.out.println("< Modtaget: " + received);
	}
	
}