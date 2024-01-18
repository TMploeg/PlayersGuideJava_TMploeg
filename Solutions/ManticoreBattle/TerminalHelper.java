import java.util.Scanner;

public class TerminalHelper{
	private static final String ANSI_RESET = "\u001B[0m";
	private static final String CLEAR_CONSOLE = "\033[H\033[2J";
	
	private static Scanner _scanner = new Scanner(System.in);
	
	public static int askForNumber(String message){
		System.out.print(message + " ");
		
		return getNumberInput();
	}
	
	public static int askForNumberInRange(String message, int min, int max){
		if(min > max){
			throw new IllegalArgumentException("min must be smaller than or equal to max");
		}
		
		System.out.print(message + " ");
		
		while(true){
			int number = getNumberInput();
			
			if(number < min || number > max){
				String errMessage = "number must be between " + min + " and " + max;
				printlnColor(errMessage, TerminalColor.YELLOW);
			}
			else{
				return number;
			}
		}
	}
	
	private static int getNumberInput(){
		while(true){
			System.out.print(getColorAnsiString(TerminalColor.BLUE));
			String input = _scanner.nextLine();
			System.out.print(ANSI_RESET);
			
			try{
				int number = Integer.parseInt(input);
				return number;
			}
			catch(NumberFormatException e){
				printlnColor("invalid input", TerminalColor.RED);
			}
		}	
	}
	
	public static void clearTerminal() {  
		System.out.print(CLEAR_CONSOLE);  
		System.out.flush();  
	}
	
	public static void printColor(String text, TerminalColor color){
		System.out.print(getColorAnsiString(color) + text + ANSI_RESET);
	}
	
	public static void printlnColor(String text, TerminalColor color){
		System.out.println(getColorAnsiString(color) + text + ANSI_RESET);
	}
	
	private static String getColorAnsiString(TerminalColor color){
		return switch(color){
			case BLACK -> "\u001B[30m";
			case RED -> "\u001B[31m";
			case GREEN -> "\u001B[32m";
			case YELLOW -> "\u001B[33m";
			case BLUE -> "\u001B[34m";
			case PURPLE -> "\u001B[35m";
			case CYAN -> "\u001B[36m";
			case WHITE -> "\u001B[37m";
		};
	}
}