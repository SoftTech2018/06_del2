package ftpMain;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public interface IFTPclient {
	
	void connectToServerLIST(String host, int port, String user, String pass) throws IOException;
	
	void connectToServerRETR(String host, int port, String user, String pass, String savePath, String fileName) throws IOException;
	
	void sendLine(String line, BufferedWriter bw) throws IOException;
	
	String readLine(BufferedReader br) throws IOException;
	
	String[] parsePASV(String pasvReturn);
	
	void getDataLIST(String host, int port) throws IOException;
	
	void getDataRETR(String host, int port, String savePath, String fileName) throws IOException;
	
	void recievePacket() throws IOException;
	
	void fileExists(String host, int port, String user, String pass, String fileName) throws IOException;

}
