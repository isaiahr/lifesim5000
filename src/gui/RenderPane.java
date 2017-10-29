package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.concurrent.CountDownLatch;
import javax.swing.JPanel;
import sim.Cell;
import sim.Grid;

/**
 * The pane that has the grid on it.
 * a renderpane represents a particular pattern.
 * */
public class RenderPane extends JPanel implements MouseMotionListener, MouseListener, ActionListener{

  private int xoffset;
  private int yoffset;
  private static final long serialVersionUID = -42318381754471375L;
  
  /** mouse variables*/
  private int lastmousex;
  private int lastmousey;
  
  private int scale = 40;
  private Grid grid;
  private CountDownLatch simulating = new CountDownLatch(1);
  
  Thread simulateThread;
  
  public RenderPane(Grid grid) {
    super();
    this.grid = grid;
    xoffset = 0;
    yoffset = 0;
    addMouseListener(this);
    addMouseMotionListener(this);
    simulateThread = new Thread(new Runnable() {

      @Override
      public void run() {
        loop();
        
      }});
    simulateThread.setName("Simulation Thread #0");
    simulateThread.start();
    
  }
  
  @Override
  public void paint(Graphics f) {
    BufferedImage buffer = new BufferedImage(super.getWidth(), super.getHeight(),BufferedImage.TYPE_INT_RGB);
    Graphics g = buffer.getGraphics();
    //g.drawString("Working", (int) xoffset, (int) yoffset);
    int initialx = (int) xoffset/scale - 1;
    int initialy = (int) yoffset/scale - 1;
    int xpos = initialx;
    int ypos = initialy;
    
    while (xpos <= xoffset/scale + super.getWidth()/scale){
      ypos = initialy;
      while (ypos <= yoffset/scale + super.getHeight()/scale) {
        Cell cell = this.grid.getCellAt(xpos, ypos);
        if (cell == null) {
          g.setColor(Color.BLUE);
          g.fillRect(xpos*scale - xoffset, ypos*scale - yoffset, scale, scale);
        }
        else if (cell.alive()) {
          g.setColor(Color.WHITE);
          g.fillRect(xpos*scale - xoffset, ypos*scale - yoffset, scale, scale);
        }
        ypos += 1;
      }
      xpos+=1;
    }
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
    
  }

  @Override
  public void mouseReleased(MouseEvent arg0) {
    
  }

  @Override
  public void mouseDragged(MouseEvent arg0) {
    xoffset -= arg0.getX() - lastmousex;
    yoffset -= arg0.getY() - lastmousey;
    lastmousex = arg0.getX();
    lastmousey = arg0.getY();
    super.repaint();
  }

  @Override
  public void mouseMoved(MouseEvent arg0) {

    
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("advance")){
      grid.nextGen();
      this.repaint();
    }
    if (e.getActionCommand().equals("play")){
      simulating.countDown();
    }
    if (e.getActionCommand().equals("pause")) {
        simulating = new CountDownLatch(1);
        System.out.println("Pause");
    }
  }

  public void loop() {
    while(true) {
      try {
      simulating.await();
      grid.nextGen();
      this.repaint();
        Thread.sleep(60);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }
}