import java.util.Scanner;

public class AskForNumber{
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	
	private static Scanner _scanner = new Scanner(System.in);

	public static void main(String[] args){
		int n1 = askForNumber("give number");
		int n2 = askForNumberInRange("give number between -24 and 33",-24,33);
		
		System.out.println(n1);
		System.out.println(n2);
	}
	
	private static int askForNumber(String message){
		System.out.print(message + " ");
		
		return getNumberInput();
	}
	
	private static int askForNumberInRange(String message, int min, int max){
		if(min > max){
			throw new IllegalArgumentException("min must be smaller than or equal to max");
		}
		
		System.out.print(message + " ");
		
		while(true){
			int number = getNumberInput();
			
			if(number < min || number > max){
				String errMessage = "number must be between " + min + " and " + max;
				System.out.println(ANSI_YELLOW + errMessage + ANSI_RESET);
			}
			else{
				return number;
			}
		}
	}
	
	private static int getNumberInput(){
		while(true){
			System.out.print(ANSI_BLUE);
			String input = _scanner.nextLine();
			System.out.print(ANSI_RESET);
			
			try{
				int number = Integer.parseInt(input);
				return number;
			}
			catch(NumberFormatException e){
				System.err.println(ANSI_RED + "invalid input" + ANSI_RESET);
			}
		}	
	}
}