package functionality;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadFiles {

	private File log;
	private File store;
	private int logLines;

	public ReadFiles() throws FileNotFoundException{
		log = new File("/files/Log.txt");
		store = new File("/files.txt/store.txt");
		logLines = countLines();
	}

	private int countLines() throws FileNotFoundException{
		Scanner scan = null;
		int i = 0;
		try {
			scan = new Scanner(store);
			while (scan.hasNextLine()){
				i++;
			}
		} finally {
			scan.close();
		}
		return i;
	}

	public String getProductName(int productNumber) throws FileNotFoundException{
		// Read store.txt, find produktnummeret og returner produktnavnet
		Scanner scan = null;
		String out = null;
		try {
			scan = new Scanner(store);
			scan.useDelimiter(",");
			while (scan.hasNext()){
				String temp = scan.next();
				if (Integer.valueOf(temp) == productNumber){
					out = scan.next();
					break;
				} else {
					scan.nextLine(); // hop til næste linie?
				}
			}
		} finally {
			scan.close();
		}
		return out;
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
