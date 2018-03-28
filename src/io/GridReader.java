package io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import sim.Grid;
import sim.SimpleCell;
import sim.SimpleUnboundedGrid;

public class GridReader {
	public GridReader() {
	}
	public Grid read(String path) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
		int v = fis.read();
		int i = -1;
		Grid g = new SimpleUnboundedGrid();
		while((i = fis.read()) != -1) {
			int j = fis.read();
			g.modifyCellState(i, j, true);
		}
		fis.close();
		return g;
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
