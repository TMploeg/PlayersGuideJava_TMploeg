package map;

import java.util.HashMap;
import java.util.Random;

public class MapBuilder{
	private record RoomLocation(int x, int y) {}
	
	public static final int MIN_SIZE = 4;
	public static final int MAX_SIZE = 25;
	public static final int MIN_FOUNTAIN_DISTANCE = 2;
	
	private int size;
	
	private HashMap<RoomLocation, RoomType> roomData;
	
	public MapBuilder(){
		size = MIN_SIZE;
	}
	
	public MapBuilder setSize(int size){
		if(size < MIN_SIZE){
			throw new IllegalArgumentException("size must be greater than or equal to " + MIN_SIZE);
		}
		
		if(size > MAX_SIZE){
			throw new IllegalArgumentException("size must be smaller than or equal to " + MAX_SIZE);
		}
		
		this.size = size;
		
		return this;
	}
	
	public Map build(){
		roomData = generateDefaultRoomData();
		
		Map map = new Map(createAndLinkRooms());
		
		return map;
	}
	
	private HashMap<RoomLocation, RoomType> generateDefaultRoomData(){
		HashMap<RoomLocation, RoomType> roomData = new HashMap<>();
		
		roomData.put(new RoomLocation(0, 0), RoomType.ENTRANCE);
		
		Random r = new Random();
		
		RoomLocation fountainLocation = null;
		
		while(fountainLocation == null){
			int fountainX = r.nextInt(size);
			int fountainY = r.nextInt(size);
			
			if(fountainX >= MIN_FOUNTAIN_DISTANCE || fountainY >= MIN_FOUNTAIN_DISTANCE){
				fountainLocation = new RoomLocation(fountainX, fountainY);
			}
		}
		
		roomData.put(fountainLocation, RoomType.FOUNTAIN);
		return roomData;
	}
	
	private Room createAndLinkRooms(){
		Room entrance = generateNewCollumnRooms(null, 0);
		Room collumnStartRoom = entrance;
		
		for(int xPos = 1; xPos < size; xPos++){
			collumnStartRoom = generateNewCollumnRooms(collumnStartRoom, xPos);
		}
		
		return entrance;
	}
	
	private Room generateNewCollumnRooms(Room previousCollumnRoom, int collumnPos){
		RoomLocation startPos = new RoomLocation(collumnPos, 0);
		Room first = new Room(getRoomTypeForLocation(startPos));
		
		if(previousCollumnRoom != null){
			first.link(Cardinal.WEST, previousCollumnRoom);
		}
		
		Room previous = first;
		
		for(int yPos = 1; yPos < size; yPos++){
			RoomLocation newPos = new RoomLocation(collumnPos, yPos);
			Room newRoom = new Room(getRoomTypeForLocation(newPos));
		
			if(collumnPos > 0){
				Room westRoom = previous.getAdjacentRoom(Cardinal.WEST).getAdjacentRoom(Cardinal.SOUTH);			
				newRoom.link(Cardinal.WEST, westRoom);
			}
			
			newRoom.link(Cardinal.NORTH, previous);
			previous = newRoom;
		}
		
		return first;
	}
	
	private RoomType getRoomTypeForLocation(RoomLocation location){
		return roomData.containsKey(location) ? roomData.get(location) : RoomType.NORMAL;
	}
}