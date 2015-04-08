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
		connect(host, port);
	}
	
	public void connect(String host, int port){
		try (Socket	socket = new Socket(host, port);
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));){
			trans.connected(in, out);
			this.state = State.START;
			start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see wcuMain.IMenuController#action(java.lang.String)
	 */
	@Override
	public void action() {
		this.state = this.state.changeState(menu,fileAccess,trans,this);
	}

	/* (non-Javadoc)
	 * @see wcuMain.IMenuController#getInput()
	 */
	@Override
	public String getInput(){
		try{
			return menu.ask();
		}
		catch(NumberFormatException e){
			System.out.println("Du er en tosse - prøv igen");
			return getInput();
		}
	}

	/* (non-Javadoc)
	 * @see wcuMain.IMenuController#start()
	 */
	@Override
	public void start(){
		menu.show("Velkommen!");
		loop();
	}

	/* (non-Javadoc)
	 * @see wcuMain.IMenuController#loop()
	 */
	@Override
	public void loop(){
		do{
			menu.show(state.desc());
			action();		
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
			State changeState(IMenu menu, IReadFiles fileAccess, ITransmitter trans, IMenuController Imc) {
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
						Imc.setOprID(inputInt);
						return GET_PROD_NR;
					} else {
						return START;
					}
				} catch (NumberFormatException | IOException e) {
					menu.show("Forkert type input. Prøv igen");
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
			State changeState(IMenu menu, IReadFiles fileAccess, ITransmitter trans, IMenuController Imc) {
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
							Imc.setVareID(inputInt);
							return SET_CONTAINER;
						} else {
							return GET_PROD_NR;
						}
					}
				} catch (NumberFormatException | IOException e) {
					menu.show("Forkert type input. Prøv igen");
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
			State changeState(IMenu menu, IReadFiles fileAccess, ITransmitter trans, IMenuController Imc) {
				String input = null, answer = "OK";
				try{
					input = trans.RM20("Påsæt beholder, kvittér:","OK","?");
					System.out.println(input);
					if(input.toLowerCase().equals("q")){
						return STOP;
					} else if (input.equals(answer)) {
						Imc.setTara(Double.parseDouble(trans.T()));
						return ADD_PRODUCT;
					} else {
						return SET_CONTAINER;
					}					
				} catch (NumberFormatException | IOException e) {
					menu.show("Forkert type input. Prøv igen");
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
			State changeState(IMenu menu, IReadFiles fileAccess, ITransmitter trans, IMenuController Imc) {
				String input = null, answer = "OK";
				try{
					input = trans.RM20("Afvej vare og kvittér:","OK","?");
					if(input.toLowerCase().equals("q")){
						return STOP;
					} else if (input.equals(answer)) {
						trans.P111("Efter vejning, kvittér med dør-knap");
						trans.startST(true);
						Imc.setAfvejning(Double.parseDouble(trans.listenST()));
						trans.startST(false);
						return REMOVE_CONTAINER;
					} else {
						return ADD_PRODUCT;
					}
					
				} catch (NumberFormatException | IOException e) {
					menu.show("Forkert type input. Prøv igen");
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
			State changeState(IMenu menu, IReadFiles fileAccess, ITransmitter trans, IMenuController Imc) {
				String input = null, answer = "OK";
				try{
					input = trans.RM20("Fjern beholder, kvittér:","OK","?");
					if(input.toLowerCase().equals("q")){
						return STOP;
					} else if (input.equals(answer)) {
						fileAccess.updProductInventory(Imc.getVareID(), Imc.getAfvejning());
						fileAccess.writeLog(Imc.getOprID(), Imc.getVareID(), Imc.getTara(), Imc.getAfvejning());
						return RESTART;
					} else {
						return REMOVE_CONTAINER;
					}
					
				} catch (NumberFormatException | IOException e) {
					menu.show("Forkert type input. Prøv igen");
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
			State changeState(IMenu menu, IReadFiles fileAccess, ITransmitter trans, IMenuController Imc) {
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
					menu.show("Forkert type input. Prøv igen");
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
			State changeState(IMenu menu, IReadFiles fileAccess, ITransmitter trans, IMenuController Imc) {
				return STOP;
			}
		};
		abstract State changeState(IMenu menu, IReadFiles fileAccess, ITransmitter trans, IMenuController Imc);
		abstract String desc();		
	}

	public int getOprID(){
		return opr_nr;
	}
	
	public void setOprID(int id){
		this.opr_nr=id;
	}
	
	public int getVareID(){
		return vare_nr;
	}
	
	public void setVareID(int id){
		this.vare_nr=id;
	}
	
	public double getAfvejning(){
		return afvejning;
	}
	
	public void setAfvejning(double afvejning){
		this.afvejning=afvejning;
	}
	
	public double getTara(){
		return tara;
	}
	
	public void setTara(double tara){
		this.tara=tara;
	}
}
