package map;

import java.util.Optional;

public enum Direction {
  NORTH,
  EAST,
  SOUTH,
  WEST,
  NORTH_EAST,
  SOUTH_EAST,
  SOUTH_WEST,
  NORTH_WEST;
  
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
  
  public Direction filterHorizontal(){
	return switch (this) {
      case EAST -> EAST;
      case WEST -> WEST;
	  case SOUTH_EAST -> EAST;
	  case NORTH_EAST -> EAST;
	  case SOUTH_WEST -> WEST;
	  case NORTH_WEST -> WEST;
	  case NORTH -> throw new RuntimeException("direction cannot be exclusively vertical");
	  case SOUTH -> throw new RuntimeException("direction cannot be exclusively vertical");
      default -> throw new RuntimeException();
    };
  }
  
  public Direction filterVertical(){
	return switch (this) {
      case NORTH -> NORTH;
      case SOUTH -> SOUTH;
	  case SOUTH_EAST -> SOUTH;
	  case NORTH_EAST -> NORTH;
	  case SOUTH_WEST -> SOUTH;
	  case NORTH_WEST -> NORTH;
	  case WEST -> throw new RuntimeException("direction cannot be exclusively horizontal");
	  case EAST -> throw new RuntimeException("direction cannot be exclusively horizontal");
      default -> throw new RuntimeException();
    };
  }
  
  public static Direction compose(Direction horizontal, Direction vertical){
	return switch(horizontal){
		case EAST -> switch(vertical) {
			case NORTH -> NORTH_EAST;
			case SOUTH -> SOUTH_EAST;
			default -> throw new RuntimeException("not a vertical value");
		};
		case WEST -> switch(vertical) {
			case NORTH -> NORTH_WEST;
			case SOUTH -> SOUTH_WEST;
			default -> throw new RuntimeException("not a vertical value");
		};
		default -> throw new RuntimeException("not a horizontal value");
	};
  }
}
