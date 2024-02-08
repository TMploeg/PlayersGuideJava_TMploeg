import pack.*;
import items.*;
import menu.*;

import java.util.Scanner;
import java.util.ArrayList;
import java.lang.reflect.InvocationTargetException;

public class PackingInventory{
	private static Pack pack;
	private static boolean running = false;
	
	private static final Class[] itemClasses = {
		Arrow.class,
		Bow.class,
		Rope.class,
		FoodRations.class,
		Water.class,
		Sword.class
	};
	
	public static void main(String[] args){
		pack = new Pack(getPackSizeInput("How large is your pack?"));
		
		running = true;
		
		while(running){
			showPackInfo();
			
			System.out.println();
			
			showMenu();
			
			System.out.println();
		}
	}
	
	private static void showPackInfo(){
		System.out.println("Nr of items: " + pack.getItemCount());
		System.out.println("Weight: " + pack.getTotalWeight() + "/" + pack.getMaxWeight());
		System.out.println("Volume: " + pack.getTotalVolume() + "/" + pack.getMaxVolume());
	}
	
	private static void showMenu(){
		ArrayList<MenuOption<Class>> menuOptions = new ArrayList<MenuOption<Class>>();
		
		for(int i = 0; i < itemClasses.length; i++){
			String optionName = itemClasses[i].getSimpleName();
			
			MenuAction<Class> onSelect = cl -> {
				InventoryItem newItem;
				
				try{
					newItem = (InventoryItem)cl.getConstructor().newInstance();
				}
				catch(NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e){
					throw new RuntimeException("unknown error occured");
				}
				
				tryAddItem(newItem);
			};
			
			menuOptions.add(new MenuOption<Class>(optionName, itemClasses[i], onSelect)); 
		}
		
		menuOptions.add(new MenuOption<Class>("Exit", null, args -> running = false));
		
		Menu<Class> menu = new Menu<Class>(menuOptions);
		menu.requestMenuSelection();
	}
	
	private static void tryAddItem(InventoryItem item){
		if(pack.canAdd(item)){
			pack.add(item);
			System.out.println("Added '" + item.getClass().getSimpleName() + "' to pack.");
			
			return;
		}
		
		System.out.println("Could not add '" + item.getClass().getSimpleName() + "' to pack");
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