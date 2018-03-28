package sim;

import java.util.Objects;

public class Point {
  public int x;
  public int y;
  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Point){
      return ((Point) obj).x == this.x && ((Point) obj).y == this.y;
    }
    return false;
  }
  
  @Override
  public String toString() {
    return "("+x+","+y+")";
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(x,y);
  }
}
