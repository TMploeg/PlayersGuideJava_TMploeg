import java.util.Scanner;
import java.awt.Toolkit;

public class CityDefense{

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLUE = "\u001B[34m";

	public static void main(String[] args){
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("target row: ");
		String rowInput = scanner.nextLine();
		
		System.out.print("target collumn: ");
		String collumnInput = scanner.nextLine();
		
		try{
			int row = Integer.parseInt(rowInput);
			int collumn = Integer.parseInt(collumnInput);
			
			System.out.println("deploy to:");
			System.out.println(ANSI_BLUE + "( "+ row + ", " + (collumn - 1) + " )");
			System.out.println("( "+ (row - 1) + ", " + collumn + " )");
			System.out.println("( "+ row + ", " + (collumn + 1) + " )");
			System.out.println("( "+ (row + 1) + ", " + collumn + " )" + ANSI_RESET);
			Toolkit.getDefaultToolkit().beep();
		}
		catch(NumberFormatException e){
			System.err.println("row and collumn must be integer");
		}
	}
}