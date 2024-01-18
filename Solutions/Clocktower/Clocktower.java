import java.util.Scanner;

public class Clocktower{
	public static void main(String[] args){
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please enter a number");
		String nrInput = scanner.nextLine();
		
		try{
			int number = Integer.parseInt(nrInput);
			if(number % 2 == 0){
				System.out.println("Tick");
			}
			else{
				System.out.println("Tock");
			}
		}
		catch(NumberFormatException e){
			System.err.println("input must be an integer");
		}
	}
}