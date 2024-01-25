import java.util.Scanner;
import java.util.HashMap;
import point.Point;
import color.Color;
import card.*;
import door.Door;
import passwordvalidator.PasswordValidator;
import tictactoe.Game;

public class CatacombsOfTheClass{
	public static void main(String[] args){
		int choice = getMenuOptionInput(new String[]{
			"Point",
			"Color",
			"Card",
			"Door",
			"Password Validator",
			"Play Tic Tac Toe"
		});
		
		switch(choice){
			case 1:
				pointTest();
				break;
			case 2:
				colorTest();
				break;
			case 3:
				cardTest();
				break;
			case 4:
				doorTest();
				break;
			case 5:
				passwordValidatorTest();
				break;
			case 6:
				int nrOfGamesChoice = getMenuOptionInput(new String[]{
					"Best of 3",
					"Best of 5"
				});
				
				int nrOfGames = switch(nrOfGamesChoice){
					case 1 -> 3;
					case 2 -> 5;
					default -> throw new RuntimeException("invalid case");
				};
				
				Game game = new Game(nrOfGames);
				break;
		}
	}
	
	public static void pointTest(){
		Point p1 = new Point(2,3);
		Point p2 = new Point(-4,0);
		
		System.out.println("Point 1: " + p1);
		System.out.println("Point 2: " + p2);
	}
	
	public static void colorTest(){
		Color[] colors = {
			Color.WHITE,
			Color.BLUE,
			new Color(40,40,40),
			new Color("#21572f"),
			new Color("4b3e5c")
		};
		
		for(Color color : colors){
			System.out.println(color);
		}
	}
	
	public static void cardTest(){
		CardColor[] colors = CardColor.values();
		CardRank[] ranks  = CardRank.values();
		
		Card[] cards = new Card[colors.length * ranks.length];
		
		int index = 0;
		for(CardColor color : colors){
			for(CardRank rank : ranks){
				cards[index] = new Card(color, rank);
				index++;
			}
		}
		
		for(Card card : cards){
			System.out.println(card);
		}
	}
	
	public static void doorTest(){
		Scanner scanner = new Scanner(System.in);
		Door door = null;
		do{
			System.out.print("Please enter a passcode for the door: ");
			String passcode = scanner.nextLine();
			
			if(passcode == null || passcode.length() == 0){
				System.out.println("Passcode must have at least one character");
			}
			else{
				door = new Door(passcode);
			}
		}
		while(door == null);
		
		boolean running = true;
		while(running){
			System.out.print("What do you want to do? ");
			String input = scanner.nextLine();
			
			switch(input){
				case "open":
					door.open();
					break;
				case "close":
					door.close();
					break;
				case "unlock":
					door.unlock();
					break;
				case "lock":
					door.lock();
					break;
				case "change passcode":
					System.out.print("Please enter the old passcode: ");
					String oldPassCode = scanner.nextLine();
					System.out.print("Please enter the new passcode: ");
					String newPassCode = scanner.nextLine();
					
					door.changePasscode(oldPassCode, newPassCode);
					break;
				case "exit":
					running = false;
					break;
				default:
					System.out.println("invalid input");
			}
		}
	}

	public static void passwordValidatorTest(){
		PasswordValidator validator = new PasswordValidator();
		HashMap<String,String> passwordMap = new HashMap<>();
		passwordMap.put("too long","Hi2");
		passwordMap.put("too short","UnreasonablyLongPasswordWhichExceedsMaxCharacterLimit1");
		passwordMap.put("is null",null);
		passwordMap.put("is empty","");
		passwordMap.put("all upper","HELLOWORLD");
		passwordMap.put("all lower","helloworld");
		passwordMap.put("all digit","12456789");
		passwordMap.put("no upper","hello1world");
		passwordMap.put("no lower","HELLO1WORLD");
		passwordMap.put("no number","helloWORLD");
		passwordMap.put("has capital 'T'","Terminal1");
		passwordMap.put("has ampersand","Wrong1&");
		passwordMap.put("valid 1","GoodPassword1");
		passwordMap.put("valid 2","myClass55");
		passwordMap.put("valid 3","fjSFjkdFJsd3");
		
		for(String key : passwordMap.keySet()){
			System.out.println(passwordMap.get(key) + "(" + key + ") -> " + validator.isValidPassword(passwordMap.get(key)));
		}
	}
	
	private static int getMenuOptionInput(String[] options){
		for(int i = 0; i < options.length; i++){
			System.out.println((i + 1) + ". " + options[i]);
		}
		
		int choice = getNumberInputInRange("Please enter the number of the option you want:", 1, options.length);
		
		return choice;
	}
	
	public static int getNumberInputInRange(String message, int min, int max){
		Scanner scanner = new Scanner(System.in);
		
		while(true){
			System.out.print(message + " ");
			String numberInput = scanner.nextLine();
			
			if(!isNumber(numberInput)){
				System.out.println("error: '" + numberInput + "' is not a number.");
				continue;
			}
			
			boolean isInteger = isInteger(numberInput);
			int number = 0;
			
			if(isInteger){
				number = Integer.parseInt(numberInput);
			}
			
			if(!isInteger || (number < min || number > max)){
				System.out.println("error: '" + number + "' is out of range (min: '" + min + "', max: '" + max + "')");
				continue;
			}
			
			return number;
		}
	}
	
	private static boolean isNumber(String str){
		if(str == null){
			throw new NullPointerException("argument 'str' is null");
		}
		
		if(str.length() == 0){
			return false;
		}
		
		char[] splitChars = str.toCharArray();
		if(str.charAt(0) == '-'){
			if(str.length() == 1){
				return false;
			}
			
			splitChars = str.substring(1).toCharArray();
		}
		
		for(char c : splitChars){
			if(!Character.isDigit(c)){
				return false;
			}
		}
		
		return true;
	}
	
	private static boolean isInteger(String nrString){
		if(nrString == null){
			throw new NullPointerException("nrString cannot be null");
		}
		
		if(!isNumber(nrString)){
			return false;
		}
	
		boolean isPositive = true;
		String maxValueString = Integer.toString(Integer.MAX_VALUE);
		String absNrString = nrString;
		
		if(nrString.charAt(0) == '-'){
			isPositive = false;
			maxValueString = Integer.toString(Integer.MIN_VALUE * -1);
			absNrString = nrString.substring(1);
		}
		
		if(absNrString.length() != maxValueString.length()){
			return absNrString.length() < maxValueString.length();
		}
		
		for(int i = absNrString.length() - 1; i >=0; i--){
			if(absNrString.charAt(i) != maxValueString.charAt(i)){
				return absNrString.charAt(i) < maxValueString.charAt(i);
			}
		}
		
		return true;
	}
}