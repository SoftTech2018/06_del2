package functionality;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public interface IZyboTransmitter {

	/**
	 * Sender en kommando til Zyboboardet og modtager et svar retur
	 * @param input Hvilken kommando
	 * @param sensor 
	 * @param sampling Kan være null medmindre der er valgt at ændre samplingsintervallet
	 * @return Besked fra Zyboboardet
	 * @throws IOException
	 */
	String sendCommand(String input, int sensor, String sampling) throws IOException;

	/**
	 * Sætter adgangen til Zyboboardet
	 * @param in
	 * @param out
	 */
	void connected(BufferedReader in, PrintWriter out);

}
