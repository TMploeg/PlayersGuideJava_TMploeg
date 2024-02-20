package game;

import entities.*;
import helpers.console.*;
import helpers.console.menu.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import map.*;
import commands.*;

public class Game {
  private enum GameState {
    PLAYING,
    WON,
    LOST
  }

  private Map map;
  private MapDisplay mapDisplay;
  private Quiver quiver;
  private CommandExecutor commandExecutor;

  private boolean cheatMode;

  public Game(boolean cheatMode) {
    this.cheatMode = cheatMode;

	MapSize mapSize = getMapSize();	
    map = createMap(mapSize);
	mapDisplay = new MapDisplay(map);
	
	quiver = new Quiver(getNrOfArrowsFromMapSize(mapSize));
	
	commandExecutor = initCommandExecutor();

    runGame();
  }
  
  private MapSize getMapSize(){
	List<MenuItem<MapSize>> menuItems = new ArrayList<>();
    menuItems.add(new MenuItem<MapSize>("small", MapSize.SMALL));
    menuItems.add(new MenuItem<MapSize>("medium", MapSize.MEDIUM));
    menuItems.add(new MenuItem<MapSize>("large", MapSize.LARGE));

    return ConsoleHelper.getMenuInput(new Menu<MapSize>(menuItems));
  }

  private Map createMap(MapSize mapSize) {
    return new MapBuilder().setSize(mapSize).build();
  }
  
  private int getNrOfArrowsFromMapSize(MapSize mapSize){
	return switch(mapSize){
	  case MapSize.SMALL -> 5;
	  case MapSize.MEDIUM -> 8;
	  case MapSize.LARGE -> 12;
	  default -> throw new IllegalArgumentException("unknown enum value detected");
	};
  }
  
  private CommandExecutor initCommandExecutor(){
	CommandExecutor commandExecutor = new CommandExecutor();
	
	commandExecutor.mapCommandAction(Command.MOVE_NORTH, () -> move(Cardinal.NORTH));
	commandExecutor.mapCommandAction(Command.MOVE_EAST, () -> move(Cardinal.EAST));
	commandExecutor.mapCommandAction(Command.MOVE_SOUTH, () -> move(Cardinal.SOUTH));
	commandExecutor.mapCommandAction(Command.MOVE_WEST, () -> move(Cardinal.WEST));
	commandExecutor.mapCommandAction(Command.SHOOT_NORTH, () -> shoot(Cardinal.NORTH));
	commandExecutor.mapCommandAction(Command.SHOOT_EAST, () -> shoot(Cardinal.EAST));
	commandExecutor.mapCommandAction(Command.SHOOT_SOUTH, () -> shoot(Cardinal.SOUTH));
	commandExecutor.mapCommandAction(Command.SHOOT_WEST, () -> shoot(Cardinal.WEST));
	commandExecutor.mapCommandAction(Command.ENABLE_FOUNTAIN, () -> map.enableFountain());
	commandExecutor.mapCommandAction(Command.HELP, () -> showHelp());
	
	return commandExecutor;
  }

  private void runGame() {
    displayRoomMessage();

    while (true) {
      displayLocation();
      displayAdjacentRoomMessages();

      Command chosenCommand = ConsoleHelper.getCommandInput("what do you want to do?");
      commandExecutor.execute(chosenCommand);

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
  
  private void displayInfo(String info){
	ConsoleHelper.printlnColor(info, ConsoleColor.LIGHT_GRAY);
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
      mapDisplay.display();
    } else {
      RoomLocation location = map.getCurrentRoom().getLocation();
      displayInfo("You are in the room at location " + location);
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
		break;
		
      default:
        break;
    }
  }

  private void displayAdjacentRoomMessages() {
    boolean foundAdjacentPit = false;
    boolean foundAdjacentMaelstrom = false;
	boolean foundAdjacentAmarok = false;

    for (Room room : map.getCurrentRoom().getAllAdjacentRooms()) {
      if (!foundAdjacentPit && room.getType() == RoomType.PIT) {
        ConsoleHelper.printlnColor("You feel a draft of air.", ConsoleColor.DARK_RED);
        foundAdjacentPit = true;
      }

	  Entity entity = room.getEntity();
	  
	  if(entity != null){
		  if(entity instanceof Maelstrom){
			foundAdjacentMaelstrom = true;
		  }
		  else if(entity instanceof Amarok){
			foundAdjacentAmarok = true;
		  }
		  else{
			throw new RuntimeException("unknown entity type");
		  }
		  
		  entity.showMessage(MessageType.AMBIANCE);
	  }
    }
  }

  private void handleRoomContent() {
	if(map.getCurrentRoom().hasEntity()){
		Entity entity = map.getCurrentRoom().getEntity();
		
		entity.showMessage(MessageType.INTERACT);
		
		if(entity instanceof Maelstrom maelstrom){
			handleMaelstrom(maelstrom);
		}
	}
  }
  
  private void handleMaelstrom(Maelstrom maelstrom) {
    moveMaelstrom(maelstrom);
    movePlayerFromMaelstrom();
  }
  
  private void moveMaelstrom(Maelstrom maelstrom){
	if(maelstrom == null){
		throw new NullPointerException("maelstrom is null");
	}
	
	Room newLocation = map.getCurrentRoom();
	
	java.util.Map<Cardinal, Integer> movementMap = Maelstrom.getMovementMap();
	
	for (Cardinal direction : movementMap.keySet()) {
      for (int i = 0; i < movementMap.get(direction); i++) {
        Optional<Room> nextRoom = newLocation.getAdjacentRoom(direction);

        if (!nextRoom.isPresent()) {
          break;
        }

        newLocation = nextRoom.get();
      }
    }
	
	if(newLocation.hasEntity()){
		newLocation = newLocation.getNearestEmptyRoom();
	}
	
	newLocation.setEntity(maelstrom);
	map.getCurrentRoom().removeEntity();
  }

  private void movePlayerFromMaelstrom() {
    java.util.Map<Cardinal, Integer> movementMap = Maelstrom.getInvertedMovementMap();

    for (Cardinal direction : movementMap.keySet()) {
      for (int i = 0; i < movementMap.get(direction); i++) {
        if (!map.getCurrentRoom().hasAdjacentRoom(direction)) {
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
    if (!map.getCurrentRoom().hasAdjacentRoom(direction)) {
	  displayInfo("You hit your head against the wall");
	  return;
	}
	
    map.move(direction);
  }
  
  private void shoot(Cardinal direction){
	if(!quiver.hasArrows()){
	  displayInfo("You cannot shoot, your quiver is empty");
	  return;
	}
	
	quiver.takeArrow();
	
	Optional<Room> adjacentRoom = map.getCurrentRoom().getAdjacentRoom(direction);
	
	if(!adjacentRoom.isPresent()){
		displayInfo("Your arrow shot into a wall");
		return;
	}
	
	Entity target = adjacentRoom.get().getEntity();
	
	if(target == null){
		displayInfo("Your arrow didn't hit anything");
		return;
	}
	
	target.showMessage(MessageType.DEATH);
	adjacentRoom.get().removeEntity();
  }
  
  private GameState getGameState() {
	RoomType currentRoomType = map.getCurrentRoom().getType();
	
	if(currentRoomType == RoomType.ENTRANCE && map.isFountainEnabled()){
	  return GameState.WON;
	}
	
	if(currentRoomType == RoomType.PIT){
		return GameState.LOST;
	}
	
	if(map.getCurrentRoom().hasEntity() && map.getCurrentRoom().getEntity() instanceof Amarok){
		return GameState.LOST;
	}
	
	return GameState.PLAYING;
  }
}
