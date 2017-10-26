package gui;

import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

public class GuiWindow extends JFrame {

  /**
   * generated
   */
  private static final long serialVersionUID = -5490909351513234262L;
  
  public GuiWindow() {
    super();
    super.setTitle("lifesim5000");
    MenuBar mb = new MenuBar();
    Menu file = new Menu("File");
    MenuItem exit = new MenuItem("Exit");
    exit.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent arg0) {
        System.exit(0);
        
      }
      
    });
    file.add(exit);
    mb.add(file);
    super.setMenuBar(mb);
    super.add(new RenderPane(400,400));
    super.pack();
    super.setVisible(true);
  }

}
