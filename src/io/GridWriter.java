package io;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import sim.Cell;
import sim.Grid;

public class GridWriter {
	
	private Grid grid;

	public GridWriter(Grid grid) {
		this.grid = grid;
	}
	
	public void write(String path) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			fos.write(1);
		} catch (IOException e1) {
			e1.printStackTrace();
		} // version
		for(Cell c: grid) {
			if(c.alive()) {
			    try {
			        fos.write(c.getLocation().x);
					fos.write(c.getLocation().y);
			    }catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		try {
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
