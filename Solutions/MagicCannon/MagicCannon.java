import java.util.Scanner;

public class MagicCannon{
	public static void main(String[] args){
		Scanner scanner = new Scanner(System.in);
		String exitKeyword = "exit";
		
		System.out.print("press ENTER to move to next shot, or type " + exitKeyword + " to stop.");
		
		int count = 0;
		while(true){
			System.out.print(" ");
			String input = scanner.nextLine();
			
			if(input.equals(exitKeyword)){
				break;
			}
			
			count++;
			if(count > 100){
				count = 1;
			}
			
			display(count);
		}
	}
	
	public static void display(int count){
		String ansiReset = "\u001B[0m";
		String ansiRed = "\u001B[31m";
		String ansiYellow = "\u001B[33m";
		String ansiBlue = "\u001B[34m";

		boolean isFire = count % 3 == 0;
		boolean isLightning = count % 5 == 0;
		
		String colorPrefix = null;
		String text = null;
		
		if(isFire){
			colorPrefix = ansiRed;
			text = "Fire";
		}
		if(isLightning){
			if(isFire){
				colorPrefix = ansiBlue;
				text += " and Lightning";
			}
			else{
				colorPrefix = ansiYellow;
				text = "Lightning";
			}
		}
		
		if(text == null){
			text = "Normal";
		}
		
		if(colorPrefix != null){
			System.out.print(colorPrefix);
		}
		
		System.out.print(count + ": " + text);
		
		if(colorPrefix != null){
			System.out.print(ansiReset);
		}
	}
}