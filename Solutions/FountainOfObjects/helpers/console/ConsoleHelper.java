package helpers.console;

public class ConsoleHelper{
	private static final String ANSI_RESET = "\033[0m";
	
	public static void printColor(String text, ConsoleColor color){
		System.out.print(getAnsiCode(color) + text + ANSI_RESET);
	}
	
	public static void printlnColor(String text, ConsoleColor color){
		System.out.println(getAnsiCode(color) + text + ANSI_RESET);
	}
	
	private static String getAnsiCode(ConsoleColor color){
		return "\033[38;2;" + color.getRed() + ";" + color.getGreen() + ";" + color.getBlue() + "m" ;
	}
}