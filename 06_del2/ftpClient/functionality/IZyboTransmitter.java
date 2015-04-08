package functionality;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public interface IZyboTransmitter {

	String sendCommand(String input, int sensor, String sampling) throws IOException;

	void connected(BufferedReader in, PrintWriter out);

}
