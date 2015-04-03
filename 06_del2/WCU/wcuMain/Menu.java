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
		System.out.println("Operatørnummer godkendt");
		System.out.println();
	}
	
	public void showGET_PROD_NR(int nr){
		
	}
	
	public void showSET_CONTAINER(int nr){
		
	}
	
	public void showADD_PRODUCT(int nr){
		
	}
	
	public void showREMOVE_CONTAINER(int nr){
		
	}
	
	public void show(String txt){
	System.out.println(txt);
	System.out.println();
	}
	
	
}
