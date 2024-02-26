package entities;

import helpers.console.ConsoleColor;
import java.util.HashMap;
import java.util.Map;

class EntityData {
  private static final Map<EntityType, EntityData> entityDataMap =
      new HashMap<>() {
        {
          put(EntityType.PLAYER, new EntityData(null, ConsoleColor.PINK));
          put(
              EntityType.MAELSTROM,
              new EntityData(
                  new HashMap<MessageType, String>() {
                    {
                      put(MessageType.DEATH, "A harrowing scream echoes through the cavern.");
                      put(MessageType.VICINITY, "You hear growling and groaning nearby.");
                      put(
                          MessageType.INTERACT,
                          "You where blown away by a "
                              + EntityType.MAELSTROM.toString().toLowerCase()
                              + ".");
                    }
                  },
                  ConsoleColor.TEAL));
          put(
              EntityType.AMAROK,
              new EntityData(
                  new HashMap<MessageType, String>() {
                    {
                      put(MessageType.DEATH, "A piercing wail sounds through the cavern.");
                      put(MessageType.VICINITY, "You smell a rotten stench nearby.");
                      put(
                          MessageType.INTERACT,
                          "You where mauled by an "
                              + EntityType.AMAROK.toString().toLowerCase()
                              + ".");
                    }
                  },
                  ConsoleColor.KUMERA));
        }
      };

  static EntityData getEntityData(EntityType type) {
    if (!entityDataMap.containsKey(type)) {
      throw new RuntimeException("entitytype '" + type.toString() + "' not implemented");
    }

    return entityDataMap.get(type);
  }

  private final Map<MessageType, String> messageMap;
  private final ConsoleColor color;

  private EntityData(Map<MessageType, String> messageMap, ConsoleColor color) {
    this.messageMap = messageMap;
    this.color = color;
  }

  ConsoleColor getColor() {
    return color;
  }

  String getMessage(MessageType type) {
    if (!messageMap.containsKey(type)) {
      throw new RuntimeException("entitytype '" + type.toString() + "' not implemented");
    }

    return messageMap.get(type);
  }
}
