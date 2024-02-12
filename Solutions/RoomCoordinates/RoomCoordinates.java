public class RoomCoordinates {
  private record Coordinate(int x, int y) {
    public boolean isAdjacent(Coordinate coordinate) {
      return Math.abs(coordinate.x() - x()) + Math.abs(coordinate.y() - y()) == 1;
    }

    @Override
    public String toString() {
      return "(" + x() + ", " + y() + ")";
    }
  }

  private record Test(Coordinate coordinate, int[] expected) {}

  public static void main(String[] args) {
    Coordinate[] coordinates = {
      new Coordinate(0, 0),
      new Coordinate(0, 1),
      new Coordinate(1, 0),
      new Coordinate(0, -1),
      new Coordinate(-1, 0),
      new Coordinate(1, 1),
      new Coordinate(-1, -1),
      new Coordinate(1, -1),
      new Coordinate(-1, 1),
      new Coordinate(2, 2),
      new Coordinate(10, 32),
      new Coordinate(-55, 21),
      new Coordinate(12, 34)
    };

    for (int i = 0; i < coordinates.length; i++) {
      for (int ii = i + 1; ii < coordinates.length; ii++) {
        boolean isAdjacent = coordinates[i].isAdjacent(coordinates[ii]);

        System.out.println(
            coordinates[i] + ", " + coordinates[ii] + "   \t-> adjacent: " + isAdjacent);
      }
    }
  }
}
