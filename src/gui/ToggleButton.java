package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 * A JButton that switches two (generally opposite) actions
 * each time it presses (aka: it toggles).
 * */
public class ToggleButton extends JButton implements ActionListener{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private ActionListener fake;
  private boolean defaultstate = true;
  private String defaultname;
  private String othername;
  
  public ToggleButton(String defaultname, String othername) {
    super();
    super.setText(defaultname);
    this.defaultname = defaultname;
    this.othername = othername;
  }
  
  public void addActionListener(ActionListener fake) {
    this.fake = fake;
    super.addActionListener(this);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String action = super.getText();
    defaultstate = !defaultstate;
    if (defaultstate){
      this.setText(defaultname);
    }
    else {
      this.setText(othername);
    }
    ActionEvent ex = new ActionEvent(this, defaultstate?1:0, action);
    fake.actionPerformed(ex);
    
  }

}
