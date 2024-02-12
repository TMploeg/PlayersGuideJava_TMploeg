package items;

import consolecolor.ConsoleColor;

public class ColoredItem<TItem>{
	private static final String ANSI_RESET = "\u001B[0m";
	
	private TItem item;
	private ConsoleColor color;
	
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
	
	public void setColor(ConsoleColor color){
		this.color = color;
	}
	
	public void display(){
		System.out.println(color.getCode() + item + ANSI_RESET);
	}
}