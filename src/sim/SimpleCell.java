package sim;

import sim.Point;

public class SimpleCell implements Cell {
  private Point location;
  private boolean state;
  public SimpleCell(int x, int y, boolean state) {
    location = new Point(x, y);
    this.state = state;
  }

  @Override
  public Point getLocation() {
    // TODO Auto-generated method stub
    return location;
  }

  @Override
  public boolean alive() {
    // TODO Auto-generated method stub
    return state;
  }

}
