package items;

import consolecolor.ConsoleColor;
import java.lang.reflect.Method;

public class ColoredItem<TItem>{
	private static final String ANSI_RESET = "\u001B[0m";
	
	private final TItem item;
	private final ConsoleColor color;
	
	public ColoredItem(TItem item, ConsoleColor color){
		this.item = item;
		this.color = color;
	}
	
	public TItem getItem(){
		return item;
	}
	
	public ConsoleColor getColor(){
		return color;
	}
	
	public void display(){
		boolean hasToString = false;
		for(Method m : item.getClass().getDeclaredMethods()){
			if(m.getName().equals("toString")){
				hasToString = true;
				break;
			}
		}
		
		String displayString = hasToString ? item.toString() : item.getClass().getSimpleName();
		System.out.println(color.getCode() + displayString + ANSI_RESET);
	}
}