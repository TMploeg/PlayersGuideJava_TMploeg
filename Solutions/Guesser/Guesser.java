import java.util.Scanner;

public class Guesser{
	public static void main(String[] args){
		int min = 0;
		int max = 100;
		
		int target = getNumberInput("User 1, enter a number between " + min + " and " + max + ":", min, max);
		clearTerminal();
		
		System.out.println("User 2, guess the number");
		
		while(true){
			int guess = getNumberInput("What is you guess?", null, null);
			
			if(guess > target){
				System.out.println("too high");
			}
			else if(guess < target){
				System.out.println("too low");
			}
			else{
				break;
			}
		}
		
		System.out.println("You guessed the number!");
	}
	
	private static int getNumberInput(String message, Integer min, Integer max){				
		if((min != null && max != null) && min > max){
			throw new IllegalArgumentException("min must be smaller than or equal to max (min: " + min + ", max: " + max + ")");
		}
		
		int number;
		Scanner scanner = new Scanner(System.in);
		
		while(true){
			if(message != null && message.length() > 0){
				System.out.print(message + " ");
			}
			String numberInput = scanner.nextLine();
			
			try{
				number = Integer.parseInt(numberInput);
				
				if(min != null && number < min){
					printError("number must be greater than or equal to " + min);
				}
				else if(max != null && number > max){
					printError("number must be smaller than or equal to " + max);
				}
				else{
					break;
				}
			}
			catch(NumberFormatException e){
				printError("invalid input");
			}
		}
		
		return number;
	}
	
	private static void clearTerminal(){
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
	
	private static void printError(String message){
		String ansiReset = "\u001B[0m";
		String ansiRed = "\u001B[31m";
		System.err.println(ansiRed + message + ansiReset);
	}
}