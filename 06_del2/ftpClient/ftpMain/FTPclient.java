package ftpMain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class FTPclient{
	
	String server = "speedtest.tele2.net";
	int port = 21;
	
	public FTPclient(){
		
	}
	
	public void command(){
		try {
			Socket socket21 = new Socket("speedtest.tele2.net", 21);
			PrintWriter out = new PrintWriter(socket21.getOutputStream(),true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket21.getInputStream()));
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void data(){
		try {
			Socket socket20 = new Socket("speedtest.tele2.net", 20);
			PrintWriter out = new PrintWriter(socket20.getOutputStream(),true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket20.getInputStream()));
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	public void dataCommand(int port, String server){
//		spasser(localHost, 21);
//		if (true)
//			spasser(localHost, 20);
//	}
	
//	public void spasser(String server, int port){
//		
//		try {
//			ftp.connect(server, port);
//			ftp.login(user, pass);
//			ftp.enterLocalPassiveMode();
//			ftp.setFileType(BINARY_FILE_TYPE);
//			
//			String remoteFile1 = "/test/video.mp4";
//			File downloadFile1 = new File("D:/Downloads/video.mp4");
//			OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(downloadFile1));
//			boolean success = ftp.retrieveFile(remoteFile1, outputStream1);
//			outputStream1.close();
//			
//			if (success) {
//				System.out.println("File #1 has been downloaded successfully.");
//			}
//			
//		} catch (IOException e){
//			System.out.println("Error: " + e.getMessage());
//			e.printStackTrace();
//		} finally {
//			System.out.println("Sup");
//		}
//	}
	
}
