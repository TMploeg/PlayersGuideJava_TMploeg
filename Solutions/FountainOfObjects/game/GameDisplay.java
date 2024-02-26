package game;

import map.*;
import helpers.console.*;
import commands.*;
import entities.*;
import java.util.Optional;
import java.util.List;

public class GameDisplay {
	private static final int TURN_SEPERATOR_LENGTH = 100;
	
	private static final ConsoleColor ENTRANCE_COLOR = ConsoleColor.YELLOW;
	private static final ConsoleColor FOUNTAIN_COLOR = ConsoleColor.BLUE;
	private static final ConsoleColor PIT_COLOR = ConsoleColor.DARK_RED;
	private static final ConsoleColor VICTORY_COLOR = ConsoleColor.GREEN;
	private static final ConsoleColor DEFEAT_COLOR = ConsoleColor.RED;
	private static final ConsoleColor INFO_COLOR = ConsoleColor.LIGHT_GRAY;
	private static final ConsoleColor INTRO_COLOR = ConsoleColor.LIGHT_PURPLE;
	
	private Map map;
	private MapDisplay mapDisplay;
	private EntityService entityService;
	
	public GameDisplay(Map map){
		if(map == null){
			throw new NullPointerException("map cannot be null");
		}
		
		this.map = map;
		this.mapDisplay = new MapDisplay(map);
		this.entityService = new EntityService();
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
            ENTRANCE_COLOR);
        break;

      case RoomType.FOUNTAIN:
        String message =
            (map.isFountainEnabled()
                ? "You hear the rushing waters from the Fountain of Objects. It has been"
                    + " reactivated!"
                : "You hear water dripping in this room. The Fountain of Objects is here!");

        ConsoleHelper.printlnColor(message, FOUNTAIN_COLOR);
        break;
		
      case RoomType.PIT:
        ConsoleHelper.printlnColor("You fell into a pit.", PIT_COLOR);
		break;
		
      default:
        break;
    }
  }
  
  public void displayAdjacentRooms() {
    for (Room room : map.getCurrentRoom().getAllAdjacentRooms()) {
      if (room.getType() == RoomType.PIT) {
        ConsoleHelper.printlnColor("You feel a draft of air.", PIT_COLOR);
		break;
      }
    }
	
	displayAdjacentEntityIfExists(EntityType.MAELSTROM);
	displayAdjacentEntityIfExists(EntityType.AMAROK);
  }
  
  private void displayAdjacentEntityIfExists(EntityType entityType){
	if(map.getCurrentRoom().hasAdjacentEntity(entityType)){
		displayEntityMessage(entityType, MessageType.VICINITY);
	}
  }
  
  public void displayEntityMessage(EntityType entityType, MessageType messageType){
	ConsoleHelper.printlnColor(
	  entityService.getEntityMessageOfType(entityType, messageType),
	  entityService.getEntityColor(entityType)
	);
  }
  
  public void displayCommands() {
    for (Command command : Command.values()) {
	  String text = "'" + command.getCommandText() + "': " + command.getDescription();
	  displayInfo(text);
    }
  }
  
  public void displayVictory(){
	ConsoleHelper.printlnColor("You Win!", VICTORY_COLOR);
  }
  
  public void displayDefeat(){
	ConsoleHelper.printlnColor("You Lose!", DEFEAT_COLOR);  
  }
  
  public void displayInfo(String info){
	ConsoleHelper.printlnColor(info, INFO_COLOR);
  }
  
  public void displaySeperator() {
    int seperatorLength = TURN_SEPERATOR_LENGTH;

    for (int i = 0; i < seperatorLength; i++) {
      System.out.print("=");
    }

    System.out.println();
  }
  
  public void displayIntro(){
	displayGeneralIntro();
	System.out.println();
	
	displayPitsIntro();
	System.out.println();
	
	displayMaelstromIntro();
	System.out.println();
	
	displayAmarokIntro();
	System.out.println();
	
	displayWeaponIntro();
	System.out.println();
  }
  
  private void displayGeneralIntro(){
	ConsoleHelper.printColor(
	  "You enter the fountain of objects, a maze of rooms filled with dangerous pits in search of the ",
	  INTRO_COLOR
    );
	ConsoleHelper.printColor(
	  "Fountain of Objects",
	  FOUNTAIN_COLOR
	);
	ConsoleHelper.printlnColor(
	  ".",
	  INTRO_COLOR
	);
	
	ConsoleHelper.printlnColor(
	  "Light is only visible in the entrance, and no other light is seen anywhere in the caverns.",
	  INTRO_COLOR
	);
	
	ConsoleHelper.printlnColor(
	  "You must navigate the Caverns with your other senses.",
	  INTRO_COLOR
	);
  }
  
  private void displayPitsIntro(){
	ConsoleHelper.printColor(
	  "Look out for ",
	  INTRO_COLOR
	);
	ConsoleHelper.printColor(
	  "pits",
	  PIT_COLOR
	);
	ConsoleHelper.printColor(
	  ". You will feel a breeze if a ",
	  INTRO_COLOR
	);
	ConsoleHelper.printColor(
	  "pit",
	  PIT_COLOR
	);
	ConsoleHelper.printlnColor(
	  " is in an adjacent room.",
	  INTRO_COLOR
	);
	
	ConsoleHelper.printColor(
	  "If you enter a room with a ",
	  INTRO_COLOR
	);
	ConsoleHelper.printColor(
	  "pit",
	  PIT_COLOR
	);
	ConsoleHelper.printlnColor(
	  ", you will die.",
	  INTRO_COLOR
	);
  }

  private void displayMaelstromIntro(){
	ConsoleHelper.printColor(
	  "Maelstroms",
	  entityService.getEntityColor(EntityType.MAELSTROM)
    );
	ConsoleHelper.printlnColor(
	  " are violent forces of sentient wind. Entering a room with one could transport you to any other location in the caverns.",
	  INTRO_COLOR
	);
	
	ConsoleHelper.printlnColor(
	  "You will be able to hear their growling and groaning in nearby rooms.",
	  INTRO_COLOR
	);
  }
  
  private void displayAmarokIntro(){
	ConsoleHelper.printColor(
	  "Amaroks",
	  entityService.getEntityColor(EntityType.AMAROK)
    );
	ConsoleHelper.printlnColor(
	  " roam the caverns. Encountering one is certain death, but you can smell their rotten stench in nearby rooms.",
	  INTRO_COLOR
	);
  }
  
  private void displayWeaponIntro(){
	ConsoleHelper.printlnColor(
	  "You carry with you a bow and a quiver of arrows. You can use them to shoot monsters in the caverns but be warned: you have a limited supply.",
	  INTRO_COLOR
    );
  }
}