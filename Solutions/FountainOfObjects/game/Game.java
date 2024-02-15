package game;

import helpers.console.*;
import helpers.console.*;
import helpers.console.menu.*;
import map.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class Game {
  private enum GameState {
    PLAYING,
    WON
  }

  private HashMap<RoomType, ConsoleColor> roomTypeColorMap;

  private Map map;

  public Game() {
    map = createMap();
	
	map.display();
	
    roomTypeColorMap = new HashMap<RoomType, ConsoleColor>();

    initRoomTypeColorMap();

    runGame();
  }
  
  private Map createMap(){
	List<MenuItem<MapSize>> menuItems = new ArrayList<>();
	menuItems.add(new MenuItem<MapSize>("small", MapSize.SMALL));
	menuItems.add(new MenuItem<MapSize>("medium", MapSize.MEDIUM));
	menuItems.add(new MenuItem<MapSize>("large", MapSize.LARGE));
	
	MapSize mapSize = ConsoleHelper.getMenuInput(new Menu<MapSize>(menuItems));
	
    return new MapBuilder().setSize(mapSize).build();
  }

  private void runGame() {
    while (getGameState() == GameState.PLAYING) {
      displaySeperator();
      displayLocation();
      displayRoomMessage();

      Command chosenCommand = ConsoleHelper.getCommandInput("what do you want to do?");
      executeCommand(chosenCommand);
    }

    displaySeperator();
    ConsoleHelper.printlnColor("You win!", ConsoleColor.LIGHT_PURPLE);
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

  private void initRoomTypeColorMap() {
    roomTypeColorMap.put(RoomType.NORMAL, ConsoleColor.WHITE);
    roomTypeColorMap.put(RoomType.ENTRANCE, ConsoleColor.YELLOW);
    roomTypeColorMap.put(RoomType.FOUNTAIN, ConsoleColor.BLUE);
  }

  private void displaySeperator() {
    int seperatorLength = 50;

    for (int i = 0; i < 50; i++) {
      System.out.print("-");
    }

    System.out.println();
  }

  private void displayLocation() {
    RoomLocation location = map.getCurrentRoom().getLocation();
    System.out.println("You are in the room at location " + location);
  }

  private void displayRoomMessage() {
    String roomMessage =
        switch (map.getCurrentRoom().getType()) {
          case RoomType.ENTRANCE ->
              "You see light in this room coming from outside the cavern. This is the entrance.";
          case RoomType.FOUNTAIN -> {
            yield (map.isFountainEnabled()
                ? "You hear the rushing waters from the Fountain of Objects. It has been"
                      + " reactivated!"
                : "You hear water dripping in this room. The Fountain of Objects is here!");
          }
          default -> null;
        };

    if (roomMessage != null) {
      ConsoleHelper.printlnColor(roomMessage, roomTypeColorMap.get(map.getCurrentRoom().getType()));
    }
  }

  private void showHelp() {
    for (Command command : Command.values()) {
      ConsoleHelper.printlnColor(
          "'" + command.getCommandText() + "': " + command.getDescription(), ConsoleColor.GREEN);
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
    if (map.isFountainEnabled() && map.getCurrentRoom().getType() == RoomType.ENTRANCE) {
      return GameState.WON;
    }

    return GameState.PLAYING;
  }
}
