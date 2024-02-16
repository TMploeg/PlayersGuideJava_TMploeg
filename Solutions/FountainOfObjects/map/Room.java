package map;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import exceptions.*;
import entities.Entity;

public class Room {
  private Map<Cardinal, Room> adjacentRooms;
  private LinkedList<Entity> entities;

  private RoomType type;
  private RoomLocation location;

  public Room(RoomType type, RoomLocation location) {
    adjacentRooms = new HashMap<>();
	entities = new LinkedList<>();

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
  
  public void addEntity(Entity entity){
	if(entities.contains(entity)){
	  throw new DuplicateElementException("room does not have entity");
	}
	
	entities.add(entity);
  }
  
  public void removeEntity(Entity entity){
	if(!entities.contains(entity)){
	  throw new NoSuchElementException("room does not have entity");
	}
	
	entities.remove(entity);
  }
  
  public <TEntity extends Entity> TEntity getEntityIfAny(Class<TEntity> entityClass){
	for(Entity entity : entities){
	  if(entity.getClass() == entityClass){
		return (TEntity)entity;
	  }
	}
	
	return null;
  }
}
