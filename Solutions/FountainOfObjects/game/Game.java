package game;

import entities.*;
import helpers.console.*;
import menu.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import map.*;
import commands.*;
import java.awt.Point;

public class Game {
  private enum GameState { PLAYING, WON, LOST }
  
  private Map map;
  private GameDisplay gameDisplay;
  private Quiver quiver;
  private CommandExecutor commandExecutor;

  private boolean cheatMode;

  public Game(boolean cheatMode) {
    this.cheatMode = cheatMode;

	MapSize mapSize = getMapSize();	
    map = createMap(mapSize);
	gameDisplay = new GameDisplay(map);
	
	quiver = new Quiver(mapSize.getProperties().arrows());
	
	commandExecutor = initCommandExecutor();

    runGame();
  }
  
  private MapSize getMapSize(){
	List<MenuItem<MapSize>> menuItems = new ArrayList<>();
    menuItems.add(new MenuItem<MapSize>("small", MapSize.SMALL));
    menuItems.add(new MenuItem<MapSize>("medium", MapSize.MEDIUM));
    menuItems.add(new MenuItem<MapSize>("large", MapSize.LARGE));

    return InputHelper.getMenuInput(new Menu<MapSize>(menuItems));
  }

  private Map createMap(MapSize mapSize) {
    return new MapBuilder().setSize(mapSize).build();
  }
  
  private CommandExecutor initCommandExecutor(){
	CommandExecutor commandExecutor = new CommandExecutor();
	
	commandExecutor.mapCommandAction(Command.MOVE_NORTH, () -> move(Direction.NORTH));
	commandExecutor.mapCommandAction(Command.MOVE_EAST, () -> move(Direction.EAST));
	commandExecutor.mapCommandAction(Command.MOVE_SOUTH, () -> move(Direction.SOUTH));
	commandExecutor.mapCommandAction(Command.MOVE_WEST, () -> move(Direction.WEST));
	commandExecutor.mapCommandAction(Command.SHOOT_NORTH, () -> shoot(Direction.NORTH));
	commandExecutor.mapCommandAction(Command.SHOOT_EAST, () -> shoot(Direction.EAST));
	commandExecutor.mapCommandAction(Command.SHOOT_SOUTH, () -> shoot(Direction.SOUTH));
	commandExecutor.mapCommandAction(Command.SHOOT_WEST, () -> shoot(Direction.WEST));
	commandExecutor.mapCommandAction(Command.ENABLE_FOUNTAIN, () -> map.enableFountain());
	commandExecutor.mapCommandAction(Command.HELP, () -> gameDisplay.displayCommands());
	
	return commandExecutor;
  }

  private void runGame() {
    gameDisplay.displayRoom();

    while (true) {
      gameDisplay.displayLocation(cheatMode);
      gameDisplay.displayAdjacentRooms();

      Command chosenCommand = InputHelper.getCommandInput("what do you want to do?");
      commandExecutor.execute(chosenCommand);

      gameDisplay.displaySeperator();

      handleRoomContent();
      
	  gameDisplay.displayRoom();

      GameState state = getGameState();
      if (state == GameState.PLAYING) {
        continue;
      }

	  switch(state){
		  case GameState.WON:
			gameDisplay.displayVictory();
			break;
			
		  case GameState.LOST:
			gameDisplay.displayDefeat();
			break;
			
		  default: throw new IllegalArgumentException("should not reach");
	  }
	  
      break;
    }
  }
  
  private void handleRoomContent() {
	map.getCurrentRoom().getEntity().ifPresent(entity -> {
		gameDisplay.displayEntityInteraction(entity);
		
		if(entity instanceof Maelstrom maelstrom){
			handleMaelstrom(maelstrom);
		}
	});
  }
  
  private void handleMaelstrom(Maelstrom maelstrom) {
    moveMaelstrom(maelstrom);
    movePlayerFromMaelstrom();
  }
  
  private void moveMaelstrom(Maelstrom maelstrom){
	if(maelstrom == null){
		throw new NullPointerException("maelstrom is null");
	}
	
	Point relativeLocation = getRelativeLocationFromMovementMap(Maelstrom.getMovementMap());
	Room newLocation = map.getCurrentRoom().getRoomAtRelativeLocation(relativeLocation);
	
	if(newLocation.hasEntity()){
		newLocation = newLocation.getNearestEmptyRoom();
	}
	
	newLocation.setEntity(maelstrom);
	map.getCurrentRoom().removeEntity();
  }
  
  private Point getRelativeLocationFromMovementMap(java.util.Map<Direction, Integer> movementMap){
	int xOffset = 0;
	
	if(movementMap.containsKey(Direction.WEST)){
	  xOffset -= movementMap.get(Direction.WEST);
	}
	
	if(movementMap.containsKey(Direction.EAST)){
	  xOffset += movementMap.get(Direction.EAST);
	}
	
	int yOffset = 0;
	
	if(movementMap.containsKey(Direction.NORTH)){
	  yOffset -= movementMap.get(Direction.NORTH);
	}
	
	if(movementMap.containsKey(Direction.SOUTH)){
	  yOffset += movementMap.get(Direction.SOUTH);
	}
	
	return new Point(xOffset, yOffset);
  }

  private void movePlayerFromMaelstrom() {
    java.util.Map<Direction, Integer> movementMap = Maelstrom.getInvertedMovementMap();

    for (Direction direction : movementMap.keySet()) {
      for (int i = 0; i < movementMap.get(direction); i++) {
        if (!map.getCurrentRoom().hasAdjacentRoom(direction)) {
          break;
        }

        map.move(direction);
      }
    }
  }

  private void move(Direction direction) {
    if (!map.getCurrentRoom().hasAdjacentRoom(direction)) {
	  gameDisplay.displayInfo("You hit your head against the wall");
	  return;
	}
	
    map.move(direction);
  }
  
  private void shoot(Direction direction){
	ShootResult result = getShootResult(direction);
	
	gameDisplay.displayInfo(result.getMessage());
	
	if(result != ShootResult.NO_ARROWS){
		quiver.takeArrow();
	}

	if(result == ShootResult.HIT){
		Room adjacentRoom = map.getCurrentRoom()
			.getAdjacentRoom(direction).orElseThrow(() -> new NullPointerException("room should exists"));
		
		Entity target = adjacentRoom.getEntity().orElseThrow(() -> new NullPointerException("entity should exist"));
		
		adjacentRoom.removeEntity();
		gameDisplay.displayEntityDeath(target);
	}
	Optional<Room> adjacentRoom = map.getCurrentRoom().getAdjacentRoom(direction);
  }
  
  private ShootResult getShootResult(Direction direction){
	if(!quiver.hasArrows()){
		return ShootResult.NO_ARROWS;
	}
	
	if(!map.getCurrentRoom().hasAdjacentRoom(direction)){
		return ShootResult.NO_ROOM;
	}
	
	if(!map.getCurrentRoom().getAdjacentRoom(direction).get().hasEntity()){
		return ShootResult.MISSED;
	}
	
	return ShootResult.HIT;
  }
  
  private GameState getGameState() {
	RoomType currentRoomType = map.getCurrentRoom().getType();
	
	if(currentRoomType == RoomType.ENTRANCE && map.isFountainEnabled()){
	  return GameState.WON;
	}
	
	if(currentRoomType == RoomType.PIT){
		return GameState.LOST;
	}
	
	if(map.getCurrentRoom().hasEntity() && map.getCurrentRoom().getEntity().get() instanceof Amarok){
		return GameState.LOST;
	}
	
	return GameState.PLAYING;
  }
}
