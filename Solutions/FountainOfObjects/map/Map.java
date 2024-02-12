package map;

import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;

public class Map{
	private final Room entrance;
	
	public Map(int size){
		if(size < 1){
			throw new IllegalArgumentException("size must be greater than or equal to 1");
		}
		
		entrance = new Room();
		entrance.setType(RoomType.ENTRANCE);
		
		LinkedList<Room> currentRooms = new LinkedList<>();
		currentRooms.add(entrance);
		
		boolean sizeComplete = currentRooms.size() == size;
		
		while(true){
			LinkedList<Room> newRooms = getNewRooms(currentRooms, !sizeComplete);
			
			if(!sizeComplete && newRooms.size() == size){
				sizeComplete = true;
			}
			
			currentRooms = newRooms;
			
			if(sizeComplete && currentRooms.size() == 1){
				break;
			}
		}
	}
	
	private LinkedList<Room> getNewRooms(LinkedList<Room> oldRooms, boolean addOuterRooms){
		LinkedList<Room> newRooms = new LinkedList<>();
		
		Iterator<Room> iterator = oldRooms.iterator();
		
		if(!iterator.hasNext()){
			throw new RuntimeException("list is empty");
		}
		
		Room previous = iterator.next();
		
		while(iterator.hasNext()){
			Room current = iterator.next();
			
			Room newRoom = new Room();
			newRooms.addLast(newRoom);

			previous.link(Cardinal.SOUTH, newRoom);
			current.link(Cardinal.EAST, newRoom);
			
			previous = current;
		}
		
		if(addOuterRooms){
			Room newEastRoom = new Room();
			newRooms.addFirst(newEastRoom);
			oldRooms.getFirst().link(Cardinal.EAST, newEastRoom);
		
			Room newSouthRoom = new Room();
			newRooms.addLast(newSouthRoom);
			oldRooms.getLast().link(Cardinal.SOUTH, newSouthRoom);
		}
		
		return newRooms;
	}
	
	public void display(){
		Room start = entrance;
		
		while(true){
			Room current = start;
			String line = "";
			
			while(true){
				line += current.getType();
				
				current = current.getAdjacentRoom(Cardinal.EAST);
				
				if(current == null){
					break;
				}
				
				line += " | ";
			}
			
			System.out.println(line);
			
			start = start.getAdjacentRoom(Cardinal.SOUTH);
			
			if(start == null){
				break;
			}
			
			String seperator = "";
			
			for(int i = 0; i < line.length(); i++){
				seperator += "-";
			}
			
			System.out.println(seperator);
		}
	}
}