package map;

import entities.*;
import helpers.console.*;
import java.awt.Point;
import java.util.NoSuchElementException;

public class Map {
  private static final String POS_MARKER = "(x)";

  private final Room entrance;
  private Room currentRoom;

  private boolean fountainEnabled = false;

  private Maelstrom[] maelstroms;

  protected Map(Room entrance, Maelstrom[] maelstroms) {
    this.entrance = entrance;
    currentRoom = this.entrance;

    this.maelstroms = maelstroms;
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

  public void display() {
    Room start = entrance;

    int largestRoomTypeNameLength = 0;
    for (RoomType roomType : RoomType.values()) {
      int length = roomType.toString().length();
      if (length > largestRoomTypeNameLength) {
        largestRoomTypeNameLength = length;
      }
    }

    while (true) {
      Room current = start;

      int length = 0;

      int count = 0;

      while (true) {
        ConsoleColor color =
            switch (current.getType()) {
              case NORMAL -> ConsoleColor.WHITE;
              case ENTRANCE -> ConsoleColor.YELLOW;
              case FOUNTAIN -> {
                yield fountainEnabled ? ConsoleColor.BLUE : ConsoleColor.LIGHT_GRAY;
              }
              case PIT -> ConsoleColor.DARK_RED;
              default -> throw new RuntimeException();
            };

        String roomTypeName = current.getType().toString();
        ConsoleHelper.printColor(roomTypeName, color);

        boolean isCurrentRoom = current == getCurrentRoom();
        boolean hasMaelstrom = getMaelstromIfAny(current) != null;

        int nrOfSpaces =
            largestRoomTypeNameLength
                - roomTypeName.length()
                + ((isCurrentRoom || hasMaelstrom) ? 0 : (POS_MARKER.length() + 1));

        if (isCurrentRoom || hasMaelstrom) {
          ConsoleHelper.printColor(
              " " + POS_MARKER, isCurrentRoom ? ConsoleColor.PINK : ConsoleColor.TEAL);
        }

        for (int i = 0; i < nrOfSpaces; i++) {
          System.out.print(" ");
        }

        count++;

        current = current.getAdjacentRoom(Cardinal.EAST);

        if (current == null) {
          break;
        }

        System.out.print(" | ");
      }

      System.out.println();

      start = start.getAdjacentRoom(Cardinal.SOUTH);

      if (start == null) {
        break;
      }

      String seperator = "";

      for (int i = 0;
          i
              < (count * largestRoomTypeNameLength
                  + (count - 1) * 3
                  + count * (POS_MARKER.length() + 1));
          i++) {
        seperator += "-";
      }

      System.out.println(seperator);
    }
  }

  public Maelstrom getMaelstromIfAny(Room room) {
    for (Maelstrom maelstrom : maelstroms) {
      if (maelstrom.getPosition() == room) {
        return maelstrom;
      }
    }

    return null;
  }
}
