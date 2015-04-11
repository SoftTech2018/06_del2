package ftpMain;

import java.io.IOException;

public interface IFTPclient {
	
	void connectToServer(String host, int port, String user, String pass) throws IOException;
	
	void downloadFile(String chooseFile);
	
	void getList();
	
	void sendLine(String line) throws IOException;
	
	String readLine() throws IOException;
	
	String[] parsePASV(String pasvReturn);
	
	void getData(String host, int port) throws IOException;
	
	void recievePacket() throws IOException;

}
