public class ForeachLoops{
	public static void main(String[] args){
		int[] array = { 4, 51, -7, 13, -99, 15, -8, 45, 90 };
		
		System.out.println(findSmallestValue(array));
		System.out.println(findAverageValue(array));
	}
	
	private static int findSmallestValue(int[] array){
		if(array == null){
			throw new NullPointerException("array must not be null");
		}
		if(array.length <= 0){
			throw new RuntimeException("array must contain at least one element");
		}
		
		int currentSmallest = Integer.MAX_VALUE; // Start higher than anything in the array.
		for(int value : array){
			if(value < currentSmallest){
				currentSmallest = value;
			}
		}
		
		return currentSmallest;
	}
	
	private static double findAverageValue(int[] array){
		if(array == null){
			throw new NullPointerException("array must not be null");
		}
		if(array.length <= 0){
			throw new RuntimeException("array must contain at least one element");
		}
		
		int total = 0;
		for (int value : array){
			total += value;
		}
		
		return (double)total / array.length;
	}
}