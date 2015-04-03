package wcuMain;



public class MenuController {

	private State state, prev_state;
	private Menu menu;

	public MenuController(Menu menu) {
		this.menu = menu;
		this.state = State.START;
		this.prev_state=null;
	}

	public void action(String x) {
		this.prev_state=this.state;
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
		action("q");
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
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			State changeState(String x) {
				switch(x) {
				case 12:
					// TODO: do something
					System.out.println("I am now changing from state " + this + " with int argument x = " + x);
					return SET_CONTAINER;
				case -1:
					// TODO: do something
					System.out.println("I am now changing from state " + this + " with int argument x = " + x);
					return START;
				case -2:
					// TODO: do something
					System.out.println("I am now changing from state " + this + " with int argument x = " + x);
					return STOP;
				default:
					// TODO: do something
					System.out.println("I am now changing from state " + this + " with int argument x = " + x);
					return INVALIDSTATE;
				}
			}


		},
		SET_CONTAINER {
			@Override
			String desc() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			State changeState(String x) {
				switch(x) {
				case 13:
					// TODO: do something
					System.out.println("I am now changing from state " + this + " with int argument x = " + x);
					return ADD_PRODUCT;
				case -1:
					// TODO: do something
					System.out.println("I am now changing from state " + this + " with int argument x = " + x);
					return START;
				case -2:
					// TODO: do something
					System.out.println("I am now changing from state " + this + " with int argument x = " + x);
					return STOP;
				default:
					// TODO: do something
					System.out.println("I am now changing from state " + this + " with int argument x = " + x);
					return INVALIDSTATE;
				}
			}


		},
		ADD_PRODUCT {
			@Override
			String desc() {
				// TODO Auto-generated method stub
				return null;
			}
			@Override
			State changeState(String x) {
				switch(x) {
				case 14:
					// TODO: do something
					System.out.println("I am now changing from state " + this + " with int argument x = " + x);
					return REMOVE_CONTAINER;
				case -1:
					// TODO: do something
					System.out.println("I am now changing from state " + this + " with int argument x = " + x);
					return START;
				case -2:
					// TODO: do something
					System.out.println("I am now changing from state " + this + " with int argument x = " + x);
					return STOP;
				default:
					// TODO: do something
					System.out.println("I am now changing from state " + this + " with int argument x = " + x);
					return INVALIDSTATE;
				}
			}


		},
		REMOVE_CONTAINER {
			@Override
			String desc() {
				// TODO Auto-generated method stub
				return null;
			}
			@Override
			State changeState(String x) {
				switch(x) {
				case 14:
					// TODO: do something
					System.out.println("I am now changing from state " + this + " with int argument x = " + x);
					return STOP;
				case -1:
					// TODO: do something
					System.out.println("I am now changing from state " + this + " with int argument x = " + x);
					return START;
				case -2:
					// TODO: do something
					System.out.println("I am now changing from state " + this + " with int argument x = " + x);
					return STOP;
				default:
					// TODO: do something
					System.out.println("I am now changing from state " + this + " with int argument x = " + x);
					return INVALIDSTATE;
				}
			}


		},
		INVALIDSTATE {
			@Override
			String desc() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			State changeState(String x) {
				// TODO: do something
				System.out.println("I am now changing from state " + this + " with int argument x = " + x);
				return STOP;
			}


		},
		STOP {
			@Override
			String desc() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			State changeState(String x) {
				// TODO: do something
				System.out.println("I am now changing from state " + this + " with int argument x = " + x);
				return STOP;
			}


		};
		abstract State changeState(String x,Menu menu);
		abstract String desc();
	}




}
