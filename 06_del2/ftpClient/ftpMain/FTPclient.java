package ftpMain;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class FTPclient extends FTP{
	
	String server = "www.facebook.com";
	int port = 21;
	String user = "user";
	String pass = "password";
	
	
	FTPClient ftp = new FTPClient();
	
	public void spasser(){
		
		try {
			ftp.connect(server, port);
			ftp.login(user, pass);
			ftp.enterLocalPassiveMode();
			ftp.setFileType(BINARY_FILE_TYPE);
			
			String remoteFile1 = "/test/video.mp4";
			File downloadFile1 = new File("D:/Downloads/video.mp4");
			OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(downloadFile1));
			boolean success = ftp.retrieveFile(remoteFile1, outputStream1);
			outputStream1.close();
			
			if (success) {
				System.out.println("File #1 has been downloaded successfully.");
			}
			
		} catch (IOException e){
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			System.out.println("Sup");
		}
	}
	
}
