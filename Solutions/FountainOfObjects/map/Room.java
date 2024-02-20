package map;

import entities.Entity;
import exceptions.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Room {
  private Map<Cardinal, Room> adjacentRooms;
 
  private Entity entity;

  private RoomType type;
  private RoomLocation location;

  public Room(RoomType type, RoomLocation location) {
    adjacentRooms = new HashMap<>();
    entity = null;

    this.type = type;
    this.location = location;
  }

  public RoomType getType() {
    return type;
  }

  public Room getAdjacentRoom(Cardinal cardinal) {
    for (Cardinal key : adjacentRooms.keySet()) {
      if (key == cardinal) {
        return adjacentRooms.get(key);
      }
    }

    return null;
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

  protected void link(Cardinal cardinal, Room room) {
    adjacentRooms.put(cardinal, room);
    room.adjacentRooms.put(cardinal.opposite(), this);
  }

  public Entity getEntity(){
	return entity;
  }

  public void setEntity(Entity entity) {
	if(this.entity != null){
		throw new AlreadyOccupiedException("room is already occupied");
	}
	
	this.entity = entity;
  }

  public void removeEntity() {
    if (entity == null) {
      throw new NullPointerException("room does not have entity");
    }

    entity = null;
  }
  
  public boolean hasEntity(){
	return entity != null;
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
			
			if(!adjacentRoom.hasEntity()){
				return adjacentRoom;
			}
			
			newCurrentRooms.add(adjacentRoom);
		}
		
		if(newCurrentRooms.size() == 0){
			break;
		}
		
		currentRooms = newCurrentRooms;
	}
	
	return null;
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
