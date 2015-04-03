package wcuMain;



public class MenuController {
	
	private State state, prev_state;
	private Menu menu;

	public MenuController(Menu menu) {
		this.menu = menu;
		this.state = State.START;
		this.prev_state=null;
	}

	public void action(int x) {
		this.prev_state=this.state;
		this.state = this.state.changeState(x);
	}
	
	public int getInput(){
		try{
			int input = Integer.parseInt(menu.ask());
			return input;
		}
		catch(NumberFormatException e){
			System.out.println("Du er en tosse - prøv igen");
			return getInput();
		}
	}
	
	public void start(){
		menu.show("Velkommen! Indtast venligst operatørnummer");
		
		
		do{
		int input;
		
		}
		while(!state.equals(State.STOP));
	}
	
	
	
	public enum State {
		START {
			@Override
			State changeState(int x) {
				switch(x) {
				case 11:
					// TODO: do something
					System.out.println("I am now changing from state " + this + " with int argument x = " + x);
					return GET_PROD_NR;
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
		GET_PROD_NR {

			@Override
			State changeState(int x) {
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
			State changeState(int x) {
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
			State changeState(int x) {
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
			State changeState(int x) {
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
			State changeState(int x) {
				// TODO: do something
				System.out.println("I am now changing from state " + this + " with int argument x = " + x);
				return STOP;
			}
		},
		STOP {

			@Override
			State changeState(int x) {
				// TODO: do something
				System.out.println("I am now changing from state " + this + " with int argument x = " + x);
				return STOP;
			}
		};
		abstract State changeState(int x);
	}
	
	


}
