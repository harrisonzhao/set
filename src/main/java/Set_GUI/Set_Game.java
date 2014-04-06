package Set_GUI;
import java.awt.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class Set_Game extends JPanel {

  private JPanel panel = new JPanel(new BorderLayout()); 
  private JPanel top = new JPanel();
  private JPanel left = new JPanel();
  private JPanel center = new JPanel();
  private JPanel error = new JPanel(); // right side of the box layout; used for error messages

  private String player, opponent;
  
  
  private JButton submitMove = new JButton();
  
  public Set_Game () {
    makeTop();
    makeLeft();
    makeCenter();
    makeError();
    
    panel.add(top, BorderLayout.NORTH);
    panel.add(left, BorderLayout.WEST);
    panel.add(center, BorderLayout.CENTER);
    panel.add(error, BorderLayout.EAST);
    
    add(panel);
    
    setSize(400,400);
  }
  public void makeTop() {
    
  }
  
  public void makeLeft() {
    
  }
  
  public void makeCenter() {
    
  }

  public void makeError() {
    
  }
  
  /**
   * Enters the lobby with your specified username.
   * <p>
   * Sets the default button for the lobby window and adds the user's username to the server's list of active users. Also sets the welcome
   * text
   * <p>
   * @param player Identifies the name of the user playing in this game instance.
   * @param opponent Identifies the name of the user the player is playing against.
   */
  public void enterGame(String player, String opponent) {
    this.player = player;
    this.opponent = opponent;
    
    this.getRootPane().setDefaultButton(submitMove);
  }
}
