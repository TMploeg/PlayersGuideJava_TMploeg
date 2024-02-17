package map;

import entities.*;
import java.util.HashMap;
import java.util.Random;

public class MapBuilder {
  public static final int MIN_SIZE = 4;
  public static final int MAX_SIZE = 25;
  public static final int ENTRANCE_SAFE_SPACE = 2;
  private static final MapSize DEFAULT_SIZE = MapSize.SMALL;

  private MapSize size;

  private HashMap<RoomLocation, RoomType> roomData;

  private RoomLocation[] maelstromLocations;
  private RoomLocation[] amarokLocations;

  public MapBuilder() {
    size = DEFAULT_SIZE;
  }

  public MapBuilder setSize(MapSize size) {
    this.size = size;

    return this;
  }

  public Map build() {
    generateRoomData();
    generateEntityData();

    Room entrance = createAndLinkRooms();
    Map map = new Map(entrance);

    return map;
  }

  private void generateRoomData() {
    roomData = new HashMap<RoomLocation, RoomType>();

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
    maelstromLocations = generateEntityLocations(size.getProperties().maelstroms());
  }
  
  private void setAmaroks(){
	amarokLocations = generateEntityLocations(size.getProperties().amaroks());
  }
  
  private RoomLocation[] generateEntityLocations(int count){
	RoomLocation[] locations = new RoomLocation[count];
	
	for (int i = 0; i < locations.length; i++) {
      locations[i] = getRandomUnoccupiedLocation();
    }
	
	return locations;
  }

  private RoomLocation getRandomUnoccupiedLocation() {
    Random r = new Random();

    while (true) {
      RoomLocation location =
          new RoomLocation(
              r.nextInt(size.getProperties().size()), r.nextInt(size.getProperties().size()));

      if (roomData.containsKey(location)
          || (location.x() < ENTRANCE_SAFE_SPACE && location.y() < ENTRANCE_SAFE_SPACE)) {
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
        Room westRoom = previous.getAdjacentRoom(Cardinal.WEST).getAdjacentRoom(Cardinal.SOUTH);
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

    if (arrayContains(maelstromLocations, location)) {
      Maelstrom.createInRoom(room);
    }
	
	if(arrayContains(amarokLocations, location)){
	  Amarok.createInRoom(room);
	}

    return room;
  }
  
  private static <TElement> boolean arrayContains(TElement[] collection, TElement element){
	for(TElement collectionElement : collection){
	  if(element.equals(collectionElement)){
		return true;
	  }
	}
	
	return false;
  }
}
