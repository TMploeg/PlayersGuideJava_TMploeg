package entities;

import java.awt.Point;
import map.Room;
import exceptions.RoomEmptyException;

public class MaelstromMover {
  private static final Point maelstromMovementOffset = new Point(-2, 1);
  
  private final Room startLocation;
  
  public MaelstromMover(Room maelstromRoom){
	EntityType entityType = maelstromRoom.getEntity().orElseThrow(() -> new RoomEmptyException("room does not contain any entity"));
	
	if(entityType != EntityType.MAELSTROM){
		throw new RuntimeException("entity is not a maelstrom");
	}
	
	startLocation = maelstromRoom;
  }
  
  public void move(){
	Room newLocation = startLocation.getRoomAtRelativeLocation(maelstromMovementOffset);
	
	if(newLocation.hasEntity()){
		newLocation = newLocation.getNearestEmptyRoom();
	}
	
	newLocation.setEntity(EntityType.MAELSTROM);
	startLocation.removeEntity();
  }
  
  public Point getPlayerMovementOffset(){
	return new Point(maelstromMovementOffset.x * -1, maelstromMovementOffset.y * -1);
  }
}