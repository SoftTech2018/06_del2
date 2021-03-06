package functionality;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ZyboTransmitter implements IZyboTransmitter {
	private PrintWriter out;
	private BufferedReader in;

	/**
	 * Sender kommando til Zybo-board for en specifik sensor
	 * @param command S = Start måling, B = Stop måling, T = Returner måling, I = Sæt samplingsinterval
	 * @param sensor Nummeret på sensoren der sendes kommando til
	 * @param parameter Kan være null - skal kun benyttes hvis samplingsinterval skal ændres
	 * @return Besked fra Zyboboardet
	 * @throws IOException
	 */
	public String sendCommand(String command, int sensor, String parameter) throws IOException{
		String message = command + " " + sensor;
		String reply = "Forbindelse ikke oprettet";
		switch (command.toUpperCase()) {
		case "S": { // Start måling
			out.println(message);
			reply = in.readLine();
		}
		break;
		case "B": { // Stop måling
			out.println(message);
			reply = in.readLine();
		}
		break;
		case "T": { // Returner måling
			out.println(message);
			reply = in.readLine().substring(2); // Antager svaret er noget lignende "T 1.54232"
		}
		break;
		case "I": { // Sæt samplingsinterval
			if (parameter == null)
				parameter = "12000";
			out.println(message + " " + parameter); // F.eks. "I 222 12000" betyder at sensor 222 skal ændre samplingsinterval til 120000
			reply = in.readLine();
		}
		break;
		default: {
			reply = "Forkert kommando modtaget";
		}
		}

		return reply;
	}

	/**
	 * Beder Zybo-boardet om en liste af aktive sensorer
	 * @return Liste af aktive sensorer
	 * @throws IOException
	 */
	public List<String> getSensors() throws IOException{
		out.println("LIST");
		String sensor = null;
		List<String> sensorList = new ArrayList<String>();
		while (!(sensor = in.readLine()).equalsIgnoreCase("END")){ // Antager at Zybo sender "END" når listen er færdig
			sensorList.add(sensor);
		}
		return sensorList;
	}

	@Override
	public void connected(BufferedReader in, PrintWriter out) {
		this.out = out;
		this.in = in;
	}
}
