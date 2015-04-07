package functionality;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Transmitter implements ITransmitter {
	
	private BufferedReader in;
	private PrintWriter out;
	
	@Override
	public void connected(BufferedReader in, PrintWriter out){
		this.in = in;
		this.out = out;
	}
	
	/* (non-Javadoc)
	 * @see functionality.ITransmitter#RM20(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String RM20(String txt1, String txt2, String txt3) throws IOException{
		out.println("RM20 8" + " \"" + txt1 + "\" \"" + txt2 + "\" \"" + txt3 + "\"" );
		String reply = in.readLine();
		String error = "ES";
		if (reply.equalsIgnoreCase("RM20 B")){
			return in.readLine().substring(7); // Skal muligvis være 6
		} else {
			return error;
		}
	}
	
	/* (non-Javadoc)
	 * @see functionality.ITransmitter#P111(java.lang.String)
	 */
	@Override
	public boolean P111(String txt) throws IOException{
		out.println(txt);
		if (in.readLine().equalsIgnoreCase("P111 A")){
			return true;
		} else {
			return false;
		}
	}
	
	/* (non-Javadoc)
	 * @see functionality.ITransmitter#S()
	 */
	@Override
	public String S() throws IOException{
		out.println("S");
		return in.readLine().substring(4);
	}
	
	/* (non-Javadoc)
	 * @see functionality.ITransmitter#T()
	 */
	@Override
	public String T() throws IOException{
		out.println("T");
		return in.readLine().substring(4);
	}
	
	/* (non-Javadoc)
	 * @see functionality.ITransmitter#D()
	 */
	@Override
	public boolean D(String txt) throws IOException{
		boolean output = false; 
		if (txt.length() < 8 ){
			out.println(txt);
			if (in.readLine().equalsIgnoreCase("D A")){
				output = true;
			}
		} else{
			output = false;
		}
		return output;
	}
	
	/* (non-Javadoc)
	 * @see functionality.ITransmitter#DW()
	 */
	@Override
	public boolean DW() throws IOException{
		out.println("DW");
		if (in.readLine().equalsIgnoreCase("DW A")){
			return true;
		} else {
			return false;
		}
	}
	
	public String ST() throws IOException{
		out.println("ST");
		return in.readLine(); // Muligvis find en substring!
		}
}
