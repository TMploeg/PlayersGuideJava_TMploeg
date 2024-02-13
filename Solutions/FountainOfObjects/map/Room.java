package map;

import java.util.Map;
import java.util.HashMap;
import java.util.Collection;

public class Room {
	private Map<Cardinal, Room> adjacentRooms;
	
	private RoomType type;
	private RoomLocation location;
	
	public Room(RoomType type, RoomLocation location) {
		adjacentRooms = new HashMap<>();
		
		this.type = type;
		this.location = location;
	}

	public RoomType getType(){
		return type;
	}
	
	public Room getAdjacentRoom(Cardinal cardinal){
		for(Cardinal key : adjacentRooms.keySet()){
			if(key == cardinal){
				return adjacentRooms.get(key);
			}
		}
		
		return null;
	}
	
	public boolean hasAdjacentRoom(Cardinal cardinal){
		return adjacentRooms.containsKey(cardinal);
	}
	
	public Collection<Room> getAllAdjacentRooms(){
		return adjacentRooms.values(); 
	}
	
	public RoomLocation getLocation(){
		return location;
	}
	
	protected void link(Cardinal cardinal, Room room){
		adjacentRooms.put(cardinal, room);
		room.adjacentRooms.put(cardinal.opposite(), this);
	}
}