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
