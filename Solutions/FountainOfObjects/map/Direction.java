package map;

public enum Direction {
  NORTH,
  EAST,
  SOUTH,
  WEST;
  
  public Direction opposite() {
    return switch (this) {
      case NORTH -> SOUTH;
      case EAST -> WEST;
      case SOUTH -> NORTH;
      case WEST -> EAST;
	  case NORTH_EAST -> SOUTH_WEST;
	  case SOUTH_EAST -> NORTH_WEST;
	  case SOUTH_WEST -> NORTH_EAST;
	  case NORTH_WEST -> SOUTH_EAST;
      default -> throw new RuntimeException();
    };
  }
}
