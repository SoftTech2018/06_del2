package wcuMain;



public class MenuController {

	private State state;
	private Menu menu;

	public MenuController(Menu menu) {
		this.menu = menu;
		this.state = State.START;
	}

	public void action(String x) {
		this.state = this.state.changeState(x,menu);
	}

	public String getInput(){
		try{
			return menu.ask();
		}
		catch(NumberFormatException e){
			System.out.println("Du er en tosse - prøv igen");
			return getInput();
		}
	}

	public void start(){
		menu.show("Velkommen!");
		loop();
	}

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
			State changeState(String x, Menu menu) {
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
					return START;
				case -2:					
					return STOP;
				default:
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
			State changeState(String x,Menu menu) {
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
			State changeState(String x, Menu menu) {
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
			State changeState(String x, Menu menu) {
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
			State changeState(String x, Menu menu) {
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
			State changeState(String x, Menu menu) {
				return STOP;
			}
		};
		abstract State changeState(String x,Menu menu);
		abstract String desc();
	}




}
