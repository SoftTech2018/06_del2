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
			System.out.println("UnknownHostException fejl");
			System.exit(1);
		} catch (IOException e) {
			System.out.println("IOException fejl");
			System.exit(1);
		}
	}

	/* (non-Javadoc)
	 * @see wcuMain.IMenuController#start()
	 */
	@Override
	public void start(){
		menu.show("Overvågning af vægtbetjening");
		do{
			menu.show("");
			menu.show(state.desc());
			this.state = this.state.changeState(menu,fileAccess,trans,this);		
		}
		while(!state.equals(State.STOP));
	}

	public enum State {
		START {
			@Override
			String desc(){				
				return "State: START"; 
			}
			@Override
			State changeState(IMenu menu, IReadFiles fileAccess, ITransmitter trans, MenuController mc) {
				String input = null,name,nameInput;
				int inputInt = 0;
				try{
					menu.show("Indtast operatørnummer:");
					input = trans.RM20("Tast bruger ID (10-16):","","");
					menu.show(input);
					if(input.toLowerCase().equals("q")){
						menu.show("Proceduren afbrudt af brugeren");
						trans.P111("");
						return STOP;
					}
					trans.P111("");
					inputInt = Integer.parseUnsignedInt(input);
					name = fileAccess.getOpr(inputInt);
					menu.show("Bruger valgt: "+name+". Er dette korrekt?");		
					nameInput = trans.RM20("Bekræft bruger:",name," ?");
					if (nameInput.toLowerCase().equals("q")){
						menu.show("Proceduren afbrudt af brugeren");
						trans.P111("");
						return STOP;
					}
					if(nameInput.equals(name)) {
						menu.show(nameInput+" bekræftet.");
						mc.setOprID(inputInt);
						return GET_PROD_NR;
					} else {
						menu.show(nameInput);
						menu.show("Forkert bruger. Prøv igen.");
						trans.P111("Forkert bruger. Prøv igen.");
						return START;
					}
				} catch (NumberFormatException e) {
					try {
						menu.show("Forkert input type. Prøv igen.");
						trans.P111("Forkert input type. Prøv igen.");
					} catch (IOException e1) {
						System.out.println("IOException fejl");
						System.exit(1);
					}
					return START;
				} catch (IOException e){
					try {
						menu.show("Bruger findes ikke. Prøv igen.");
						trans.P111("Bruger findes ikke. Prøv igen.");
					} catch (IOException e1) {
						System.out.println("IOException fejl");
						System.exit(1);
					}
					return START;
				}
			}
		},
		GET_PROD_NR {
			@Override
			String desc() {
				return "State: GET_PROD_NR";
			}
			@Override
			State changeState(IMenu menu, IReadFiles fileAccess, ITransmitter trans, MenuController mc) {
				String input = null, product, prodInput;
				int inputInt = 0;
				try{
					menu.show("Indtast varenummer:");
					input = trans.RM20("Tast produkt ID (1-9):","","");
					menu.show(input);
					if(input.toLowerCase().equals("q")){
						menu.show("Proceduren afbrudt af brugeren");
						trans.P111("");
						return STOP;
					}
					trans.P111("");
					inputInt = Integer.parseUnsignedInt(input);
					product = fileAccess.getProductName(inputInt);
					menu.show("Produkt valgt: "+product+". Er dette korrekt?");
					prodInput = trans.RM20("Bekræft produkt:",product," ?");
					if (prodInput.toLowerCase().equals("q")){
						menu.show("Proceduren afbrudt af brugeren");
						trans.P111("");
						return STOP;
					}
					if(prodInput.equals(product)){
						menu.show("Produkt bekræftet.");
						mc.setVareID(inputInt);
						return SET_CONTAINER;
					} else {
						menu.show("Forkert produkt. Prøv igen.");
						trans.P111("Forkert produkt. Prøv igen.");
						return GET_PROD_NR;
					}					
				} catch (NumberFormatException | IOException e) {
					try {
						menu.show("Fejl. Prøv igen.");
						trans.P111("Fejl. Prøv igen.");
					} catch (IOException e1) {
						System.out.println("IOException fejl");
						System.exit(1);
					}
					return GET_PROD_NR;
				}				
			}
		},
		SET_CONTAINER {
			@Override
			String desc() {
				return "State: SET_CONTAINER";
			}

			@Override
			State changeState(IMenu menu, IReadFiles fileAccess, ITransmitter trans, MenuController mc) {
				String input = null, answer = "OK";
				try{
					menu.show("Påsæt beholder og bekræft.");
					input = trans.RM20("Påsæt beholder, bekræft:","OK","?");
					menu.show(input);
					if(input.toLowerCase().equals("q")){
						menu.show("Proceduren afbrudt af brugeren");
						trans.P111("");
						return STOP;
					}
					trans.P111("");
					if (input.equals(answer)) {
						menu.show("Beholder påsat");
						mc.setTara(Double.parseDouble(trans.T()));
						menu.show("Vægt tareret: "+mc.getTara());
						return ADD_PRODUCT;
					} else {
						menu.show("Beholder ej påsat. Prøv igen.");
						trans.P111("Beholder ej påsat. Prøv igen.");
						return SET_CONTAINER;
					}					
				} catch (NumberFormatException | IOException e) {
					try {
						menu.show("Fejl. Prøv igen.");
						trans.P111("Fejl. Prøv igen.");
					} catch (IOException e1) {
						System.out.println("IOException fejl");
						System.exit(1);
					}
					return SET_CONTAINER;
				}				
			}
		},
		ADD_PRODUCT {
			@Override
			String desc() {
				return "State: ADD_PRODUCT";
			}
			@Override
			State changeState(IMenu menu, IReadFiles fileAccess, ITransmitter trans, MenuController mc) {
				String input = null, answer = "OK";
				try{
					menu.show("Afvej vare og bekræft.");
					input = trans.RM20("Afvej vare og bekræft:","OK","?");
					menu.show(input);
					if(input.toLowerCase().equals("q")){
						menu.show("Proceduren afbrudt af brugeren");
						trans.P111("");
						return STOP;
					}
					trans.P111("");
					if (input.equals(answer)) {
						menu.show("Afvej og kvittér med dør-knap");
						trans.P111("Afvej og kvittér med dør-knap");
						trans.startST(true);
						mc.setAfvejning(Double.parseDouble(trans.listenST()));
						trans.startST(false);
						menu.show(mc.getAfvejning()+" afvejet.");
						trans.P111("");
						return REMOVE_CONTAINER;
					} else {
						menu.show("Vare ej afvejet. Prøv igen.");
						trans.P111("Vare ej afvejet. Prøv igen.");
						return ADD_PRODUCT;
					}

				} catch (NumberFormatException | IOException e) {
					try {
						menu.show("Fejl. Prøv igen.");
						trans.P111("Fejl. Prøv igen.");
					} catch (IOException e1) {
						System.out.println("IOException fejl");
						System.exit(1);
					}
					return ADD_PRODUCT;
				}
			}
		},
		REMOVE_CONTAINER {
			@Override
			String desc() {
				return "State: REMOVE_CONTAINER";
			}
			@Override
			State changeState(IMenu menu, IReadFiles fileAccess, ITransmitter trans, MenuController mc) {
				String input = null, answer = "OK";
				try{
					menu.show("Fjern beholder og bekræft.");
					input = trans.RM20("Fjern beholder, kvittér:","OK","?");
					menu.show(input);
					if(input.toLowerCase().equals("q")){
						menu.show("Proceduren afbrudt af brugeren");
						trans.P111("");
						return STOP;
					}
					trans.P111("");
					if (input.equals(answer)) {
						fileAccess.updProductInventory(mc.getVareID(), mc.getAfvejning());
						menu.show("Beholdning opdateret:");
						menu.show("Vare ID: "+mc.getVareID()+", Afvejning: "+mc.getAfvejning());
						fileAccess.writeLog(mc.getOprID(), mc.getVareID(), mc.getTara(), mc.getAfvejning());
						menu.show("Log skrevet:");
						menu.show("Operatør ID: "+mc.getOprID()+", Vare ID: "+mc.getVareID()+", Tara vægt: "+mc.getTara()+", Afvejning: "+mc.getAfvejning());
						return RESTART;
					} else {
						menu.show("Beholder ej fjernet. Prøv igen.");
						trans.P111("Beholder ej fjernet. Prøv igen");
						return REMOVE_CONTAINER;
					}

				} catch (NumberFormatException | IOException e) {
					try {
						menu.show("Fejl. Prøv igen.");
						trans.P111("Fejl. Prøv igen.");
					} catch (IOException e1) {
						System.out.println("IOException fejl");
						System.exit(1);
					}
					return REMOVE_CONTAINER	;
				}
			}
		},
		RESTART {

			@Override
			String desc() {
				return "State: RESTART";
			}

			@Override
			State changeState(IMenu menu, IReadFiles fileAccess, ITransmitter trans, MenuController mc) {
				String input = null, answer = "OK";
				try{
					menu.show("Foretag ny vejning?");
					input = trans.RM20("Foretag ny vejning?","OK","");
					menu.show(input);
					if(input.toLowerCase().equals("q")){
						menu.show("Proceduren afbrudt af brugeren");
						trans.P111("");
						return STOP;
					}
					trans.P111("");
					if (input.equals(answer)) {
						menu.show("Proceduren genstartes.");
						return GET_PROD_NR;
					} else {
						menu.show("Proceduren stoppes.");
						return STOP;
					}

				} catch (NumberFormatException | IOException e) {
					try {
						menu.show("Fejl. Prøv igen.");
						trans.P111("Fejl. Prøv igen.");
					} catch (IOException e1) {
						System.out.println("IOException fejl");
						System.exit(1);
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
