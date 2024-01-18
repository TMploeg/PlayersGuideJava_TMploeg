import java.util.Scanner;

public class BreadOven{
	public static void main(String[] args){
		System.out.println("Bread is ready.");
		System.out.println("Who is the bread for?");
		
		Scanner scanner = new Scanner(System.in);
		String name = scanner.nextLine();
		
		System.out.println(name + " got bread.");
	}
}