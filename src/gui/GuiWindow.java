package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.UIManager;

import io.GridReader;
import io.GridWriter;
import sim.Grid;
import sim.SimpleBoundedGrid;
import sim.SimpleUnboundedGrid;

public class GuiWindow extends JFrame {

  /**
   * generated
   */
  private static final long serialVersionUID = -5490909351513234262L;
  private RenderPane rend;
  
  public GuiWindow() {
    super();
    super.setTitle("lifesim5000");
    super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    MenuBar mb = new MenuBar();
    Menu file = new Menu("File");
    MenuItem save = new MenuItem("Save");
    MenuItem open = new MenuItem("Open");
    MenuItem exit = new MenuItem("Exit");
    exit.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent arg0) {
        System.exit(0);
      }
      
    });
    final GuiWindow hack = this;

    save.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			GridWriter g = new GridWriter(rend.getGrid());
			JFileChooser jfc = new JFileChooser();
			jfc.showSaveDialog(null);
			g.write(jfc.getSelectedFile().getPath());
			
		}});
    file.add(save);
    file.add(open);
    file.add(exit);
    mb.add(file);
    super.setMenuBar(mb);
    Grid g = new SimpleUnboundedGrid();
    g.modifyCellState(10,10, true);
    g.modifyCellState(9, 10, true);
    g.modifyCellState(8, 10, true);
    rend = new RenderPane(g);
    JPanel controlpanel = new JPanel();
    GridLayout gl = new GridLayout();
    gl.setColumns(1);
    controlpanel.setLayout(gl);
    JButton advance = new JButton();
    advance.setText("1F");
    
    advance.setActionCommand("advance");
    advance.addActionListener(rend);


    JRadioButton move = new JRadioButton("Move");
    move.setActionCommand("move");
    move.addActionListener(rend);
    move.setSelected(true);
    JRadioButton draw = new JRadioButton("Draw");
    draw.setActionCommand("draw");
    draw.addActionListener(rend);
    
    JButton rewind = new JButton("Rewind");
    rewind.setActionCommand("rewind");
    rewind.addActionListener(rend);
    ToggleButton playpause = new ToggleButton("play","pause");
    playpause.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
    	rewind.setEnabled((!e.getActionCommand().equals("play")));
    	advance.setEnabled((!e.getActionCommand().equals("play")));
    	move.setEnabled((!e.getActionCommand().equals("play")));
    	draw.setEnabled((!e.getActionCommand().equals("play")));
        move.setSelected(true);
        rend.actionPerformed(e);
      }
      
    });
    open.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			GridReader g = new GridReader();
			JFileChooser jfc = new JFileChooser();
			jfc.showOpenDialog(null);
			Grid b = g.read(jfc.getSelectedFile().getPath());
			rend.kill();
			hack.remove(rend);
			rend = new RenderPane(b);
			move.addActionListener(rend);
			draw.addActionListener(rend);
			rewind.addActionListener(rend);
			advance.addActionListener(rend);
			hack.add(rend, BorderLayout.CENTER);
			hack.validate();
			rend.repaint();
		}});
    ButtonGroup actions = new ButtonGroup();
    actions.add(draw);
    actions.add(move);
    controlpanel.add(advance);
    controlpanel.add(rewind);
    controlpanel.add(playpause);
    controlpanel.add(draw);
    controlpanel.add(move);
    super.add(controlpanel,BorderLayout.NORTH);
    super.add(rend, BorderLayout.CENTER);
    super.pack();
    super.setVisible(true);
  }

}
