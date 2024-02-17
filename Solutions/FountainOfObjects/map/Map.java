package map;

import entities.*;
import helpers.console.*;
import java.awt.Point;
import java.util.NoSuchElementException;

public class Map {
  private final Room entrance;
  private Room currentRoom;

  private boolean fountainEnabled = false;

  protected Map(Room entrance) {
    this.entrance = entrance;
    currentRoom = this.entrance;
  }

  public Room getRoom(Point location) {
    if (location.x < 0 || location.y < 0) {
      return null;
    }

    Room currentRoom = entrance;

    for (int currentX = 0; currentX < location.x; currentX++) {
      currentRoom = currentRoom.getAdjacentRoom(Cardinal.EAST);

      if (currentRoom == null) {
        return null;
      }
    }

    for (int currentY = 0; currentY < location.y; currentY++) {
      currentRoom = currentRoom.getAdjacentRoom(Cardinal.SOUTH);

      if (currentRoom == null) {
        return null;
      }
    }

    return currentRoom;
  }

  public Room getCurrentRoom() {
    return currentRoom;
  }
  
  protected Room getEntrance() {
	return entrance;
  }

  public boolean isFountainEnabled() {
    return fountainEnabled;
  }

  public void enableFountain() {
    fountainEnabled = true;
  }

  public void move(Cardinal direction) {
    Room newRoom = currentRoom.getAdjacentRoom(direction);

    if (newRoom == null) {
      throw new NoSuchElementException("current room has no '" + direction.toString() + "' room");
    }

    currentRoom = newRoom;
  }
}
