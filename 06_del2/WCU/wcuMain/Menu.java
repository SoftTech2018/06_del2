package wcuMain;

import java.util.*;

public class Menu {
	
	Scanner menuScan = new Scanner(System.in);
	
	
	public String ask() {
		String out;
		try {
			System.out.println("Tast her: ");
			out = menuScan.next();
			menuScan.nextLine();
		} catch (InputMismatchException e){
			menuScan.nextLine();
			System.out.println("Forkert input, prøv igen"); 
			out= ask();
		} 
		return out;
	}
	
	public void showSTART(){
		
	}
	
	public void showGET_PROD_NR(){
		
	}
	
	public void showSET_CONTAINER(){
		
	}
	
	public void showADD_PRODUCT(){
		
	}
	
	public void showREMOVE_CONTAINER(){
		
	}
	
	public void show(String txt){
	System.out.println(txt);	
	}
	
	
}
