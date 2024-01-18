import java.util.Scanner;

public class EggFarm{
	public static void main(String[] args){
		System.out.println("How many eggs were gathered? ");
		
		Scanner scanner = new Scanner(System.in);
		String countInput = scanner.nextLine();
		
		int nrOfSisters = 4;
		
		try {
			int count = Integer.parseInt(countInput);
			int remainder = count % 4;
			int eggsPerSister = (count - remainder) / 4;
			
			System.out.println("Each sister gets " + eggsPerSister + " eggs.");
			System.out.println("The duckbear gets " + remainder + " eggs.");
		}
		catch (NumberFormatException e) {
			System.err.println("number of eggs must be an integer");
		}
	}
}