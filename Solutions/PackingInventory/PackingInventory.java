import pack.*;
import items.*;

import java.util.Scanner;
import java.util.Set;

public class PackingInventory{
	public static void main(String[] args){
		for(Class c : InventoryItem.getAllItemTypes()){
			System.out.println(c.getName());
		}
		
		//Pack pack = new Pack(getPackSizeInput("How large is your pack?"));
	}
	
	private static PackSize getPackSizeInput(String message){
		Scanner scanner = new Scanner(System.in);
		
		PackSize[] pSizes = PackSize.values();
		
		String pSizesDisplayString = "{ ";
		
		for(int i = 0; i < pSizes.length; i++){
			if(i != 0){
				pSizesDisplayString += ", ";
			}
			
			pSizesDisplayString += toPascalCase(pSizes[i].toString());
		}
		
		pSizesDisplayString += " }";
		
		while(true){
			System.out.print(message + " ");
			
			String input = scanner.nextLine();
			
			for(PackSize pSize : pSizes){
				if(equalsIgnoreCase(pSize.toString(), input)){
					return pSize;
				}
			}
			
			System.out.println("'" + input + "' is not a valid value, valid values: " + pSizesDisplayString);
		}
	}
	
	private static boolean equalsIgnoreCase(String s1, String s2){
		return s1.toLowerCase().equals(s2.toLowerCase());
	}
	
	private static String toPascalCase(String... values){
		if(values == null){
			return null;
		}
		
		String result = "";
		
		for(String value : values){
			if(value == null || value.length() == 0){
				continue;
			}
			
			result += value.substring(0,1).toUpperCase();
			
			if(value.length() > 1){
				result += value.substring(1).toLowerCase();
			}
		}
		
		return result;
	}
}