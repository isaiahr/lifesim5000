package sim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SimpleUnboundedGrid implements Grid {
	Map <Point, Boolean> alive = new HashMap<>();

	@Override
	public Cell getCellAt(int x, int y) {
		return new SimpleCell(x, y, (alive.containsKey(new Point(x, y)) && alive.get(new Point(x, y))));
	}

	@Override
	public void nextGen() {
		Map<Point, Boolean> nextGen = new HashMap<>();
		for (Point p:alive.keySet()) {
	      int numalive = accum(getCellAt(p.x+1, p.y+1),
	              getCellAt(p.x, p.y+1),
	              getCellAt(p.x-1, p.y+1),
	              getCellAt(p.x+1, p.y),
	              getCellAt(p.x-1, p.y),
	              getCellAt(p.x+1, p.y-1),
	              getCellAt(p.x, p.y-1),
	              getCellAt(p.x-1, p.y-1));
	          if (numalive == 3 || (numalive == 2 && alive.get(p))){
	            nextGen.put(p, true);
	            update(nextGen, p);
	          }
		}
		this.alive = nextGen;
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
	private void update(Map<Point, Boolean> m, Point p) {
		if(!m.containsKey(new Point(p.x+1, p.y+1))) {
			m.put(new Point(p.x+1, p.y+1), false);
		}
		if(!m.containsKey(new Point(p.x, p.y+1))) {
			m.put(new Point(p.x, p.y+1), false);
		}
		if(!m.containsKey(new Point(p.x-1, p.y+1))) {
			m.put(new Point(p.x-1, p.y+1), false);
		}
		if(!m.containsKey(new Point(p.x+1, p.y))) {
			m.put(new Point(p.x+1, p.y), false);
		}
		if(!m.containsKey(new Point(p.x-1, p.y))) {
			m.put(new Point(p.x-1, p.y), false);
		}
		if(!m.containsKey(new Point(p.x+1, p.y-1))) {
			m.put(new Point(p.x+1, p.y-1), false);
		}
		if(!m.containsKey(new Point(p.x, p.y-1))) {
			m.put(new Point(p.x, p.y-1), false);
		}
		if(!m.containsKey(new Point(p.x-1, p.y-1))) {
			m.put(new Point(p.x-1, p.y-1), false);
		}
	}

	@Override
	public void modifyCellState(int x, int y, boolean newstate) {
		if(newstate) {
			alive.put(new Point(x, y), true);
			update(alive, new Point(x,y));
		}
		else {
			alive.remove(new Point(x, y));
		}

	}
	  @Override
	  public SimpleUnboundedGrid clone() {
		  SimpleUnboundedGrid g = new SimpleUnboundedGrid();
		  for(Point k:alive.keySet()) {
			  boolean c = alive.get(k);
			  g.alive.put(k, c);
		  }
		  return g;
	  }

	@Override
	public Iterator<Cell> iterator() {
	   List<Cell> l = new ArrayList<Cell>();
	   for(Point p:alive.keySet()) {
		   Cell c = new SimpleCell(p.x, p.y, alive.get(p));
		   l.add(c);
	   }
	   return l.iterator();
	}
}
