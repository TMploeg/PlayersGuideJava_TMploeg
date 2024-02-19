package entities;

import java.util.HashMap;
import java.util.Map;
import map.*;
import helpers.console.*;

public class Maelstrom extends Entity {
  private static final ConsoleColor ENTITY_COLOR = ConsoleColor.TEAL;
  
  private static final Map<Cardinal, Integer> movementMap =
      new HashMap<>() {
        {
          put(Cardinal.SOUTH, 1);
          put(Cardinal.WEST, 2);
        }
      };

  public Maelstrom() {
    super(ENTITY_COLOR);
  }

  public static Map<Cardinal, Integer> getMovementMap() {
	return movementMap;
  }

  public static Map<Cardinal, Integer> getInvertedMovementMap() {
    HashMap<Cardinal, Integer> playerMovementMap = new HashMap<>();

    for (Cardinal direction : movementMap.keySet()) {
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
