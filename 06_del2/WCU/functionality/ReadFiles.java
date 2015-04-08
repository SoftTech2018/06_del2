package functionality;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ReadFiles implements IReadFiles {

	private File log;
	private File store;

	public ReadFiles() throws FileNotFoundException{
		log = new File("files/Log.txt");
		store = new File("files/store.txt");
		if (countLines() != 4) // Hvor mange linjer varer skal vi have?
			throw new FileNotFoundException();
	}

	private int countLines() throws FileNotFoundException{
		int i = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(store));) {
			while (br.readLine() != null){
				i++;
			}
		} catch (IOException e) {
			throw new FileNotFoundException();
		} 
		return i;
	}

	/* (non-Javadoc)
	 * @see functionality.IReadFiles#getProductName(int)
	 */
	@Override
	public String getProductName(int productNumber) throws FileNotFoundException{
		// Read store.txt, find produktnummeret og returner produktnavnet
		String out = null;
		String[] data = readFile(store);
		int p = 0;
		while (p < data.length){
			String[] line = data[p].split(",");
			if (productNumber == Integer.parseInt(line[0])){
				out = line[1];
			} 
			p++;
		}
		if (out.equals(null))
			out = "Produkt ej fundet!";
		return out;
	}

	/* (non-Javadoc)
	 * @see functionality.IReadFiles#writeLog(int, int, double, double)
	 */
	@Override
	public void writeLog(int oprNr, int vareNr, double afvejet) throws FileNotFoundException{
		// tilføj en linje til Log.txt
		//Dato, Tid, Operatørnummer, varenummer, afvejning (i kg), tilbage på lager (i kg)
		//I denne fil ændres eksisterende data aldrig – der ”appendes” altid. 
		try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(log, true)));){
			java.util.Date date = new java.util.Date();						
			out.println(new Timestamp(date.getTime())+","+oprNr+","+vareNr+","+afvejet+","+getProductInventory(vareNr));
		}catch (IOException e) {
			throw new FileNotFoundException();
		}
	}

	/* (non-Javadoc)
	 * @see functionality.IReadFiles#updProductInventory(int, double)
	 */
	@Override
	public boolean updProductInventory(int productNumber, double amountUsed) throws FileNotFoundException{
		// Ret i inventory i store.txt
		boolean updated = false;
		String[] data = readFile(store);
		int p = 0;
		while (p < data.length){
			String[] line = data[p].split(",");
			if (productNumber == Integer.parseInt(line[0])){
				data[p] = data[p].replaceAll(line[2], Double.toString(Double.parseDouble(line[2])-amountUsed));
				updated = true;
			} 
			p++;
		}
		if (updated)
			writeFile(store, data);
		return updated;
	}

	/* (non-Javadoc)
	 * @see functionality.IReadFiles#getProductInventory(int)
	 */
	@Override
	public double getProductInventory(int productNumber) throws FileNotFoundException{
		// find mængde på lager for det pågældende produkt
		double out = -1;
		String[] data = readFile(store);
		int p = 0;
		while (p < data.length){
			String[] line = data[p].split(",");
			if (productNumber == Integer.parseInt(line[0])){
				out = Double.parseDouble(line[2]);
			} 
			p++;
		}
		return out;
	}

	private String[] readFile(File fil) throws FileNotFoundException{
		List<String> data = new ArrayList<String>();
		String linje = null;
		try (BufferedReader br = new BufferedReader(new FileReader(store));){
			while ((linje = br.readLine()) != null){
				data.add(linje);
			}
		} catch (IOException e) {
			e.printStackTrace(); // Fjernes i endeligt program
			throw new FileNotFoundException();
		}
		return data.toArray(new String[data.size()]);
	}

	private void writeFile(File fil, String[] data) throws FileNotFoundException{
		try (BufferedWriter write = new BufferedWriter(new FileWriter(fil));){
			for (String s : data){
				write.write(s);
				write.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace(); // Fjernes i endelig kode
			throw new FileNotFoundException();
		}
	}

}
