package helpers.console;

public enum ConsoleColor{
	LIGHT_PURPLE(163, 118, 176),
	BLUE(56, 132, 232),
	CYAN(52, 227, 227),
	YELLOW(230, 223, 23),
	GREEN(27, 222, 27),
	WHITE(255, 255, 255);
	
	private int red;
	private int green;
	private int blue;
	
	private ConsoleColor(int red, int green, int blue){
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	public int getRed(){
		return red;
	}
	
	public int getGreen(){
		return green;
	}
	
	public int getBlue(){
		return blue;
	}
}