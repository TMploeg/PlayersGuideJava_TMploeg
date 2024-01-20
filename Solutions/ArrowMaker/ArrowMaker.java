import java.util.Scanner;

public class ArrowMaker{
	public static void main(String[] args){
		ArrowHead arrowHead = getEnumValueInput(ArrowHead.class, "Please select an arrow head:");
		int shaftLength = getNumberInput("Please choose a shaft length (in centimeters):");
		Fletching fletching = getEnumValueInput(Fletching.class, "Please select a fletching:");
		
		Arrow arrow = new Arrow(arrowHead, shaftLength, fletching);
		double cost = calculateCost(arrow);
		
		System.out.println("Your arrow costs " + cost + "g");
	}
	
	private static <T extends Enum<T>> T getEnumValueInput(Class<T> enumClass, String message){
		Scanner scanner = new Scanner(System.in);
		T[] values = enumClass.getEnumConstants();
		
		while(true){
			System.out.print(message + " ");
			String input = scanner.nextLine();
			
			for(T value : values){
				if(toFirstCharacterUppercase(value.name()).toLowerCase().equals(input.toLowerCase())){
					return value;
				}
			}
			
			if(input.toLowerCase().equals("banana")){
				System.out.println("potassium");
			}
			else{
				String[] names = new String[values.length];
				
				for(int i = 0; i < values.length; i++){
					names[i] = toFirstCharacterUppercase(values[i].name());
				}
				
				System.out.println("'" + input + "' is not a valid input, available options: { " + String.join(", ", names) + " }");
			}
		}
	}
	
	private static int getNumberInput(String message){
		Scanner scanner = new Scanner(System.in);
		
		while(true){
			System.out.print(message + " ");
			String numberInput = scanner.nextLine();
			
			try{
				int number = Integer.parseInt(numberInput);
				return number;
			}
			catch(NumberFormatException e){
				System.out.println("invalid input, length must be an integer");
			}
		}
	}
	
	private static double calculateCost(Arrow arrow){
		double cost = 0;
		
		cost += switch(arrow.getArrowHead()){
			case ArrowHead.STEEL -> 10;
			case ArrowHead.WOOD -> 3;
			case ArrowHead.OBSIDIAN -> 5;
			default -> throw new RuntimeException("should not reach, all enum values exhausted");
		};
		
		cost += arrow.getShaftLength() * 0.05;
		
		cost += switch(arrow.getFletching()){
			case Fletching.PLASTIC -> 10;
			case Fletching.TURKEY_FEATHERS -> 5;
			case Fletching.GOOSE_FEATHERS -> 3;
			default -> throw new RuntimeException("should not reach, all enum values exhausted");
		};
		
		return cost;
	}
	
	private static String toFirstCharacterUppercase(String string){
		if(string == null || string.length() == 0){
			return string;
		}
		
		String[] splitString = string.replace('_',' ').split(" ", 0);
		String[] result = new String[splitString.length];
		
		for(int i = 0; i < splitString.length; i++){
			if(splitString[i].length() == 0){
				result[i] = splitString[i];
			}
			else{
				String part = splitString[i];
				result[i] = part.substring(0,1).toUpperCase() + part.substring(1).toLowerCase();
			}
		}
		
		return String.join(" ", result);
	}
}