package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.Stack;
import java.util.concurrent.CountDownLatch;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import sim.Cell;
import sim.Grid;

/**
 * The pane that has the grid on it.
 * a renderpane represents a particular pattern.
 * */
public class RenderPane extends JPanel implements MouseMotionListener, MouseListener, ActionListener, MouseWheelListener, ChangeListener{

  private float xoffset;
  private float yoffset;
  private static final long serialVersionUID = -42318381754471373L;
  
  /** mouse variables*/
  private int lastmousex;
  private int lastmousey;
  
  private int scale = 40;
  private Grid grid;
  private CountDownLatch simulating = new CountDownLatch(1);
  
  private int state;
  
  private int DRAW = 3141;
  private int PAN = 271828;
  
  private int DELAY = 60;
  private int GENS = 1;
  
  private boolean running = true;
  
  private Stack<Grid> hist = new Stack<Grid>();
  

  
  // draw mode: true for drawing, false for erasing
  private boolean drawmode = true;
  
  Thread simulateThread;
  
  public RenderPane(Grid grid) {
    super();
    this.grid = grid;
    xoffset = 0;
    yoffset = 0;
    addMouseListener(this);
    addMouseMotionListener(this);
    addMouseWheelListener(this);
    simulateThread = new Thread(new Runnable() {

      @Override
      public void run() {
        loop();
        
      }});
    simulateThread.setName("Simulation Thread #0");
    simulateThread.start();
    System.out.println("initializing");
  }
  
  @Override
  public void paint(Graphics f) {
    BufferedImage buffer = new BufferedImage(super.getWidth(), super.getHeight(),BufferedImage.TYPE_INT_RGB);
    Graphics g = buffer.getGraphics();
    int initialx = (int) xoffset - 1;
    int initialy = (int) yoffset - 1;
    int xpos = initialx;
    int ypos = initialy;
    
    while (xpos <= xoffset + super.getWidth()/scale + scale){
      g.setColor(Color.GRAY);
      g.drawLine((int) (xpos*scale - xoffset*scale), 0, (int) (xpos*scale - xoffset*scale), getHeight());
      ypos = initialy;
      while (ypos <= yoffset + super.getHeight()/scale + scale) {
        Cell cell = this.grid.getCellAt(xpos, ypos);
        if(xpos == initialx) { 
          g.setColor(Color.GRAY);
          g.drawLine(0, (int) (ypos*scale - yoffset*scale), getWidth(), (int) (ypos*scale - yoffset*scale));
        }
        if (cell == null) {
          g.setColor(Color.BLUE);
          g.fillRect((int) (xpos*scale - xoffset*scale), (int) (ypos*scale - yoffset*scale), scale, scale);
        }
        else if (cell.alive()) {
          g.setColor(Color.WHITE);
          g.fillRect((int) (xpos*scale - xoffset*scale), (int) (ypos*scale - yoffset*scale), scale, scale);
        }
        ypos += 1;
      }
      xpos+=1;
    }
    g.setColor(Color.RED);
    g.drawString("offset ("+xoffset+", "+yoffset+") scale: "+scale, 30, super.getHeight()-50);
    f.drawImage(buffer, 0, 0, null);
  }

  @Override
  public void mouseClicked(MouseEvent arg0) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void mouseEntered(MouseEvent arg0) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void mouseExited(MouseEvent arg0) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void mousePressed(MouseEvent arg0) {
    lastmousex = arg0.getX();
    lastmousey = arg0.getY();
    if (state == DRAW) {
      drawmode = !grid.getCellAt(((int)(xoffset*scale)+lastmousex)/scale, ((int)(yoffset*scale)+lastmousey)/scale).alive();
      grid.modifyCellState(((int)(xoffset*scale)+lastmousex)/scale, ((int)(yoffset*scale)+lastmousey)/scale, drawmode);
      repaint();
    }
  }

  @Override
  public void mouseReleased(MouseEvent arg0) {
    System.out.println(xoffset + ","+yoffset);
  }

  @Override
  public void mouseDragged(MouseEvent arg0) {
    if (state == DRAW) {
      grid.modifyCellState(((int)(xoffset*scale)+arg0.getX())/scale, ((int)(yoffset*scale)+arg0.getY())/scale, drawmode);
    }
    else {
      xoffset -= ((float)arg0.getX() - (float)lastmousex)/scale;
      yoffset -= ((float)arg0.getY() - (float)lastmousey)/scale;
      lastmousex = arg0.getX();
      lastmousey = arg0.getY();
    }
    super.repaint();
  }


  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("advance")){
      grid.nextGen();
      this.repaint();
    }
    if (e.getActionCommand().equals("play")){
      hist.push(grid.clone());
      state = PAN;
      simulating.countDown();
    }
    if (e.getActionCommand().equals("pause")) {
      state = PAN;
      simulating = new CountDownLatch(1);
    }
    if (e.getActionCommand().equals("move")) {
      state = PAN;
    }
    if (e.getActionCommand().equals("draw")) {
      state = DRAW;
    }
    if (e.getActionCommand().equals("rewind")) {
      if(!hist.isEmpty()) {
        grid = hist.pop();
        super.repaint();
      }
    }
  }

  public void loop() {
    while(running) {
      try {
      simulating.await();
      long t = System.currentTimeMillis();
      grid.nthGen(GENS);
      this.repaint();
        Thread.sleep(Math.max(DELAY-(System.currentTimeMillis()-t), 0));
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    System.out.println("disposing");
  }
  
  public void kill() {
	  this.running = false;
	  
  }
  @Override
  public void mouseWheelMoved(MouseWheelEvent e) {
	// blocks left of middle
	float bx = getWidth()/(2*scale);
	// similar for vertical component
	float by = getHeight()/(2*scale);
    scale *= Math.pow(2, -e.getWheelRotation());
    scale = Math.max(10, scale);
    scale = Math.min(320, scale);
    float newbx = getWidth()/(2*scale);
    float newby = getHeight()/(2*scale);
    xoffset -= (newbx-bx);
    yoffset -= (newby-by);
    repaint();
  }
  
  public Grid getGrid() {
	  return grid;
  }

@Override
public void mouseMoved(MouseEvent e) {
}

@Override
public void stateChanged(ChangeEvent e) {
	int v = ((JSlider)e.getSource()).getValue();
	DELAY = (int) (60.0 * (Math.pow((double)2,-(double)v)));
	GENS = 1;
	if (v > 2) {
		DELAY = 15;
		GENS = (int)Math.round( Math.pow(2 , v-3));
	}
	
}
}