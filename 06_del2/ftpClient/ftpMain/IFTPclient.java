package ftpMain;

import java.io.IOException;
import java.util.ArrayList;

public interface IFTPclient {
	
	/**
	 * Udfører og sender en kommando til en FTP-server (LIST eller RETR)
	 * @param host
	 * @param port
	 * @param command
	 * @param user
	 * @param pass
	 * @param savePath Kan være null hvis command er LIST
	 * @param fileName Kan være null hvis command er LIST
	 * @return Besked hvorvidt kommandoen blev udført
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	String executeCommand(String host, int port, String command, String user,
			String pass, String savePath, String fileName)
			throws NumberFormatException, IOException;

	/**
	 * Hent filliste. Kan benyttes efter at executeCommand er kørt med LIST kommando.
	 * @return
	 */
	ArrayList<String> getFileList();
}