package sim;

import java.awt.Point;
import java.util.HashMap;

public class SimpleBoundedGrid implements Grid{
  HashMap<Point, SimpleCell> cellmap = new HashMap<>();
  private int width;
  private int height;
  public SimpleBoundedGrid(int width, int height) {
    this.width = width;
    this.height = height;
    for (int i = 1;i <= width;i++) {
      for (int j = 1;j <= height;j++) {
        SimpleCell cell = new SimpleCell(i,j,false);
        cellmap.put(cell.getLocation(), cell);
      }
    }
  }

  @Override
  public SimpleCell getCellAt(int x, int y) {
    if (x > width){
      return getCellAt(x-width, y);
    }
    if (y > height) {
      return getCellAt(x, y-height);
    }
    return cellmap.get(new Point(x,y));
  }
  
  public SimpleCell getCellAt(Point p) {
    return getCellAt(p.x, p.y);
  }

  @Override
  public void nextGen() {
    HashMap<Point, SimpleCell> nextGen = new HashMap<Point, SimpleCell>();
    for (SimpleCell sc: cellmap.values()) {
      int numalive = accum(getCellAt(sc.getLocation().x+1, sc.getLocation().y+1),
          getCellAt(sc.getLocation().x, sc.getLocation().y+1),
          getCellAt(sc.getLocation().x-1, sc.getLocation().y+1),
          getCellAt(sc.getLocation().x+1, sc.getLocation().y),
          getCellAt(sc.getLocation().x, sc.getLocation().y),
          getCellAt(sc.getLocation().x-1, sc.getLocation().y),
          getCellAt(sc.getLocation().x+1, sc.getLocation().y-1),
          getCellAt(sc.getLocation().x, sc.getLocation().y-1),
          getCellAt(sc.getLocation().x-1, sc.getLocation().y-1));
      if (numalive == 3 || (numalive == 2 && sc.alive())){
        nextGen.put(sc.getLocation(), new SimpleCell(sc.getLocation().x, sc.getLocation().y, true));
      }
      else
        nextGen.put(sc.getLocation(), new SimpleCell(sc.getLocation().x, sc.getLocation().y, false));
    }
    
  }

  private int accum(SimpleCell... cells) {
    int numalive = 0;
    for (SimpleCell cell:cells) {
      if (cell.alive()) {
        numalive++;
      }
    }
    return numalive;
    
  }

  @Override
  public void nthGen(int n) {
    for (int i = 0; i < n; i++) {
      nextGen();
    }
  }
  
}
