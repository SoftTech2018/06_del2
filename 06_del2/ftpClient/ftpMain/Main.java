package ftpMain;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		Scanner menuScan = new Scanner(System.in);
		
		IMenu menu = new Menu(menuScan);
	}

}
