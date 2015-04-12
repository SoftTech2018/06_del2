package wcuMain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import functionality.IReadFiles;
import functionality.ITransmitter;



public class MenuController implements IMenuController {

	private State state;
	private IMenu menu;
	private IReadFiles fileAccess;
	private ITransmitter trans;
	private int opr_nr,vare_nr;
	private double afvejning,tara;

	public MenuController(IMenu menu, IReadFiles rf, String host, int port, ITransmitter trans) {
		this.menu = menu;
		this.fileAccess = rf;
		this.trans = trans;
		this.state = State.START;
		connect(host, port);
	}

	@Override
	public void connect(String host, int port){
		try (Socket	socket = new Socket(host, port);
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));){
			trans.connected(in, out);			
			start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see wcuMain.IMenuController#start()
	 */
	@Override
	public void start(){
		menu.show("Velkommen!");
		do{
			menu.show(state.desc());
			this.state = this.state.changeState(menu,fileAccess,trans,this);		
		}
		while(!state.equals(State.STOP));
	}

	public enum State {
		START {
			@Override
			String desc(){				
				return "Indtast operatørnummer: "; 
			}
			@Override
			State changeState(IMenu menu, IReadFiles fileAccess, ITransmitter trans, MenuController mc) {
				String input = null,name;
				int inputInt = 0;
				try{
					input = trans.RM20("Tast bruger ID (10-16):","","");
					System.out.println(input);
					if(input.toLowerCase().equals("q")){
						return STOP;
					}
					inputInt = Integer.parseUnsignedInt(input);
					name = fileAccess.getOpr(inputInt);
					input = trans.RM20("Korrekt bruger:",name,"?");
					if(input.equals(name)){
						mc.setOprID(inputInt);
						return GET_PROD_NR;
					} else {
						return START;
					}
				} catch (NumberFormatException | IOException e) {
					try {
						trans.P111("Forkert type input. Prøv igen");
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					return START;
				}				
			}
		},
		GET_PROD_NR {
			@Override
			String desc() {
				return "Indtast varenummer:";
			}

			@Override
			State changeState(IMenu menu, IReadFiles fileAccess, ITransmitter trans, MenuController mc) {
				String input = null, product;
				int inputInt = 0;
				try{
					input = trans.RM20("Tast produkt ID (1-9):","","");
					if(input.toLowerCase().equals("q")){
						return STOP;
					} else {
						inputInt = Integer.parseUnsignedInt(input);
						product = fileAccess.getProductName(inputInt);
						if(trans.RM20("Bekræft produkt:",product,"?").equals(product)){
							mc.setVareID(inputInt);
							return SET_CONTAINER;
						} else {
							return GET_PROD_NR;
						}
					}
				} catch (NumberFormatException | IOException e) {
					try {
						trans.P111("Forkert type input. Prøv igen");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					return GET_PROD_NR;
				}				
			}
		},
		SET_CONTAINER {
			@Override
			String desc() {
				return "Påsæt beholder og kvittér herefter (y/n)";
			}

			@Override
			State changeState(IMenu menu, IReadFiles fileAccess, ITransmitter trans, MenuController mc) {
				String input = null, answer = "OK";
				try{
					input = trans.RM20("Påsæt beholder, kvittér:","OK","?");
					System.out.println(input);
					if(input.toLowerCase().equals("q")){
						return STOP;
					} else if (input.equals(answer)) {
						mc.setTara(Double.parseDouble(trans.T()));
						return ADD_PRODUCT;
					} else {
						return SET_CONTAINER;
					}					
				} catch (NumberFormatException | IOException e) {
					try {
						trans.P111("Forkert type input. Prøv igen");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					return SET_CONTAINER;
				}				
			}
		},
		ADD_PRODUCT {
			@Override
			String desc() {
				return "Afvej vare og kvittér herefter (y/n)";
			}
			@Override
			State changeState(IMenu menu, IReadFiles fileAccess, ITransmitter trans, MenuController mc) {
				String input = null, answer = "OK";
				try{
					input = trans.RM20("Afvej vare og kvittér:","OK","?");
					if(input.toLowerCase().equals("q")){
						return STOP;
					} else if (input.equals(answer)) {
						trans.P111("Efter vejning, kvittér med dør-knap");
						trans.startST(true);
						mc.setAfvejning(Double.parseDouble(trans.listenST()));
						trans.startST(false);
						trans.P111("");
						return REMOVE_CONTAINER;
					} else {
						return ADD_PRODUCT;
					}

				} catch (NumberFormatException | IOException e) {
					try {
						trans.P111("Forkert type input. Prøv igen");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					return ADD_PRODUCT;
				}
			}
		},
		REMOVE_CONTAINER {
			@Override
			String desc() {
				return "Fjern beholder, kvittér herefter (y/n)";
			}
			@Override
			State changeState(IMenu menu, IReadFiles fileAccess, ITransmitter trans, MenuController mc) {
				String input = null, answer = "OK";
				try{
					input = trans.RM20("Fjern beholder, kvittér:","OK","?");
					if(input.toLowerCase().equals("q")){
						return STOP;
					} else if (input.equals(answer)) {
						fileAccess.updProductInventory(mc.getVareID(), mc.getAfvejning());
						fileAccess.writeLog(mc.getOprID(), mc.getVareID(), mc.getTara(), mc.getAfvejning());
						return RESTART;
					} else {
						return REMOVE_CONTAINER;
					}

				} catch (NumberFormatException | IOException e) {
					try {
						trans.P111("Forkert type input. Prøv igen");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					return REMOVE_CONTAINER	;
				}
			}
		},
		RESTART {

			@Override
			String desc() {
				return null;
			}

			@Override
			State changeState(IMenu menu, IReadFiles fileAccess, ITransmitter trans, MenuController mc) {
				String input = null, answer = "OK";
				try{
					input = trans.RM20("Foretag ny vejning?","OK","");
					if(input.toLowerCase().equals("q")){
						return STOP;
					} else if (input.equals(answer)) {
						return GET_PROD_NR;
					} else {
						return STOP;
					}

				} catch (NumberFormatException | IOException e) {
					try {
						trans.P111("Forkert type input. Prøv igen");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					return RESTART;
				}
			}			
		},
		STOP {
			@Override
			String desc() {
				return null;
			}

			@Override
			State changeState(IMenu menu, IReadFiles fileAccess, ITransmitter trans, MenuController mc) {
				return STOP;
			}
		};
		abstract State changeState(IMenu menu, IReadFiles fileAccess, ITransmitter trans, MenuController mc);
		abstract String desc();		
	}

	private int getOprID(){
		return opr_nr;
	}

	private void setOprID(int id){
		this.opr_nr=id;
	}

	private int getVareID(){
		return vare_nr;
	}

	private void setVareID(int id){
		this.vare_nr=id;
	}

	private double getAfvejning(){
		return afvejning;
	}

	private void setAfvejning(double afvejning){
		this.afvejning=afvejning;
	}

	private double getTara(){
		return tara;
	}

	private void setTara(double tara){
		this.tara=tara;
	}
}
