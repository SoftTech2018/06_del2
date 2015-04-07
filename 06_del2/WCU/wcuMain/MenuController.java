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
		this.state = this.state.changeState(x,menu,fileAccess);
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
			State changeState(String x, IMenu menu, IReadFiles fileAccess) {
				int input;
				if(x.toLowerCase().equals("q")){
					input = -2;
				} else {
					try{
						input = Integer.parseUnsignedInt(x);
					} catch (NumberFormatException e) {
						menu.show("Forkert type input. Prøv igen");
						input=-1;
					}
				}
				switch(input) {
				case -1:					
					return START;
				case -2:					
					return STOP;
				default:
					opr_nr = input;
					menu.showSTART();
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
			State changeState(String x,IMenu menu, IReadFiles fileAccess) {
				int input;
				if(x.toLowerCase()=="q"){
					input = -2;
				} else {
					try{
						input = Integer.parseUnsignedInt(x);
					} catch (NumberFormatException e) {
						menu.show("Forkert type input. Prøv igen");
						input=-1;
					}
				}
				switch(input) {
				case -1:					
					return GET_PROD_NR;
				case -2:					
					return STOP;
				default:
					vare_nr = input;
					menu.showGET_PROD_NR();
					return SET_CONTAINER;
				}
			}


		},
		SET_CONTAINER {
			@Override
			String desc() {
				return "Påsæt beholder og kvittér herefter (y/n)";
			}

			@Override
			State changeState(String x, IMenu menu, IReadFiles fileAccess) {
				String input = x.toLowerCase();
				switch(input) {
				case "y":
					
					return ADD_PRODUCT;
				case "q":
					return STOP;
				default:
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
			State changeState(String x, IMenu menu, IReadFiles fileAccess) {
				String input = x.toLowerCase();
				switch(input) {
				case "y":
					return REMOVE_CONTAINER;
				case "q":
					return STOP;
				default:
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
			State changeState(String x, IMenu menu, IReadFiles fileAccess) {
				String input = x.toLowerCase();
				switch(input) {
				case "y":
					return STOP;
				case "q":
					return STOP;
				default:
					return REMOVE_CONTAINER;
				}
			}
		},
		STOP {
			@Override
			String desc() {
				return null;
			}

			@Override
			State changeState(String x, IMenu menu, IReadFiles fileAccess) {
				return STOP;
			}
		};
		abstract State changeState(String x, IMenu menu, IReadFiles fileAccess);
		abstract String desc();
		int opr_nr,vare_nr;
	}




}
