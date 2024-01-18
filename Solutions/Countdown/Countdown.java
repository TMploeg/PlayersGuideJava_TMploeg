import java.lang.Math;

public class Countdown{
	public static void main(String[] args){
		countdown(0);
		System.out.println();
		
		countdown(1);
		System.out.println();
		
		countdown(-1);
		System.out.println();
		
		countdown(22);
		System.out.println();
		
		countdown(21);
		System.out.println();

		countdown(-21);
		System.out.println();
		
		countdown(10);
	}
	
	public static void countdown(int start){
		System.out.println(start);
		if(start != 0){		
			countdown(start - (start / Math.abs(start)));
		}
	}
}