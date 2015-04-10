package ftpMain;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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

public class FTPclient{
	
	private Socket socket = null;
	private BufferedReader reader = null;
	private BufferedWriter writer = null;
	private static boolean DEBUG = true;
	DatagramSocket ds;
	
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
		
		if (!response.startsWith("331 ")){ //331 = Brugernavn OK, venter p� Password
			throw new IOException("FTP klienten modtog forkert respons fra server efter brugernavn blev indtastet: "+ response);
		}
		
		sendLine("PASS " + pass);
		
		response = readLine();
		
		if (!response.startsWith("230-User ")){
			throw new IOException("FTP klienten n�gtet adgang med det givne password: "+ response);
		}
		
//		sendLine("PASV");
//		readLine();
//		String[] test = parsePASV(readLine());
//		getData(test[0], Integer.parseInt(test[1]));
//		sendLine("LIST");
//		recievePacket();
//		readLine();
	}
	
	public void downloadFile(){
		String ftpUrl = "ftp://missekat.dk:jakobmedc@ftp.missekat.dk/wp-content/uploads/photo-gallery/selfie2.JPG;type=i";
        String host = "www.missekat.com";
        String user = "missekat.dk";
        String pass = "jakobmedc";
        String filePath = "/wp-content/uploads/photo-gallery/selfie2.JPG";
        String savePath = "C:/Users/JACOB/Desktop/selfie2.JPG";
 
        ftpUrl = String.format(ftpUrl, user, pass, host, filePath);
        System.out.println("URL: " + ftpUrl);
 
        try {
            URL url = new URL(ftpUrl);
            URLConnection conn = url.openConnection();
            InputStream inputStream = conn.getInputStream();
 
            FileOutputStream outputStream = new FileOutputStream(savePath);
            
            System.out.println("Fil downloades - vent venligst");
            
            int BufferStoerrelse = 4096;
            byte[] buffer = new byte[BufferStoerrelse];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
 
            outputStream.close();
            inputStream.close();
 
            System.out.println("Filen er downloaded");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
	}
	
	public void getList(){
		String ftpUrl = "ftp://missekat.dk:jakobmedc@ftp.missekat.dk/wp-content/uploads/photo-gallery;type=d";
        String host = "www.missekat.dk";
        String user = "missekat.dk";
        String pass = "jakobmedc";
        String dirPath = "wp-content/uploads/photo-gallery";
 
        ftpUrl = String.format(ftpUrl, user, pass, host, dirPath);
//        System.out.println("URL: " + ftpUrl);
 
        try {
            URL url = new URL(ftpUrl);
            URLConnection conn = url.openConnection();
            InputStream inputStream = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
 
            String line = null;
//            System.out.println("--- START ---");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
//            System.out.println("--- END ---");
 
            inputStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
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
	
	public void getData(String host, int port) throws IOException{
		ds = new DatagramSocket();
		ds.connect(InetAddress.getByName(host), port);
		
		if (DEBUG)
			System.out.println("Tilsluttet Datagram: " + ds.isConnected());
        
	}
	
	public void recievePacket() throws IOException{
		byte[] buf = new byte[2048];
		DatagramPacket packet = new DatagramPacket(buf, buf.length);
//        ds.send(packet);
//        if (DEBUG)
//			System.out.println("Sendt packet");
        
        packet = new DatagramPacket(buf, buf.length);
        ds.receive(packet);
        if (DEBUG)
			System.out.println("Modtaget packet");
        String received = new String(packet.getData(), 0, packet.getLength());
        System.out.println("< Modtaget: " + received);
	}
	
}