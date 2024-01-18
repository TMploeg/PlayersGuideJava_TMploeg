import java.util.Scanner;

public class ThingNamer{
	public static void main(String[] args){
		System.out.println("What kind of thing are we talking about?");
		Scanner input = new Scanner(System.in);
		
		// the thing to be named
		String a = input.next();
		
		System.out.println("How would you describe it? Big? Azure? Tattered?");
		
		// a description of the thing
		String b = input.next();
		
		// cool sounding affix
		String c = " of Doom";
		
		// big number to give it a sense of power
		String d = "3000";
		
		System.out.println("The " + b + " " + a + c + " " + d + "!");
		
		/* other fixes:
		 * more descriptive variable names
		 * combine variables 'c' and 'd' into one as well as the space and exclamation mark
		 * combine the different text objects into a new variable before printing
		 */
	}
}