package gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class RenderPane extends JPanel {

  /**
   * generated
   */
  private static final long serialVersionUID = -42318381754471375L;
  
  public RenderPane(int width, int height) {
    super();
    super.setSize(width, height);
  }
  
  @Override
  public void paint(Graphics f) {
    BufferedImage buffer = new BufferedImage(super.getWidth(), super.getHeight(),BufferedImage.TYPE_INT_RGB);
    Graphics g = buffer.getGraphics();
    g.drawString("Working", 200, 200);
    f.drawImage(buffer, 0, 0, null);
  }

}
