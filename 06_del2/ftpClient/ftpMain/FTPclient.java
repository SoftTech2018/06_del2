package ftpMain;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class FTPclient implements IFTPclient{
		
	private boolean DEBUG = false;
	private ArrayList<String> fileList;
	
	public void sendLine(String line, BufferedWriter bw) throws IOException {
			bw.write(line + "\r\n");
			bw.flush();
			if (DEBUG) 
				System.out.println("> " + line);
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
		fileList = new ArrayList<String>();
		try (Socket datasocket = new Socket(host, port);
			 BufferedReader br = new BufferedReader(new InputStreamReader(datasocket.getInputStream()));){
		if (DEBUG)
			System.out.println("Datasocket tilsluttet: " + datasocket.isConnected());
		fileList.add("**************Start***************");
        String line = null;
        while ((line = br.readLine()) != null) {
            fileList.add(line);
        }
        fileList.add("**************Slut****************");
		}
	}
	
	public boolean getDataRETR(String host, int port, String savePath, String fileName, BufferedReader br){
		File fil = new File(savePath + fileName);
		try ( 	Socket datasocket = new Socket(host, port);
				InputStream inputStream = datasocket.getInputStream();
				FileOutputStream outputStream = new FileOutputStream(fil);){	
			String reply = readLine(br);
			if (!reply.substring(0, 3).equalsIgnoreCase("150"))
				throw new FileNotFoundException();
			int BufferStoerrelse = 4096;
			byte[] buffer = new byte[BufferStoerrelse];
			int bytesRead = -1;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			return true;
		} catch (FileNotFoundException e){
			fil.delete();
			return false;
		} catch (IOException e1) {
			fil.delete();
			return false;
		}
	}
	
	@Override
	public String executeCommand(String host, int port, String command, String user, String pass, String savePath, String fileName) throws NumberFormatException, IOException{
		String reply;
		try (Socket socket = new Socket(host, port);
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));){
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
		
		switch (command.toUpperCase()){
		case "LIST":
			sendLine("LIST", bw);
			getDataLIST(p[0], Integer.parseInt(p[1]));
			reply = "Listen er hentet.";
			break;
		case "RETR":
			sendLine("TYPE I", bw);
			readLine(br);
			sendLine("RETR " + fileName, bw);
			if (getDataRETR(p[0], Integer.parseInt(p[1]), savePath, fileName, br))
				reply = "Filen blev hentet.";
			else
				reply = "Filen fandtes ikke på serveren.";
			break;
			default:
				reply = "Kommando ej forstået.";
		}
		sendLine("QUIT", bw);
		readLine(br);
		
	}
		return reply;
	}
	
	@Override
	public ArrayList<String> getFileList(){
		return fileList;
	}	
}