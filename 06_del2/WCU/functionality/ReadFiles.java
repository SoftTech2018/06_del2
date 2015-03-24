package functionality;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadFiles {

	private File log;
	private File store;

	public ReadFiles(){
		log = new File("/files/Log.txt");
		store = new File("/files.txt/store.txt");
	}

	public String getProductName(int productNumber) throws FileNotFoundException{
		// Read store.txt, find produktnummeret og returner produktnavnet
		Scanner scan = null;
		try {
			scan = new Scanner(store);
			int p =0;
			do {
				p = scan.nextInt();
			} while (p != productNumber);
		} finally {
			scan.close();
		}
		return null;
	}

	public void writeLog(String line){
		// tilføj en linje til Log.txt
	}

	public void updProductInventory(int productNumber, double amountUsed){
		// Ret i inventory i store.txt
	}

	public double getProductInventory(int productNumber){
		// find mængde på lager for det pågældende produkt
		return 0;
	}

}
