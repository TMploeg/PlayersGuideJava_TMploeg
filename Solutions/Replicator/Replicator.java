import java.util.Scanner;

public class Replicator{
	public static void main(String[] args){
		int arrayLength = 5;
		
		int[] inputNumbers = new int[arrayLength];
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Please input " + arrayLength + " numbers.");
		for(int i = 0; i < inputNumbers.length; i++) {
			while(true){	
				String nrInput = scanner.nextLine();
				
				try{
					int number = Integer.parseInt(nrInput);
					inputNumbers[i] = number;
					break;
				}
				catch(NumberFormatException e){
					System.err.println("invalid input");
				}
			}
		}
		
		int[] duplicateNumbers = new int[arrayLength];
		for(int i = 0; i < arrayLength; i++){
			duplicateNumbers[i] = inputNumbers[i];
		}
		
		System.out.println("numbers:");
		for(int i = 0; i < arrayLength; i++){
			System.out.println("input: " + inputNumbers[i] + " - replicated: " + duplicateNumbers[i]);
		}
	}
}