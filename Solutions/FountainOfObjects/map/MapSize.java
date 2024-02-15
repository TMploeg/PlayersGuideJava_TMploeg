package map;

public enum MapSize {
  SMALL(4, 1),
  MEDIUM(6, 2),
  LARGE(8, 4);

  private int value;
  private int nrOfPits;

  private MapSize(int value, int nrOfPits) {
    this.value = value;
    this.nrOfPits = nrOfPits;
  }

  public int getValue() {
    return value;
  }

  public int getNrOfPits() {
    return nrOfPits;
  }
}
