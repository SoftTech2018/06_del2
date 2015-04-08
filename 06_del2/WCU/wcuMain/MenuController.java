package wcuMain;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
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
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see wcuMain.IMenuController#action(java.lang.String)
	 */
	@Override
	public void action(String x) {
		this.state = this.state.changeState(menu,fileAccess,trans);
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
			action(getInput());		
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
			State changeState(IMenu menu, IReadFiles fileAccess, ITransmitter trans) {
				String input = null;
				int inputInt = 0;
				try{
					input = trans.RM20("Indtast bruger ID:","","");
					inputInt = Integer.parseUnsignedInt(input);
					
				} catch (NumberFormatException | IOException e) {
					menu.show("Forkert type input. Prøv igen");
					inputInt=-1;
				}
					
				if(input.toLowerCase().equals("q")){
					inputInt = -2;
				}
				
				switch(inputInt) {
				case -1:					
					return START;
				case -2:					
					return STOP;
				default:
					opr_nr = inputInt;
					return GET_PROD_NR;
				}
			}
		},
		GET_PROD_NR {
			@Override
			String desc() {
				return "Indtast varenummer:";
			}

			@Override
			State changeState(IMenu menu, IReadFiles fileAccess, ITransmitter trans) {
				String input = null, product;
				int inputInt = 0;
				try{
					input = trans.RM20("Indtast produkt ID:","","");
					if(input.toLowerCase().equals("q")){
						return STOP;
					} else {
						inputInt = Integer.parseUnsignedInt(input);
						product = fileAccess.getProductName(inputInt);
						if(trans.RM20("Bekræft produkt:",product,"?").equals(product)){
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
			State changeState(IMenu menu, IReadFiles fileAccess, ITransmitter trans) {
				String input = null, answer = "OK";
				try{
					input = trans.RM20("Påsæt beholder og kvittér:","OK","?");
					if(input.toLowerCase().equals("q")){
						return STOP;
					} else if (input.equals(answer)) {
						trans.T();
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
			State changeState(IMenu menu, IReadFiles fileAccess, ITransmitter trans) {
				String input = null, answer = "OK";
				try{
					input = trans.RM20("Afvej vare og kvittér:","OK","?");
					if(input.toLowerCase().equals("q")){
						return STOP;
					} else if (input.equals(answer)) {
						trans.P111("Efter vejning, kvittér med dør-knap");
						trans.startST(true);
						afvejning = Double.parseDouble(trans.listenST());
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
				return "Fjern beholder og kvittér herefter (y/n)";
			}
			@Override
			State changeState(IMenu menu, IReadFiles fileAccess, ITransmitter trans) {
				String input = null, answer = "OK";
				try{
					input = trans.RM20("Fjern beholder og kvittér:","OK","?");
					if(input.toLowerCase().equals("q")){
						return STOP;
					} else if (input.equals(answer)) {
						fileAccess.updProductInventory(vare_nr, afvejning);
						fileAccess.writeLog(opr_nr, vare_nr, afvejning);
						return STOP;
					} else {
						return REMOVE_CONTAINER;
					}
					
				} catch (NumberFormatException | IOException e) {
					menu.show("Forkert type input. Prøv igen");
					return REMOVE_CONTAINER	;
				}
			}
		},
		STOP {
			@Override
			String desc() {
				return null;
			}

			@Override
			State changeState(IMenu menu, IReadFiles fileAccess, ITransmitter trans) {
				return STOP;
			}
		};
		abstract State changeState(IMenu menu, IReadFiles fileAccess, ITransmitter trans);
		abstract String desc();
		int opr_nr,vare_nr;
		double afvejning;
	}




}
