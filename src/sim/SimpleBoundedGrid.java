package sim;

import java.util.HashMap;
import java.util.Iterator;

public class SimpleBoundedGrid implements Grid{
  HashMap<Point, Cell> cellmap = new HashMap<>();
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

  public Cell getCellAtW(int x, int y) {
    if (x > width){
      return getCellAtW(x-width, y);
    }
    if (y > height) {
      return getCellAtW(x, y-height);
    }
    if (x <= 0) {
      return getCellAtW(x+width, y);
    }
    if (y <= 0) {
      return getCellAtW(x, y+width);
    }
    return cellmap.get(new Point(x,y));
  }
  
  @Override
  public Cell getCellAt(int x, int y) {
    return cellmap.get(new Point(x,y));
  }

  public Cell getCellAt(Point p) {
    return getCellAt(p.x, p.y);
  }

  @Override
  public void nextGen() {
    HashMap<Point, Cell> nextGen = new HashMap<Point, Cell>();
    for (Cell sc: cellmap.values()) {
      int numalive = accum(getCellAtW(sc.getLocation().x+1, sc.getLocation().y+1),
          getCellAtW(sc.getLocation().x, sc.getLocation().y+1),
          getCellAtW(sc.getLocation().x-1, sc.getLocation().y+1),
          getCellAtW(sc.getLocation().x+1, sc.getLocation().y),
          getCellAtW(sc.getLocation().x-1, sc.getLocation().y),
          getCellAtW(sc.getLocation().x+1, sc.getLocation().y-1),
          getCellAtW(sc.getLocation().x, sc.getLocation().y-1),
          getCellAtW(sc.getLocation().x-1, sc.getLocation().y-1));
      if (numalive == 3 || (numalive == 2 && sc.alive())){
        nextGen.put(sc.getLocation(), new SimpleCell(sc.getLocation().x, sc.getLocation().y, true));
      }
      else
        nextGen.put(sc.getLocation(), new SimpleCell(sc.getLocation().x, sc.getLocation().y, false));
    }
    cellmap = nextGen;
    
  }

  private int accum(Cell... cells) {
    int numalive = 0;
    for (Cell cell:cells) {
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

  @Override
  public void modifyCellState(int x, int y, boolean newstate) {
    if (getCellAt(x,y) == null) {
      return;
    }
    cellmap.put(new Point(x,y), new SimpleCell(x, y, newstate));
    
  }
  
  @Override
  public SimpleBoundedGrid clone() {
	  SimpleBoundedGrid g = new SimpleBoundedGrid(width, height);
	  for(Point k:cellmap.keySet()) {
		  Cell c = cellmap.get(k);
		  c.alive();
		  SimpleCell b = new SimpleCell(c.getLocation().x, c.getLocation().y, c.alive());
		  g.cellmap.put(k, b);
	  }
	  return g;
  }

@Override
public Iterator<Cell> iterator() {
	// TODO Auto-generated method stub
	return (Iterator<Cell>) cellmap.values().iterator();
}
  
}
