import java.util.Scanner;

public class TriangleAreaCalculator{
	public static void main(String[] args){
		System.out.println("Please enter triangle measurements");
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Base: ");
		String baseInput = scanner.nextLine();
		
		System.out.print("Height: ");
		String heightInput = scanner.nextLine();
		
		try{	
			double base = Double.parseDouble(baseInput);
			double height = Double.parseDouble(heightInput);
			double area = base * height / 2;
			
			System.out.println(area);
		}
		catch(NumberFormatException e){
			System.err.println("measurements are not valid, base and height must be numbers");
		}
	}
}