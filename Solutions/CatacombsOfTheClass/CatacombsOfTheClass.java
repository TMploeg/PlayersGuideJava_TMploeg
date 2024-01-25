import java.util.Scanner;
import java.util.HashMap;

public class CatacombsOfTheClass{
	public static void main(String[] args){
		pointTest();
		colorTest();
		cardTest();
		doorTest();
		passwordValidatorTest();
	}
	
	public static void pointTest(){
		System.out.println("POINT TEST START");
		
		Point p1 = new Point(2,3);
		Point p2 = new Point(-4,0);
		
		System.out.println("Point 1: " + p1);
		System.out.println("Point 2: " + p2);
		
		System.out.println("POINT TEST END\n");
	}
	
	public static void colorTest(){
		System.out.println("COLOR TEST START");
		
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
		
		System.out.println("COLOR TEST END\n");
	}
	
	public static void cardTest(){
		System.out.println("CARD TEST START");
		
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
		
		System.out.println("CARD TEST END\n");
	}
	
	public static void doorTest(){
		System.out.println("DOOR TEST START");
		
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
					System.out.print("Please enter the passcode: ");
					door.unlock(scanner.nextLine());
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
		
		System.out.println("DOOR TEST END\n");
	}

	public static void passwordValidatorTest(){
		System.out.println("POINT TEST START");
		
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
		
		System.out.println("POINT TEST END\n");
	}
}