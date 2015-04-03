package wcuMain;



public class MenuController {
	public enum State {
		START {
			@Override
			State changeState(int x) {
				switch(x) {
				case 11:
					// TODO: do something
					System.out.println("I am now changing from state " + this + " with int argument x = " + x);
					return STEP1;
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
					return STEP2;
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
			}},
			SET_CONTAINER {

				@Override
				State changeState(int x) {
					switch(x) {
					case 13:
						// TODO: do something
						System.out.println("I am now changing from state " + this + " with int argument x = " + x);
						return STEP3;
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
				}},
				ADD_PRODUCT {

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
					}},
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
						}},
						INVALIDSTATE {

							@Override
							State changeState(int x) {
								// TODO: do something
								System.out.println("I am now changing from state " + this + " with int argument x = " + x);
								return STOP;
							}},
							STOP {

								@Override
								State changeState(int x) {
									// TODO: do something
									System.out.println("I am now changing from state " + this + " with int argument x = " + x);
									return STOP;
								}};
								abstract State changeState(int x);
	}

	private State state, prev_state;

	public MenuController() {
		this.state = State.START;
		this.prev_state=null;
	}

	public void doSomething(int x) {
		this.prev_state=this.state;
		this.state = this.state.changeState(x);
	}
}
