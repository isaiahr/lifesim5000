package sim;

import java.awt.Point;

public interface Cell {
  /**gets the location of the cell on a two-dimensional grid*/
  public Point getLocation();
  /**gets the state of the cell (dead or alive) (alive = true, dead=false)*/
  public boolean alive();

}
