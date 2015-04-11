package jonTest;

import java.io.IOException;

import ftpMain.FTPclient;

public class FTPTestDriver {

	public static void main(String[] args) throws IOException {
		FTPclient ftp = new FTPclient();
		ftp.connectToServer("ftp.missekat.dk", 21, "missekat.dk", "jakobmedc");

	}

}
