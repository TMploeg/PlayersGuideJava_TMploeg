package entities.archive;

import map.Room;
import helpers.console.*;

public abstract class Entity extends ColoredItem implements Interactable {
  public Entity(ConsoleColor entityColor) {
	super(entityColor);
  }
  
  public void showMessage(MessageType type){
	String message = switch(type){
		case DEATH 		-> getDeathMessage();
		case AMBIANCE 	-> getAmbianceMessage();
		case INTERACT	-> getInteractMessage();
		default			-> throw new RuntimeException("unknown enum value");
	};
	
	ConsoleHelper.printlnColor(message, this.getColor());
  }
  
  protected abstract String getDeathMessage();
  
  protected abstract String getAmbianceMessage();
  
  protected abstract String getInteractMessage();
}