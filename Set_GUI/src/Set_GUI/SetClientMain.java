package Set_GUI;

import javax.swing.SwingUtilities;
import java.io.IOException;

public class SetClientMain {

  /**
   * 
   * @author Alejandro Acosta <acosta317@gmail.com>
   * @since 2014-03-21
   */
	public static void main(String[] args) {
    try {
      SetClient client = new SetClient();
      client.runClient();
    } catch(IOException except) {
      // do something
      System.out.println("oops");
    }
	  /*SwingUtilities.invokeLater(new Runnable() {
      
      public void run() {
        Login log = new Login();
        log.setVisible(true);
      }
    });
	}*/
	}
}
