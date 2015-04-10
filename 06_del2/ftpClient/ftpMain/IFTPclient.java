package ftpMain;

import java.io.IOException;

public interface IFTPclient {
	
	void connect(String host, int port, String user, String pass) throws IOException;
	
	void downloadFile();
	
	void getList();
	
	void sendLine(String line) throws IOException;
	
	String readLine() throws IOException;
	
	String[] parsePASV(String pasvReturn);
	
	void getData(String host, int port);
	
	void recievePacket();

}
