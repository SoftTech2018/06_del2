package functionality;

import java.io.File;
import java.util.ArrayList;

public class FTPFunc {
	
	// Hent en liste over filer på FTP serveren
	public ArrayList<String> getDir(){
		return new ArrayList<String>();
	}
	
	public boolean getFile(String fileName, String path){
		File fil = new File(path); // Her gemmes filen
		// Her hentes filen og skrives til fil
		return false; // true = overførsel var en succes
	}

}
