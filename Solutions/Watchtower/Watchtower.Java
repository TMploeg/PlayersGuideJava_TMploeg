import java.util.Scanner;

public class Watchtower{
	public static void main(String[] args){
		System.out.println("Please enter enemy position.");
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("x: ");
		String xPosInput = scanner.nextLine();
		
		System.out.print("y: ");
		String yPosInput = scanner.nextLine();
		
		try{
			int xPos = Integer.parseInt(xPosInput);
			int yPos = Integer.parseInt(yPosInput);
			
			String xPosText = "";
			String yPosText = "";
			
			if(xPos < 0){
				xPosText = "west";
			}
			else if(xPos > 0){
				xPosText = "east";
			}
			
			if(yPos < 0){
				yPosText = "south";
			}
			else if(yPos > 0){
				yPosText = "north";
			}
			
			System.out.print("The enemy is ");
			if(xPosText.length() > 0 || yPosText.length() > 0){
				System.out.println("to the " + yPosText + xPosText + "!");
			}
			else{
				System.out.println("here!");
			}
			
		}
		catch(NumberFormatException e){
			System.err.println("position must be integer");
		}
	}
}