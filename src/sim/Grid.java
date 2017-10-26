package sim;

/**Abstract Grid interface, representing a collection of cells.*/
public interface Grid {
  /** returns the cell at a specific location */
  public Cell getCellAt(int x, int y);
  /** updates the grid to reflect the next generation */
  public void nextGen();
  /** updates the grid to reflect the nth generation 
   * (equivalent to calling nextGen n times*/
  public void nthGen(int n);
  /**
   * modify the cells state
   * @param x - x position of cell
   * @param y - y position of cell
   * @param newstate the new state (alive or dead) of the cell
   */
  public void modifyCellState(int x, int y, boolean newstate);
  
}
