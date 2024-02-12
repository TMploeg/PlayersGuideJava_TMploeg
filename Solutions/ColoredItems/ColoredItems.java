import consolecolor.ConsoleColor;
import items.*;

public class ColoredItems{
	public static void main(String[] args){
		ColoredItem<Sword> blueSword = new ColoredItem<>(new Sword(), ConsoleColor.BLUE);
		blueSword.display();
		
		ColoredItem<Bow> redBow = new ColoredItem<>(new Bow(), ConsoleColor.RED);
		redBow.display();
		
		ColoredItem<Axe> greenAxe = new ColoredItem<>(new Axe(), ConsoleColor.GREEN);
		greenAxe.display();
	}
}