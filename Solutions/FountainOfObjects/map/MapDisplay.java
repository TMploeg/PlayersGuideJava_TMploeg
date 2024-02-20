package map;

import helpers.console.*;
import entities.*;
import java.util.Optional;

public class MapDisplay {
	private static final String POS_MARKER = "x";
	
	private Map map;
	
	public MapDisplay(Map map){
	  this.map = map;
	}
	
	public void display() {
		Optional<Room> rowStart = Optional.of(map.getEntrance());

		while (rowStart.isPresent()) {
			displayRow(rowStart.get());

			System.out.println();

			int nameLength = 1;
			int space = 1;
			int markerLength = 3;
			int leftRightMargin = 1;
			int verticalSeperatorWidth = 1;
			  
			int roomDisplayLength = leftRightMargin + nameLength + space + markerLength + leftRightMargin;
			  
			int rowLength = getRowLength(rowStart.get());
			  
			int horizontalSeperatorLength = roomDisplayLength * rowLength + verticalSeperatorWidth * (rowLength - 1);
			  
			StringBuilder seperatorBuilder = new StringBuilder();
			  
			for(int i = 0; i < horizontalSeperatorLength; i++){
				seperatorBuilder.append("-");
			}
			  
			System.out.println(seperatorBuilder.toString());
			
			rowStart = rowStart.get().getAdjacentRoom(Cardinal.SOUTH);
		}
	}
  
  private void displayRow(Room start){
	Room current = start;
	
	while (true) {
	  displayRoom(current);
	  
	  Optional<Room> eastRoom = current.getAdjacentRoom(Cardinal.EAST);
	  
	  if (!eastRoom.isPresent()) {
		break;
	  }
	  
	  current = eastRoom.get();

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
	
	boolean isCurrentRoom = room == map.getCurrentRoom();
	
	Entity roomEntity = room.getEntity();

	int markerLength = POS_MARKER.length();
	
	final int maxNrOfMarkers = 3;
	int nrOfMarkers = 0;
	
	if(isCurrentRoom){
	  ConsoleHelper.printColor(POS_MARKER, ConsoleColor.PINK);
	  nrOfMarkers++;
	}
	
	if(roomEntity != null){
	  ConsoleHelper.printColor(POS_MARKER, roomEntity.getColor());
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
		yield map.isFountainEnabled() ? ConsoleColor.BLUE : ConsoleColor.LIGHT_GRAY;
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
	
	Optional<Room> current = Optional.of(rowStart);
	
	while(current.isPresent()){
	  length++;
	  
	  current = current.get().getAdjacentRoom(Cardinal.EAST);
	}
	
	return length;
  }
}