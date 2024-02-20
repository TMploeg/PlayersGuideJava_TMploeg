package map;

import entities.*;
import java.util.HashMap;
import java.util.Random;
import java.util.LinkedList;

public class MapBuilder {
  public static final int MIN_SIZE = 4;
  public static final int MAX_SIZE = 25;
  public static final int ENTRANCE_SAFE_SPACE = 2;
  private static final MapSize DEFAULT_SIZE = MapSize.SMALL;

  private MapSize size;

  private HashMap<RoomLocation, RoomType> roomData;

  private LinkedList<RoomLocation> maelstromLocations;
  private LinkedList<RoomLocation> amarokLocations;

  public MapBuilder() {
    size = DEFAULT_SIZE;
  }

  public MapBuilder setSize(MapSize size) {
    this.size = size;

    return this;
  }

  public Map build() {
	roomData = new HashMap<>();
	maelstromLocations = new LinkedList<>();
	amarokLocations = new LinkedList<>();
	
    generateRoomData();
    generateEntityData();

    Room entrance = createAndLinkRooms();
    Map map = new Map(entrance);

    return map;
  }

  private void generateRoomData() {
    setEntrance();
    setFountain();
    setPits();
  }

  private void generateEntityData() {
    setMaelstroms();
	setAmaroks();
  }

  private void setEntrance() {
    roomData.put(new RoomLocation(0, 0), RoomType.ENTRANCE);
  }

  private void setFountain() {
    roomData.put(getRandomUnoccupiedLocation(), RoomType.FOUNTAIN);
  }

  private void setPits() {
    for (int i = 0; i < size.getProperties().pits(); i++) {
      roomData.put(getRandomUnoccupiedLocation(), RoomType.PIT);
    }
  }

  private void setMaelstroms() {
	for(int i = 0; i < size.getProperties().maelstroms(); i++){
		maelstromLocations.add(getRandomUnoccupiedLocation());
	}
  }
  
  private void setAmaroks(){
	for(int i = 0; i < size.getProperties().amaroks(); i++){
		amarokLocations.add(getRandomUnoccupiedLocation());
	}
  }
  
  private RoomLocation getRandomUnoccupiedLocation() {
    Random r = new Random();

    while (true) {
      RoomLocation location =
          new RoomLocation(
              r.nextInt(size.getProperties().size()), r.nextInt(size.getProperties().size()));

	  boolean hasData = roomData.containsKey(location);
	  boolean isInSafeSpace = location.x() < ENTRANCE_SAFE_SPACE && location.y() < ENTRANCE_SAFE_SPACE;
	  boolean hasMaelstrom = maelstromLocations.contains(location);
	  boolean hasAmarok = amarokLocations.contains(location);
	  
      if (hasData || isInSafeSpace || hasMaelstrom || hasAmarok) {
        continue;
      }

      return location;
    }
  }

  private Room createAndLinkRooms() {
    Room entrance = generateNewCollumnRooms(null);
    Room collumnStartRoom = entrance;

    for (int xPos = 1; xPos < size.getProperties().size(); xPos++) {
      collumnStartRoom = generateNewCollumnRooms(collumnStartRoom);
    }

    return entrance;
  }

  private Room generateNewCollumnRooms(Room previousCollumnRoom) {
    boolean hasPreviousCollumn = previousCollumnRoom != null;
    int collumn = hasPreviousCollumn ? (previousCollumnRoom.getLocation().x() + 1) : 0;

    RoomLocation startPos = new RoomLocation(collumn, 0);
    Room first = createRoom(startPos);

    if (hasPreviousCollumn) {
      first.link(Cardinal.WEST, previousCollumnRoom);
    }

    Room previous = first;

    for (int yPos = 1; yPos < size.getProperties().size(); yPos++) {
      RoomLocation newPos = new RoomLocation(collumn, yPos);
      Room newRoom = createRoom(newPos);
	  
      if (hasPreviousCollumn) {
        Room westRoom = previous
		  .getAdjacentRoom(Cardinal.WEST).orElseThrow(() -> new RuntimeException("room should exist"))
		  .getAdjacentRoom(Cardinal.SOUTH).orElseThrow(() ->  new RuntimeException("room should exist"));
		  
        newRoom.link(Cardinal.WEST, westRoom);
      }

      newRoom.link(Cardinal.NORTH, previous);
      previous = newRoom;
    }

    return first;
  }

  private RoomType getRoomTypeForLocation(RoomLocation location) {
    return roomData.containsKey(location) ? roomData.get(location) : RoomType.NORMAL;
  }

  private Room createRoom(RoomLocation location) {
    RoomType type = getRoomTypeForLocation(location);
    Room room = new Room(type, location);
	
	boolean hasMaelstrom = maelstromLocations.contains(location);
	boolean hasAmarok = amarokLocations.contains(location);
	
    if (hasMaelstrom) {
      room.setEntity(new Maelstrom());
    }
	
	if(hasAmarok){
	  if(hasMaelstrom){
		  throw new RuntimeException("room has multiple entities");
	  }
	  
	  room.setEntity(new Amarok());
	}

    return room;
  }
}
