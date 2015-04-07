package functionality;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class Transmitter implements ITransmitter {
	
	private BufferedReader in;
	private PrintWriter out;
	
	public Transmitter(BufferedReader in, PrintWriter out){
		this.in = in;
		this.out = out;
	}
	
	
	/* (non-Javadoc)
	 * @see functionality.ITransmitter#RM20(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String RM20(String txt1, String txt2, String txt3){
		return null;
	}
	
	/* (non-Javadoc)
	 * @see functionality.ITransmitter#P111(java.lang.String)
	 */
	@Override
	public boolean P111(String txt){
		return false;
	}
	
	/* (non-Javadoc)
	 * @see functionality.ITransmitter#S()
	 */
	@Override
	public String S(){
		return null;
	}
	
	/* (non-Javadoc)
	 * @see functionality.ITransmitter#T()
	 */
	@Override
	public String T(){
		return null;
	}
	
	/* (non-Javadoc)
	 * @see functionality.ITransmitter#D()
	 */
	@Override
	public boolean D(){
		return false;
	}
	
	/* (non-Javadoc)
	 * @see functionality.ITransmitter#DW()
	 */
	@Override
	public boolean DW(){
		return false;
	}

}
