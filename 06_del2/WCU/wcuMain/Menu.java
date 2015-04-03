package wcuMain;

import java.util.*;

public class Menu implements IMenu {
	
	Scanner menuScan = new Scanner(System.in);	
	
	/* (non-Javadoc)
	 * @see wcuMain.IMenu#ask()
	 */
	@Override
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
	
	/* (non-Javadoc)
	 * @see wcuMain.IMenu#showSTART()
	 */
	@Override
	public void showSTART(){
		System.out.println("Operatørnummer godkendt");
		System.out.println();
	}
	
	/* (non-Javadoc)
	 * @see wcuMain.IMenu#showGET_PROD_NR()
	 */
	@Override
	public void showGET_PROD_NR(){
		
	}
	
	/* (non-Javadoc)
	 * @see wcuMain.IMenu#showSET_CONTAINER(int)
	 */
	@Override
	public void showSET_CONTAINER(int nr){
		
	}
	
	/* (non-Javadoc)
	 * @see wcuMain.IMenu#showADD_PRODUCT(int)
	 */
	@Override
	public void showADD_PRODUCT(int nr){
		
	}
	
	/* (non-Javadoc)
	 * @see wcuMain.IMenu#showREMOVE_CONTAINER(int)
	 */
	@Override
	public void showREMOVE_CONTAINER(int nr){
		
	}
	
	/* (non-Javadoc)
	 * @see wcuMain.IMenu#show(java.lang.String)
	 */
	@Override
	public void show(String txt){
	System.out.println(txt);
	System.out.println();
	}
	
	
}
