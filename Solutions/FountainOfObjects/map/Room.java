package map;

import entities.EntityType;
import exceptions.*;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Room {
  private Map<Direction, Room> adjacentRooms;

  private Optional<EntityType> entity;

  private RoomType type;
  private RoomLocation location;

  public Room(RoomType type, RoomLocation location) {
    adjacentRooms = new HashMap<>();
    entity = Optional.empty();

    this.type = type;
    this.location = location;
  }

  public RoomType getType() {
    return type;
  }

  public Optional<Room> getAdjacentRoom(Direction cardinal) {
    for (Direction key : adjacentRooms.keySet()) {
      if (key == cardinal) {
        return Optional.of(adjacentRooms.get(key));
      }
    }

    return Optional.empty();
  }

  public boolean hasAdjacentRoom(Direction cardinal) {
    return adjacentRooms.containsKey(cardinal);
  }

  public Collection<Room> getAllAdjacentRooms() {
    return adjacentRooms.values();
  }

  public RoomLocation getLocation() {
    return location;
  }

  public Room getRoomAtRelativeLocation(Point relativeLocation) {
    if (relativeLocation == null) {
      throw new NullPointerException("relativeLocation is null");
    }

    int diagonalOffset = Math.min(Math.abs(relativeLocation.x), Math.abs(relativeLocation.y));
    int remainingHorizontalOffset = Math.abs(relativeLocation.x) - diagonalOffset;
    int remainingVerticalOffset = Math.abs(relativeLocation.y) - diagonalOffset;

    Optional<Direction> horizontal = getDirectionFromHorizontalOffset(relativeLocation.x);
    Optional<Direction> vertical = getDirectionFromVerticalOffset(relativeLocation.y);

    Room current = this;

    for (int i = diagonalOffset; i > 0; i--) {
      Optional<Room> newRoom =
          current.getAdjacentRoom(Direction.compose(horizontal.get(), vertical.get()));

      if (!newRoom.isPresent()) {
        remainingHorizontalOffset =
            current.hasAdjacentRoom(horizontal.get()) ? remainingHorizontalOffset + i : 0;
        remainingVerticalOffset =
            current.hasAdjacentRoom(vertical.get()) ? remainingVerticalOffset + i : 0;

        break;
      }

      current = newRoom.get();
    }

    for (int x = 0;
        x < remainingHorizontalOffset && current.hasAdjacentRoom(horizontal.get());
        x++) {
      current = current.getAdjacentRoom(horizontal.get()).get();
    }

    for (int y = 0; y < remainingVerticalOffset && current.hasAdjacentRoom(vertical.get()); y++) {
      current = current.getAdjacentRoom(vertical.get()).get();
    }

    return current;
  }

  private Optional<Direction> getDirectionFromHorizontalOffset(int offset) {
    if (offset > 0) {
      return Optional.of(Direction.EAST);
    } else if (offset < 0) {
      return Optional.of(Direction.WEST);
    } else {
      return Optional.empty();
    }
  }

  private Optional<Direction> getDirectionFromVerticalOffset(int offset) {
    if (offset > 0) {
      return Optional.of(Direction.SOUTH);
    } else if (offset < 0) {
      return Optional.of(Direction.NORTH);
    } else {
      return Optional.empty();
    }
  }

  protected void link(Direction cardinal, Room room) {
    adjacentRooms.put(cardinal, room);
    room.adjacentRooms.put(cardinal.opposite(), this);
  }

  public Optional<EntityType> getEntity() {
    return entity;
  }

  public void setEntity(EntityType entity) {
    if (hasEntity()) {
      throw new AlreadyOccupiedException("room is already occupied");
    }

    this.entity = Optional.of(entity);
  }

  public void removeEntity() {
    if (!hasEntity()) {
      throw new RoomEmptyException("room does not have entity");
    }

    entity = Optional.empty();
  }

  public boolean hasEntity() {
    return entity.isPresent();
  }

  public boolean hasAdjacentEntity(EntityType entityType) {
    for (Room room : getAllAdjacentRooms()) {
      if (room.getEntity().filter(eT -> eT == entityType).isPresent()) {
        return true;
      }
    }

    return false;
  }

  public Room getNearestEmptyRoom() {
    List<Room> checkedRooms = new ArrayList<>();

    List<Room> currentRooms = new LinkedList<>();
    currentRooms.add(this);

    while (true) {
      List<Room> newCurrentRooms = new LinkedList<>();

      checkedRooms.addAll(currentRooms);

      for (Room adjacentRoom : getAllAdjacentRoomsForCollection(currentRooms)) {
        if (checkedRooms.contains(adjacentRoom)) {
          continue;
        }

        if (adjacentRoom.isValidEmptyRoom()) {
          return adjacentRoom;
        }

        newCurrentRooms.add(adjacentRoom);
      }

      if (newCurrentRooms.size() == 0) {
        break;
      }

      currentRooms = newCurrentRooms;
    }

    throw new RuntimeException("no empty room found");
  }

  private boolean isValidEmptyRoom() {
    return type != RoomType.ENTRANCE && !hasEntity();
  }

  private static Collection<Room> getAllAdjacentRoomsForCollection(Collection<Room> rooms) {
    LinkedList<Room> adjacentRooms = new LinkedList<Room>();

    for (Room room : rooms) {
      for (Room adjacentRoom : room.getAllAdjacentRooms()) {
        if (adjacentRooms.contains(adjacentRoom)) {
          continue;
        }

        adjacentRooms.add(adjacentRoom);
      }
    }

    return adjacentRooms;
  }
}
