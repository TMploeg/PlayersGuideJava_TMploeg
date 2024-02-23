package map;

import entities.Entity;
import exceptions.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.awt.Point;

public class Room {
  private Map<Cardinal, Room> adjacentRooms;
 
  private Optional<Entity> entity;

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

  public Optional<Room> getAdjacentRoom(Cardinal cardinal) {
    for (Cardinal key : adjacentRooms.keySet()) {
      if (key == cardinal) {
        return Optional.of(adjacentRooms.get(key));
      }
    }

    return Optional.empty();
  }

  public boolean hasAdjacentRoom(Cardinal cardinal) {
    return adjacentRooms.containsKey(cardinal);
  }

  public Collection<Room> getAllAdjacentRooms() {
    return adjacentRooms.values();
  }

  public RoomLocation getLocation() {
    return location;
  }
  
  public Room getRoomAtRelativeLocation(Point relativeLocation){
	if(relativeLocation == null){
		throw new NullPointerException("relativeLocation is null");
	}
	
	Cardinal xDirection = relativeLocation.x < 0 ? Cardinal.WEST : Cardinal.EAST;
	Cardinal yDirection = relativeLocation.y < 0 ? Cardinal.NORTH : Cardinal.SOUTH;
	
	Room current = this;
	
	for(int x = 0; x < Math.abs(relativeLocation.x) && current.hasAdjacentRoom(xDirection); x++){
	  current = current.getAdjacentRoom(xDirection).get();
	}
	
	for(int y = 0; y < Math.abs(relativeLocation.y) && current.hasAdjacentRoom(yDirection); y++){
	  current = current.getAdjacentRoom(yDirection).get();
	}
	
	return current;
  }

  protected void link(Cardinal cardinal, Room room) {
    adjacentRooms.put(cardinal, room);
    room.adjacentRooms.put(cardinal.opposite(), this);
  }

  public Optional<Entity> getEntity(){
	return entity;
  }

  public void setEntity(Entity entity) {
	if(hasEntity()){
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
  
  public boolean hasEntity(){
	return entity.isPresent();
  }
  
  public List<Entity> getAdjacentEntities(Predicate<Entity> predicate){
	if(predicate == null){
		throw new NullPointerException("predicate is null");
	}
	
	final List<Entity> result = new LinkedList<>();
	
	for(Room room : getAllAdjacentRooms()){
		room.getEntity().filter(predicate).ifPresent(entity -> result.add(entity));
	}
	
	return result;
  }
  
  public Room getNearestEmptyRoom(){
	List<Room> checkedRooms = new ArrayList<>();
	
	List<Room> currentRooms = new LinkedList<>();
	currentRooms.add(this);
	
	while(true){
		List<Room> newCurrentRooms = new LinkedList<>();
		
		checkedRooms.addAll(currentRooms);
		
		for(Room adjacentRoom : getAllAdjacentRoomsForCollection(currentRooms)){
			if(checkedRooms.contains(adjacentRoom)){
				continue;
			}
			
			if(adjacentRoom.isValidEmptyRoom()){
				return adjacentRoom;
			}
			
			newCurrentRooms.add(adjacentRoom);
		}
		
		if(newCurrentRooms.size() == 0){
			break;
		}
		
		currentRooms = newCurrentRooms;
	}
	
	throw new RuntimeException("no empty room found");
  }
  
  private boolean isValidEmptyRoom(){
	return type != RoomType.ENTRANCE && !hasEntity();
  }
  
  private static Collection<Room> getAllAdjacentRoomsForCollection(Collection<Room> rooms){
	LinkedList<Room> adjacentRooms = new LinkedList<Room>();
	
	for(Room room : rooms){
		for(Room adjacentRoom : room.getAllAdjacentRooms()){
			if(adjacentRooms.contains(adjacentRoom)){
				continue;
			}
			
			adjacentRooms.add(adjacentRoom);
		}
	}
	
	return adjacentRooms;
  }
}
