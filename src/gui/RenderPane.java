package gui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class RenderPane extends JPanel implements MouseMotionListener, MouseListener{

  private long xoffset;
  private long yoffset;
  private static final long serialVersionUID = -42318381754471375L;
  
  /** mouse variables*/
  private int lastmousex;
  private int lastmousey;
  
  public RenderPane(int width, int height) {
    super();
    xoffset = 0;
    yoffset = 0;
    super.setSize(width, height);
    addMouseListener(this);
    addMouseMotionListener(this);
  }
  
  @Override
  public void paint(Graphics f) {
    BufferedImage buffer = new BufferedImage(super.getWidth(), super.getHeight(),BufferedImage.TYPE_INT_RGB);
    Graphics g = buffer.getGraphics();
    g.drawString("Working", (int) xoffset, (int) yoffset);
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
    xoffset += arg0.getX() - lastmousex;
    yoffset += arg0.getY() - lastmousey;
    lastmousex = arg0.getX();
    lastmousey = arg0.getY();
    super.repaint();
  }

  @Override
  public void mouseMoved(MouseEvent arg0) {

    
  }

}
