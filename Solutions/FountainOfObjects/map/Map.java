package map;

import entities.*;
import helpers.console.*;
import java.awt.Point;
import java.util.NoSuchElementException;

public class Map {
  private static final String POS_MARKER = "x";

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
    Room rowStart = entrance;

    while (true) {
	  displayRow(rowStart);

      System.out.println();

      rowStart = rowStart.getAdjacentRoom(Cardinal.SOUTH);

      if (rowStart == null) {
        break;
      }

	  int nameLength = 1;
	  int space = 1;
	  int markerLength = 3;
	  int leftRightMargin = 1;
	  int verticalSeperatorWidth = 1;
	  
	  int roomDisplayLength = leftRightMargin + nameLength + space + markerLength + leftRightMargin;
	  
	  int rowLength = getRowLength(rowStart);
	  
	  int horizontalSeperatorLength = roomDisplayLength * rowLength + verticalSeperatorWidth * (rowLength - 1);
	  
	  StringBuilder seperatorBuilder = new StringBuilder();
	  
	  for(int i = 0; i < horizontalSeperatorLength; i++){
		seperatorBuilder.append("-");
	  }
	  
      System.out.println(seperatorBuilder.toString());
    }
  }
  
  private void displayRow(Room start){
	Room current = start;
	
	while (true) {
	  displayRoom(current);
	  
	  current = current.getAdjacentRoom(Cardinal.EAST);
	  
	  if (current == null) {
		break;
	  }

	  ConsoleHelper.printColor("|", ConsoleColor.WHITE);
	}
  }
  
  private void displayRoom(Room room){
	System.out.print(" ");
	
	printRoomName(room);
	printRoomEndString(room);
	
	System.out.print(" ");
  }
  
  private void printRoomName(Room room){
	ConsoleColor roomColor = getRoomColor(room);
	String roomName = getRoomName(room);
	
	ConsoleHelper.printColor(roomName, roomColor);
  }
  
  private void printRoomEndString(Room room){
	System.out.print(" ");
	
	boolean isCurrentRoom = room == getCurrentRoom();
	boolean hasMaelstrom = room.getEntityIfAny(Maelstrom.class) != null;
	boolean hasAmarok = room.getEntityIfAny(Amarok.class) != null;

	int markerLength = POS_MARKER.length();
	
	final int maxNrOfMarkers = 3;
	int nrOfMarkers = 0;
	
	if(isCurrentRoom){
	  ConsoleHelper.printColor(POS_MARKER, ConsoleColor.PINK);
	  nrOfMarkers++;
	}
	
	if(hasMaelstrom){
	  ConsoleHelper.printColor(POS_MARKER, ConsoleColor.TEAL);
	  nrOfMarkers++;
	}
	
	if(hasAmarok){
	  ConsoleHelper.printColor(POS_MARKER, ConsoleColor.KUMERA);
	  nrOfMarkers++;
	}
	
	int nrOfSpaces = (maxNrOfMarkers - nrOfMarkers) * markerLength;

	for (int i = 0; i < nrOfSpaces; i++) {
	  System.out.print(" ");
	}
  }
  
  private static int getDefaultRoomLength(){
	int longestLength = 0;
	
    for (RoomType roomType : RoomType.values()) {
      int length = roomType.toString().length();
      if (length > longestLength) {
        longestLength = length;
      }
    }
	
	return longestLength;
  }
  
  private ConsoleColor getRoomColor(Room room) {
	return switch (room.getType()) {
	  case NORMAL -> ConsoleColor.WHITE;
	  case ENTRANCE -> ConsoleColor.YELLOW;
	  case FOUNTAIN -> {
		yield fountainEnabled ? ConsoleColor.BLUE : ConsoleColor.LIGHT_GRAY;
	  }
	  case PIT -> ConsoleColor.DARK_RED;
	  default -> throw new RuntimeException();
	};
  }
  
  private static String getRoomName(Room room){
	String name = room.getType().toString();
	
	return name.length() > 0 ? name.substring(0,1) : "";
  }
  
  private static int getRowLength(Room rowStart){
	int length = 0;
	
	Room current = rowStart;
	
	do{
	  length++;
	  current = current.getAdjacentRoom(Cardinal.EAST);
	}
	while(current != null);
	
	return length;
  }
}
