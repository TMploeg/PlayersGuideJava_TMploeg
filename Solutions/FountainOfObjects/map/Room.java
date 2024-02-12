package map;

import java.util.Map;
import java.util.HashMap;
import java.util.Collection;

public class Room {
	private Map<Cardinal, Room> adjacentRooms;
	
	private RoomType type;
	
	public Room() {
		adjacentRooms = new HashMap<>();
		type = RoomType.NORMAL;
	}

	public RoomType getType(){
		return type;
	}
	
	public void setType(RoomType type){
		this.type = type;
	}
	
	public Room getAdjacentRoom(Cardinal cardinal){
		for(Cardinal key : adjacentRooms.keySet()){
			if(key == cardinal){
				return adjacentRooms.get(key);
			}
		}
		
		return null;
	}
	
	public Collection<Room> getAllAdjacentRooms(){
		return adjacentRooms.values(); 
	}
	
	protected void link(Cardinal cardinal, Room room){
		adjacentRooms.put(cardinal, room);
		room.adjacentRooms.put(cardinal.opposite(), this);
	}
}