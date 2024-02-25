package entities;

import java.util.HashMap;
import java.util.Map;
import map.*;
import helpers.console.*;

public class Maelstrom extends Entity {
  public static final ConsoleColor ENTITY_COLOR = ConsoleColor.TEAL;
  
  private static final Map<Direction, Integer> movementMap =
      new HashMap<>() {
        {
          put(Direction.SOUTH, 1);
          put(Direction.WEST, 2);
        }
      };

  public Maelstrom() {
    super(ENTITY_COLOR);
  }

  public static Map<Direction, Integer> getMovementMap() {
	return movementMap;
  }

  public static Map<Direction, Integer> getInvertedMovementMap() {
    HashMap<Direction, Integer> playerMovementMap = new HashMap<>();

    for (Direction direction : movementMap.keySet()) {
      playerMovementMap.put(direction.opposite(), movementMap.get(direction));
    }

    return playerMovementMap;
  }
  
  @Override
  protected String getDeathMessage(){
	return "A harrowing scream echoes through the cavern";  
  }
  
  @Override
  protected String getAmbianceMessage(){
	return "You hear growling and groaning nearby";  
  }
  
  @Override
  protected String getInteractMessage(){
	return "You where blown away by a " + this.getClass().getSimpleName().toLowerCase();  
  }
}
