import java.util.Scanner;
import java.util.EnumSet;

public class SoupMaker{
	public static void main(String[] args){
		FoodType foodType = getEnumValueInput(FoodType.class, "What type of soup do you want to make?");
		Ingredient ingredient = getEnumValueInput(Ingredient.class, "What is the main ingredient in your soup?");
		Seasoning seasoning = getEnumValueInput(Seasoning.class, "What kind of seasoning do you want to use?");
		
		Soup soup = new Soup(foodType, ingredient, seasoning);
		
		displaySoup(soup);
	}
	
	private static <T extends Enum<T>> T getEnumValueInput(Class<T> enumClass, String message){
		Scanner scanner = new Scanner(System.in);
		T[] values = enumClass.getEnumConstants();
		
		while(true){
			System.out.print(message + " ");
			String input = scanner.nextLine();
			
			for(T value : values){
				if(value.name().toLowerCase().equals(input.toLowerCase())){
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
	
	private static void displaySoup(Soup soup){
		String soupString =
			toFirstCharacterUppercase(soup.getSeasoning().name()) + " " +
			toFirstCharacterUppercase(soup.getMainIngredient().name()) + " " +
			toFirstCharacterUppercase(soup.getType().name());
		
		System.out.println("You made '" + soupString + "'");
	}
	
	private static String toFirstCharacterUppercase(String string){
		if(string == null || string.length() == 0){
			return string;
		}
		
		return string.substring(0,1).toUpperCase() + string.substring(1).toLowerCase();
	}
}