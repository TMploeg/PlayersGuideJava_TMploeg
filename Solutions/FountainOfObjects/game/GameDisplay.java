package game;

import map.*;
import helpers.console.*;
import commands.*;
import entities.*;
import java.util.Optional;

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
    boolean foundAdjacentPit = false;
    boolean foundAdjacentMaelstrom = false;
	boolean foundAdjacentAmarok = false;

    for (Room room : map.getCurrentRoom().getAllAdjacentRooms()) {
      if (!foundAdjacentPit && room.getType() == RoomType.PIT) {
        ConsoleHelper.printlnColor("You feel a draft of air.", ConsoleColor.DARK_RED);
        foundAdjacentPit = true;
      }
	  
	  Optional<Entity> entity = room.getEntity();
	  
	  if(!entity.isPresent()){
		  continue;
	  }
	  
	  foundAdjacentMaelstrom = foundAdjacentMaelstrom || entity.get() instanceof Maelstrom;
	  foundAdjacentAmarok = foundAdjacentAmarok || entity.get() instanceof Amarok;
	  
	  entity.get().showMessage(MessageType.AMBIANCE);
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