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
  
  private final Map map;
  private final GameDisplay gameDisplay;
  private final Quiver quiver;
  private final CommandExecutor commandExecutor;

  private boolean cheatMode;

  public Game(boolean cheatMode) {
    this.cheatMode = cheatMode;

	MapSize mapSize = getMapSize();	
    map = createMap(mapSize);
	gameDisplay = new GameDisplay(map);
	
	quiver = new Quiver(mapSize.getProperties().arrows());
	
	commandExecutor = initCommandExecutor();

	gameDisplay.displayIntro();

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
	map.getCurrentRoom().getEntity().ifPresent(entityType -> {
		gameDisplay.displayEntityMessage(entityType, MessageType.INTERACT);
		
		if(entityType == EntityType.MAELSTROM){
			handleMaelstrom();
		}
	});
  }
  
  private void handleMaelstrom() {
	MaelstromMover mover = new MaelstromMover(map.getCurrentRoom());
	
	mover.move();
    
	moveMultiple(mover.getPlayerMovementOffset());
  }
  
  private void moveMultiple(Point movementOffset) {
	Optional<Direction> horizontalDirection = switch(Integer.signum(movementOffset.x)){
		case 1 -> Optional.of(Direction.EAST);
		case -1 -> Optional.of(Direction.WEST);
		case 0 -> Optional.empty();
		default -> throw new RuntimeException("should not reach");
	};
	
	horizontalDirection.ifPresent(dir -> {
	  for(int x = 0; x < Math.abs(movementOffset.x); x++){
		map.move(dir);
	  }
	});
	
	Optional<Direction> verticalDirection = switch(Integer.signum(movementOffset.y)){
		case 1 -> Optional.of(Direction.SOUTH);
		case -1 -> Optional.of(Direction.NORTH);
		case 0 -> Optional.empty();
		default -> throw new RuntimeException("should not reach");
	};

	verticalDirection.ifPresent(dir -> {
	  for(int y = 0; y < Math.abs(movementOffset.y); y++){
		map.move(dir);
	  }
	});
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
		
		EntityType target = adjacentRoom.getEntity().orElseThrow(() -> new NullPointerException("entity should exist"));
		
		adjacentRoom.removeEntity();
		gameDisplay.displayEntityMessage(target, MessageType.DEATH);
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
	
	return map.getCurrentRoom().getEntity()
	  .map(
	    entityType -> {
		  if(entityType == EntityType.AMAROK){
		    return GameState.LOST;
		  }
		  
		  return null;
	    }
	  )
	  .orElse(GameState.PLAYING);
  }
}
