package menu;

import java.util.Scanner;
import java.util.List;

public class Menu<TActionArgs>{
	private List<MenuOption<TActionArgs>> options;
	
	public Menu(List<MenuOption<TActionArgs>> options){
		if(options == null){
			throw new NullPointerException();
		}
		
		if(options.size() == 0){
			throw new IllegalArgumentException("Menu must contain at least one item");
		}
		
		this.options = options;
	}
	
	public void requestMenuSelection(){
		Scanner scanner = new Scanner(System.in);
		
		while(true){
			displayMenu();
			MenuOption option = getMenuOptionInput(scanner, "Please enter the nr of one of the items above: ");
			
			if(option != null){
				option.select();
				break;
			}
		}
	}
	
	private void displayMenu(){
		int nr = 1;
			
		for(MenuOption option : options){
			System.out.println(nr + ". " + option);
			nr++;
		}
	}
	
	private MenuOption getMenuOptionInput(Scanner scanner, String message){
		System.out.println(message + " ");
		
		String input = scanner.nextLine();
		
		if(isIntegerInRange(input, 1, options.size())){
			return options.get(Integer.parseInt(input) - 1);
		}
		
		System.out.println("invalid input");
		
		return null;
	}
	
	private static boolean equalsIgnoreCase(String s1, String s2){
		return s1.toLowerCase().equals(s2.toLowerCase());
	}
	
	private static boolean isIntegerInRange(String s, Integer min, Integer max){
		if(s == null || s.length() == 0){
			return false;
		}
		
		if(s.length() > max.toString().length() || s.length() < min.toString().length()){
			return false;
		}
		
		boolean minEqual = min.toString().length() == s.length();
		boolean maxEqual = max.toString().length() == s.length();		
		
		for(int i = 0; i < s.length(); i++){
			char current = s.charAt(i);
			
			if(!Character.isDigit(current)){
				return false;
			}
			
			if(minEqual){
				switch(Integer.valueOf(Character.compare(min.toString().charAt(i), current))){
					case Integer j when j < 0:
						minEqual = false;
						break;
					case Integer j when j > 0:
						return false;
					default:
						break;
				}
			}
			
			if(maxEqual){
				switch(Integer.valueOf(Character.compare(max.toString().charAt(i), current))){
					case Integer j when j > 0:
						maxEqual = false;
						break;
					case Integer j when j < 0:
						return false;
					default:
						break;
				}
			}
		}
		
		return true;
	}
}