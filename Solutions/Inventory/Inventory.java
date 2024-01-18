import java.util.Scanner;

public class Inventory{
	private static final String myName = "Yggdrasil";
	
	public static void main(String[] args){
		System.out.println("1. Rope");
		System.out.println("2. Torches");
		System.out.println("3. Climbing Equipment");
		System.out.println("4. Clean Water");
		System.out.println("5. Machete");
		System.out.println("6. Canoe");
		System.out.println("7. Food Supplies");
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Please item number.");
		String itemInput = scanner.nextLine();
		
		System.out.println("Please enter name.");
		String nameInput = scanner.nextLine();
		
		double cost = switch (itemInput){
			case "1" -> 10;
			case "2" -> 15;
			case "3" -> 25;
			case "4" -> 1;
			case "5" -> 20;
			case "6" -> 200;
			case "7" -> 1;
			default -> -1;
		};
		
		if(cost >= 0){
			if(nameInput != null && nameInput.equals(myName)){
				cost /= 2;
			}
			
			System.out.println("price: " + cost + " gold.");
		}
		else{
			System.err.println("invalid input");
		}
	}
}