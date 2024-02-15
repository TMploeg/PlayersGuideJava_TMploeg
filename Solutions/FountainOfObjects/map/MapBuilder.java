package map;

import java.util.HashMap;
import java.util.Random;

public class MapBuilder {
  public static final int MIN_SIZE = 4;
  public static final int MAX_SIZE = 25;
  public static final int ENTRANCE_SAFE_SPACE = 2;
  private static final MapSize DEFAULT_SIZE = MapSize.SMALL;

  private MapSize size;

  private HashMap<RoomLocation, RoomType> roomData;

  public MapBuilder() {
    size = DEFAULT_SIZE;
  }

  public MapBuilder setSize(MapSize size) {
    this.size = size;

    return this;
  }

  public Map build() {
    generateRoomData();

    Map map = new Map(createAndLinkRooms());

    return map;
  }

  private void generateRoomData() {
    roomData = new HashMap<RoomLocation, RoomType>();

    setEntrance();
    setFountain();
    setPits();
  }

  private void setEntrance() {
    roomData.put(new RoomLocation(0, 0), RoomType.ENTRANCE);
  }

  private void setFountain() {
    roomData.put(getRandomUnoccupiedLocation(), RoomType.FOUNTAIN);
  }

  private void setPits() {
    for (int i = 0; i < size.getNrOfPits(); i++) {
      roomData.put(getRandomUnoccupiedLocation(), RoomType.PIT);
    }
  }

  private RoomLocation getRandomUnoccupiedLocation() {
    Random r = new Random();

    while (true) {
      RoomLocation location =
          new RoomLocation(r.nextInt(size.getValue()), r.nextInt(size.getValue()));

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

    for (int xPos = 1; xPos < size.getValue(); xPos++) {
      collumnStartRoom = generateNewCollumnRooms(collumnStartRoom);
    }

    return entrance;
  }

  private Room generateNewCollumnRooms(Room previousCollumnRoom) {
    boolean hasPreviousCollumn = previousCollumnRoom != null;
    int collumn = hasPreviousCollumn ? (previousCollumnRoom.getLocation().x() + 1) : 0;

    RoomLocation startPos = new RoomLocation(collumn, 0);
    Room first = new Room(getRoomTypeForLocation(startPos), startPos);

    if (hasPreviousCollumn) {
      first.link(Cardinal.WEST, previousCollumnRoom);
    }

    Room previous = first;

    for (int yPos = 1; yPos < size.getValue(); yPos++) {
      RoomLocation newPos = new RoomLocation(collumn, yPos);
      Room newRoom = new Room(getRoomTypeForLocation(newPos), newPos);

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
}
