package ftpMain;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public interface IFTPclient {
		
	void sendLine(String line, BufferedWriter bw) throws IOException;
	
	String readLine(BufferedReader br) throws IOException;
	
	String[] parsePASV(String pasvReturn);
	
	void getDataLIST(String host, int port) throws IOException;
	
	boolean getDataRETR(String host, int port, String savePath, String fileName, BufferedReader br) throws IOException;

	String executeCommand(String host, int port, String command, String user,
			String pass, String savePath, String fileName)
			throws NumberFormatException, IOException;

	ArrayList<String> getFileList();
	

}
