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

public class Entity{
	
}

enum EntityType{
	PLAYER,
	MAELSTROM,
	AMAROK;
	
	private ConsoleColor entityColor;
	private Map<MessageType, String> messageMap;
	
	private EntityType(ConsoleColor entityColor, String deathMessage, String ambianceMessage, String interactMessage){
		this.entityColor = entityColor;
		
		messageMap = new HashMap<>();
		messageMap.put(MessageType.DEATH, deathMessage);
		messageMap.put(MessageType.AMBIANCE, ambianceMessage);
		messageMap.put(MessageType.INTERACT, interactMessage);
	}
	
	public String getMessage(MessageType type){
		if(!messageType.containsKey(type)){
			throw new RuntimeException("message type '" + type.toString() + "' not implemented");
		}
		
		return messageMap.get(type);
	}
}