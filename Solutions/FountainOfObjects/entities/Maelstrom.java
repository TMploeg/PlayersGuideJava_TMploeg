package entities;

import map.*;
import java.util.HashMap;
import java.util.Map;

public class Maelstrom{
	private Room position;
	
	private static final Map<Cardinal, Integer> movementMap = new HashMap<>(){{
		put(Cardinal.SOUTH, 1);
		put(Cardinal.WEST, 2);
	}};
	
	public Maelstrom(Room position){
		this.position = position;
	}
	
	public Room getPosition(){
		return position;
	}
	
	public void move(){
		for(Cardinal direction : movementMap.keySet()){
			for(int i = 0; i < movementMap.get(direction); i++){
				Room nextRoom = position.getAdjacentRoom(direction);
				
				if(nextRoom == null){
					break;
				}
				
				position = nextRoom;
			}
		}
	}
	
	public static Map<Cardinal, Integer> getPlayerMovementMap(){
		Map<Cardinal, Integer> result = new HashMap<>();
		
		for(Cardinal direction : movementMap.keySet()){
			result.put(direction.opposite(), movementMap.get(direction));
		}
		
		return result;
	}
}