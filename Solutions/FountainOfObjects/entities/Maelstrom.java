package entities;

import java.util.HashMap;
import java.util.Map;
import map.*;
import helpers.console.ConsoleColor;

public class Maelstrom extends Entity {
  private static final ConsoleColor ENTITY_COLOR = ConsoleColor.TEAL;
  
  private static final Map<Cardinal, Integer> movementMap =
      new HashMap<>() {
        {
          put(Cardinal.SOUTH, 1);
          put(Cardinal.WEST, 2);
        }
      };

  private Maelstrom(Room location) {
    super(location, ENTITY_COLOR);
  }

  public static void createInRoom(Room room) {
    new Maelstrom(room);
  }

  public void move() {
    Room newLocation = getLocation();

    for (Cardinal direction : movementMap.keySet()) {
      for (int i = 0; i < movementMap.get(direction); i++) {
        Room nextRoom = newLocation.getAdjacentRoom(direction);

        if (nextRoom == null) {
          break;
        }

        newLocation = nextRoom;
      }
    }

    newLocation.addEntity(this);
    getLocation().removeEntity(this);

    setLocation(newLocation);
  }

  public static Map<Cardinal, Integer> getPlayerMovementMap() {
    HashMap<Cardinal, Integer> playerMovementMap = new HashMap<>();

    for (Cardinal direction : movementMap.keySet()) {
      playerMovementMap.put(direction.opposite(), movementMap.get(direction));
    }

    return playerMovementMap;
  }
  
  public String getDeathMessage(){
	return "a harrowing scream echoes through the cavern";  
  }
}
