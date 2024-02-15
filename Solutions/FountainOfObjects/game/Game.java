package game;

import helpers.console.*;
import helpers.console.menu.*;
import java.util.ArrayList;
import java.util.List;
import map.*;
import entities.*;

public class Game {
  private enum GameState {
    PLAYING,
    WON,
    LOST
  }

  private Map map;

  private boolean cheatMode;

  public Game(boolean cheatMode) {
    this.cheatMode = cheatMode;

    map = createMap();

    runGame();
  }

  private Map createMap() {
    List<MenuItem<MapSize>> menuItems = new ArrayList<>();
    menuItems.add(new MenuItem<MapSize>("small", MapSize.SMALL));
    menuItems.add(new MenuItem<MapSize>("medium", MapSize.MEDIUM));
    menuItems.add(new MenuItem<MapSize>("large", MapSize.LARGE));

    MapSize mapSize = ConsoleHelper.getMenuInput(new Menu<MapSize>(menuItems));

    return new MapBuilder().setSize(mapSize).build();
  }

  private void runGame() {
    displayRoomMessage();

    while (true) {
      displayLocation();
      displayAdjacentRoomMessages();

      Command chosenCommand = ConsoleHelper.getCommandInput("what do you want to do?");
      executeCommand(chosenCommand);

      displaySeperator();
	  
	  handleRoomContent();
      displayRoomMessage();

      GameState state = getGameState();
      if (state == GameState.PLAYING) {
        continue;
      }

      boolean won = state == GameState.WON;

      String message = won ? "You Win!" : "You Lose!";
      ConsoleColor color = won ? ConsoleColor.GREEN : ConsoleColor.RED;

      ConsoleHelper.printlnColor(message, color);

      break;
    }
  }

  private void executeCommand(Command command) {
    switch (command) {
      case MOVE_NORTH:
        move(Cardinal.NORTH);
        break;
      case MOVE_EAST:
        move(Cardinal.EAST);
        break;
      case MOVE_SOUTH:
        move(Cardinal.SOUTH);
        break;
      case MOVE_WEST:
        move(Cardinal.WEST);
        break;
      case ENABLE_FOUNTAIN:
        map.enableFountain();
        break;
      case HELP:
        showHelp();
        break;
    }
  }

  private void displaySeperator() {
    int seperatorLength = 100;

    for (int i = 0; i < seperatorLength; i++) {
      System.out.print("=");
    }

    System.out.println();
  }

  private void displayLocation() {
    if (cheatMode) {
      map.display();
    } else {
      RoomLocation location = map.getCurrentRoom().getLocation();
      System.out.println("You are in the room at location " + location);
    }

    System.out.println();
  }

  private void displayRoomMessage() {
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
      default:
        break;
    }
  }

  private void displayAdjacentRoomMessages() {
    boolean foundAdjacentPit = false;
	boolean foundAdjacentMaelstrom = false;

    for (Room room : map.getCurrentRoom().getAllAdjacentRooms()) {
      if (!foundAdjacentPit && room.getType() == RoomType.PIT) {
		ConsoleHelper.printlnColor("You feel a draft of air.", ConsoleColor.DARK_RED);
        foundAdjacentPit = true;
      }
	  
	  if(!foundAdjacentMaelstrom && map.getMaelstromIfAny(room) != null){
		ConsoleHelper.printlnColor("You hear growling and groaning nearby.", ConsoleColor.TEAL);
		foundAdjacentMaelstrom = true;
	  }
    }
  }
  
  private void handleRoomContent(){
	  Maelstrom maelstrom = map.getMaelstromIfAny(map.getCurrentRoom());
	  if(maelstrom != null){
		  handleMaelstrom(maelstrom);
	  }
  }
  
  private void handleMaelstrom(Maelstrom maelstrom){
	  ConsoleHelper.printlnColor("You where blown away by a maelstrom.", ConsoleColor.TEAL);
	  maelstrom.move();
	  movePlayerFromMaelstrom();
  }
  
  private void movePlayerFromMaelstrom(){
	java.util.Map<Cardinal, Integer> movementMap = Maelstrom.getPlayerMovementMap();
	
	for(Cardinal direction : movementMap.keySet()){
		for(int i = 0; i < movementMap.get(direction); i++){
			if(!map.getCurrentRoom().hasAdjacentRoom(direction)){
				break;
			}
			
			map.move(direction);
		}
	}
  }

  private void showHelp() {
    for (Command command : Command.values()) {
      ConsoleHelper.printlnColor(
          "'" + command.getCommandText() + "': " + command.getDescription(),
          ConsoleColor.LIGHT_GRAY);
    }
  }

  private void move(Cardinal direction) {
    if (map.getCurrentRoom().hasAdjacentRoom(direction)) {
      map.move(direction);
    } else {
      System.out.println("You hit your head against the wall");
    }
  }

  private GameState getGameState() {
    return switch (map.getCurrentRoom().getType()) {
      case RoomType.ENTRANCE -> {
        yield (map.isFountainEnabled() ? GameState.WON : GameState.PLAYING);
      }
      case RoomType.PIT -> GameState.LOST;
      default -> GameState.PLAYING;
    };
  }
}
