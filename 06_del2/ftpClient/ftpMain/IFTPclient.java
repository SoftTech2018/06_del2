package ftpMain;

import java.io.IOException;

public interface IFTPclient {
	
	void downloadFile(String chooseFile, String save);
	
	void getList();
	
	void sendLine(String line) throws IOException;
	
	String readLine() throws IOException;
	
	String[] parsePASV(String pasvReturn);
	
	void getData(String host, int port) throws IOException;
	
	void recievePacket() throws IOException;

}
