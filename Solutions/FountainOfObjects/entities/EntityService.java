package entities;

import helpers.console.ConsoleColor;

public class EntityService {
  public ConsoleColor getEntityColor(EntityType entityType){
	return EntityData.getEntityData(entityType).getColor();
  }
  
  public String getEntityMessageOfType(EntityType entityType, MessageType messageType){
	if(entityType == EntityType.PLAYER){
		throw new IllegalArgumentException("EntityType.PLAYER cannot have messages");
	}
	
	return EntityData.getEntityData(entityType).getMessage(messageType);
  }
}