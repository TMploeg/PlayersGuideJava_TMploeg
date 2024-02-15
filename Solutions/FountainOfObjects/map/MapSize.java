package map;

public enum MapSize {
  SMALL(4),
  MEDIUM(6),
  LARGE(8);
  
  private int value;
  
  private MapSize(int value){
	  this.value = value;
  }
  
  public int getValue(){
	  return value;
  }
}
