import java.util.Scanner;

public class KingdomScoreCalculator{
	public static void main(String[] args){
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("How many provinces do you have?");
		String provinceCountInput = scanner.nextLine();
		
		System.out.println("How many duchies do you have?");
		String duchyCountInput = scanner.nextLine();
		
		System.out.println("How many estates do you have?");
		String estateCountInput = scanner.nextLine();
		
		try{
			int provinceScore = Integer.parseInt(provinceCountInput) * 6;
			int duchyScore = Integer.parseInt(duchyCountInput) * 3;
			int estateScore = Integer.parseInt(estateCountInput);
			
			int totalScore = provinceScore + duchyScore + estateScore;
			
			System.out.println("Your kingdom scores " + totalScore + " points!");
		}
		catch(NumberFormatException e){
			System.err.println("inputs must be integer");
		}
	}
}