package ftpMain;

import java.io.IOException;

public interface IFTPclient {
	
	void connectToServer(String host, int port, String user, String pass) throws IOException;
	
	/**
	 * Downloader/kopierer fil fra FTP-serveren og ned på harddisken.
	 * @param chooseFile - String, som indeholder filens navn og som indtastes af brugeren.
	 * @param save - String, som indeholder hvor på computeren filen skal gemmes.
	 */
	void downloadFile(String chooseFile, String save);
	
	/**
	 * Returnerer hvilke filer der ligger på FTP-serveren og udprinter listen i consollen.
	 */
	void getList();
	
	void sendLine(String line) throws IOException;
	
	String readLine() throws IOException;
	
	String[] parsePASV(String pasvReturn);
	
	void getData(String host, int port) throws IOException;
	
	void recievePacket() throws IOException;

}
