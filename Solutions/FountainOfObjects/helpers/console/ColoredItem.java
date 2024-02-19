package helpers.console;

public abstract class ColoredItem {
	private ConsoleColor color;
	
	public ColoredItem(ConsoleColor color){
		this.color = color;
	}
	
	public ConsoleColor getColor(){
		return color;
	}
}