package game;

import map.*;
import helpers.console.*;
import commands.*;
import entities.*;
import java.util.Optional;
import java.util.List;

public class GameDisplay {
	private static final int TURN_SEPERATOR_LENGTH = 100;
	
	private Map map;
	private MapDisplay mapDisplay;
	
	public GameDisplay(Map map){
		if(map == null){
			throw new NullPointerException("map cannot be null");
		}
		
		this.map = map;
		this.mapDisplay = new MapDisplay(map);
	}
	
	public void displayLocation(boolean displayFullMap){
		if(displayFullMap){
			mapDisplay.displayMap();
			return;
		}
		
		displayInfo("You are in the room at location " + map.getCurrentRoom().getLocation());
	}
	
  public void displayRoom() {
    switch (map.getCurrentRoom().getType()) {
      case RoomType.ENTRANCE:
        ConsoleHelper.printlnColor(
            "You see light in this room coming from outside the cavern. This is the entrance.",
            ConsoleColor.YELLOW);
        break;

      case RoomType.FOUNTAIN:
        String message =
            (map.isFountainEnabled()
                ? "You hear the rushing waters from the Fountain of Objects. It has been"
                    + " reactivated!"
                : "You hear water dripping in this room. The Fountain of Objects is here!");

        ConsoleHelper.printlnColor(message, ConsoleColor.BLUE);
        break;
		
      case RoomType.PIT:
        ConsoleHelper.printlnColor("You fell into a pit.", ConsoleColor.DARK_RED);
		break;
		
      default:
        break;
    }
  }
  
  public void displayAdjacentRooms() {
    for (Room room : map.getCurrentRoom().getAllAdjacentRooms()) {
      if (room.getType() == RoomType.PIT) {
        ConsoleHelper.printlnColor("You feel a draft of air.", ConsoleColor.DARK_RED);
		break;
      }
    }
	
	displayAdjacentEntityIfExists(Maelstrom.class);
	displayAdjacentEntityIfExists(Amarok.class);
  }
  
  private <T extends Entity> void displayAdjacentEntityIfExists(Class<T> entityClass){
	List<Entity> adjacentSameTypeEntities = map.getCurrentRoom().getAdjacentEntities(entity -> entity.getClass() == entityClass);
	
	if(adjacentSameTypeEntities.size() > 0){
		adjacentSameTypeEntities.get(0).showMessage(MessageType.AMBIANCE);
	}
  }
  
  public void displayEntityInteraction(Entity entity){
	entity.showMessage(MessageType.INTERACT);  
  }
  
  public void displayEntityDeath(Entity entity){
	entity.showMessage(MessageType.DEATH);
  }
  
  public void displayCommands() {
    for (Command command : Command.values()) {
	  String text = "'" + command.getCommandText() + "': " + command.getDescription();
	  displayInfo(text);
    }
  }
  
  public void displayVictory(){
	ConsoleHelper.printlnColor("You Win!", ConsoleColor.GREEN);
  }
  
  public void displayDefeat(){
	ConsoleHelper.printlnColor("You Lose!", ConsoleColor.RED);  
  }
  
  public void displayInfo(String info){
	ConsoleHelper.printlnColor(info, ConsoleColor.LIGHT_GRAY);
  }
  
  public void displaySeperator() {
    int seperatorLength = TURN_SEPERATOR_LENGTH;

    for (int i = 0; i < seperatorLength; i++) {
      System.out.print("=");
    }

    System.out.println();
  }
}