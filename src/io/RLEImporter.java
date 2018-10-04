package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import sim.Grid;
import sim.SimpleCell;
import sim.SimpleUnboundedGrid;

public class RLEImporter {
	public RLEImporter() {
	}
	public Grid read(String path) {
		BufferedReader bf = null;

		Grid g = new SimpleUnboundedGrid();
		try {
			
			bf = new BufferedReader(new FileReader(new File(path)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
		
		String xa = "";
		String entire = "";
        while((xa = bf.readLine()) != null) {
            entire += (xa + "\n");
        }
        int x = 0;
        int y = 0;
        int xinit = 0;
        int repeat = 1;
        boolean lastdig = false;
        for(int i = 0; i < entire.length(); i++) {
        	char c = entire.charAt(i);
        	if(c == '#' || c == 'x') {
        		if(entire.charAt(i) == 'x') {
        			System.out.println("xa"+x);
        			while(!Character.isDigit(entire.charAt(i))) {
        				i += 1;
        			}
        			
        			while(Character.isDigit(entire.charAt(i))) {
        				x = x * 10 + Integer.parseInt(entire.charAt(i)+"");
        				i += 1;
        			}
        			while(!Character.isDigit(entire.charAt(i))) {
        				i += 1;
        			}
        			
        			while(Character.isDigit(entire.charAt(i))) {
        				y = y * 10 + Integer.parseInt(entire.charAt(i)+"");
        				i += 1;
        			}
        			while(entire.charAt(i) != '\n') i+=1;
        			x = -x;
        			xinit = x;
        			y = -y;
        			continue;
        		}
        		try {
        		while(entire.charAt(i) != '\n') {
        			i += 1;
        		}
        		 continue;
        		} catch(StringIndexOutOfBoundsException e) {
        			//eof
        			return g;
        		}
        	}
        	if(c == 'b') {
        		x += repeat;
        		repeat = 1;
        	}
        	if(c == 'o') {
        		for(int it = 0; it < repeat; it++) {
        			g.modifyCellState(x, y, true);
        			x += 1;
        		}
        		repeat = 1;
        	}
        	if(c == '$') {
        		y += repeat;
        		x = xinit;
        		repeat = 1;
        	}
        	if(Character.isDigit(c)) {

        		if(lastdig)
        			repeat = 10*repeat + Integer.parseInt(c+"");
        		else
        		    repeat = Integer.parseInt(c+"");
        		lastdig = true;
        	}
        	else if(c != '\n'){
        		lastdig = false;
        	}
        	
        }
		} catch(Exception e) {
			
			e.printStackTrace();
		}
		return g;
	}
}