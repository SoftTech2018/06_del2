package ftpMain;



public class MenuController implements IMenuController{

	private IMenu menu;
	private IZyboTransmitter zbtr;

	
	
	
	public MenuController(IMenu menu, IZyboTransmitter zbtr) { 
		this.menu = menu;
		this.zbtr = zbtr;
		
	}
	
	public void choice() {
		
		switch (menu.showMenu()) {
		case "1":
			menu.list(); //Viser fil-liste på ftp server
			// Her skal der tilføjes en kommando til at kontakte ftp serveren
			break;
		case "2":
			menu.retrieve(); //Henter fil fra ftp server
			// Her skal der tilføjes en kommando til at kontakte ftp serveren
			break;
		case "3":
			String input = menu.sensorOverblik();
			if (input.equals("e")) {
				choice();
			}
			else {
			specificSensor(input);
			}
			break;
		case "4":
			//Afslut program
			break;
		default:
			System.out.println("Forkert indtastning - prøv igen!");
			choice();
		}	
	}
	

		
	
	public void specificSensor(String sensor) { //Her skal der kaldes en metode i den socket-klasse der har kontakt til zybo-boardets sensorere
			
		String input = menu.sensorMenu();
		
		if (input.equals("e")) {
			choice();
		}
		
		if(input.equals("1")) {
			String sampling = menu.setSampling();
			zbtr.sendCommand(input, Integer.parseInt(sensor), sampling);
		}
		else {
			zbtr.sendCommand(input, Integer.parseInt(sensor), null);
		}
		
	}


	
	
	//MenuController skal bruge en måde at modtage fil-listen på og videresende til Menu - evt et array?
}
