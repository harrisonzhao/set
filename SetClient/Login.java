import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.util.*;

/* Written by Alejandro Acosta
 * 
 */
public class Login extends JFrame implements ActionListener {
	/* panels for each of the areas on the login screen.
	 * Each has its own method to create it 
	 */
	private JPanel panel = new JPanel(new BorderLayout());
	private JPanel top = new JPanel();
	private JPanel bottom = new JPanel();
	private JPanel right = new JPanel();
	private JPanel center = new JPanel();
	
	// fields used for password entry. Needed here for use in action listener
	private JTextField inputUsername = new JTextField(10);
	private JPasswordField inputPassword = new JPasswordField(10);
	
	/* I don't know what this is for. It suppresses a warning on the class 
	 * "The serializable class does not declare a static final serialVersionUID field?"
	 */
	private static final long serialVersionUID = 1L;
	
	// Constructor: calls the function that creates everything that this GUI uses
	public Login() {
		createGUI();
	}
	
		/* Creates the GUI for this particular page. 
	 * It is a Border Layout Manager with a project header at the top, an area for 
	 * error messages to the right, a register button to the botom and in the center is
	 * the actual content of the Login.
	 */
	public final void createGUI() {
		makeTop();
		makeBottom();
		makeRight();
		makeCenter();
		
		panel.add(top, BorderLayout.NORTH);
		panel.add(bottom, BorderLayout.SOUTH);
		panel.add(right, BorderLayout.EAST);
		panel.add(center, BorderLayout.CENTER);
		add(panel);
		
		setTitle("Set Login Screen");
		
		setSize(400,400);
		//pack();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
	/* The top is the header for the project. 
	 * Still a WIP
	 */
	public void makeTop() {
		// Image for header
		BufferedImage header = null; // is there a cleaner way to do this? I don't like doing it this way.
		try {
			header = ImageIO.read(new File("set_card.png"));
		} catch (IOException ex) {
			// handle exception
		}
		JLabel headerLabel = new JLabel(new ImageIcon(header));
		
		// text for header
		top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
	
		// Create the header text
		// We can come up with a better name later
		String headerText = "The Trembling Triumvirate's Terrific Set";
		JLabel jHeaderText = new JLabel(headerText);
		
		top.add(Box.createRigidArea(new Dimension(0, 5)));
		top.add(headerLabel);
		top.add(jHeaderText);
		
		// Center Aligning the components to the top panel
		headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		jHeaderText.setAlignmentX(Component.CENTER_ALIGNMENT);
	}
	
	// need to add action listener to this guy
	/* Adds the register button and launches the register window when clicked
	 * Still a WIP.
	 */
	public void makeBottom() {
		
		JButton registerButton = new JButton("Register");
		bottom.add(registerButton);
	}
	
	/* Area for login error message
	 * Invisible until incorrect login attempted
	 * Needs listener to communicate with server about that.
	 */
	public void makeRight() {
		right.setLayout(new BoxLayout(right, BoxLayout.X_AXIS));
		right.add(new JSeparator(SwingConstants.VERTICAL)); // creates a vertical line
		
		String errorText = "<html><p><center>Login Failed.<br>" +
				"That username & <br> password do not match  <br>" +
				"any in the system. <br> Please try again <br> or register " +
				"a <br> new username.</center></p></html>";
		JLabel jErrorText = new JLabel(errorText);
		
		// setting font style for the error text
		jErrorText.setForeground(Color.RED);
		
		right.add(Box.createRigidArea(new Dimension(10, 0)));
		right.add(jErrorText);
		right.add(Box.createRigidArea(new Dimension(10, 0))); // why doesn't this seem to create blank space?
		
		right.setVisible(false); 
	}
	
	/* Makes the section of the screen that is the actual login.
	 * A login indicator and below fields to enter your login information.
	 */
	public void makeCenter() {
		// Setting up center panel to have components organized vertically
		center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
		// top portion of center. Login Indicator
		JLabel logLabel = new JLabel("Login");
		logLabel.setFont(new Font("Serif", Font.BOLD, 16));
		logLabel.setAlignmentX(CENTER_ALIGNMENT);
		
		// username input field
		JPanel username = new JPanel();
		username.add(new JLabel("Username"));
		username.add(inputUsername);
		username.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		// password input field
		JPanel password = new JPanel();
		password.add(new JLabel("Password"));
		password.add(inputPassword);
		password.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		// login button
		JButton logButton = new JButton("Login");
		logButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		logButton.addActionListener(this);
		
		// adding in all components and formatting whitespace
		center.add(Box.createRigidArea(new Dimension(0, 50)));
		center.add(logLabel);
		center.add(username);
		center.add(password);
		center.add(Box.createRigidArea(new Dimension(0, 10)));
		center.add(logButton);
		center.add(Box.createRigidArea(new Dimension(0, 50)));
		
	}
	
	/* The action performed when the Login button is pressed.
	 * Needs database querying 
	 */
	public void actionPerformed(ActionEvent e) {
		boolean isCorrect = false;
		String yourUsername = inputUsername.getText();
		char[] yourPassword = inputPassword.getPassword();
		
		// temporary fix while we implement the database querying
		String correctUsername = "cooper!cu";
		char[] correctPassword = {'o', 'p', 'e', 'n', 's', 'e', 's', 'a', 'm', 'e'};

		isCorrect = Arrays.equals(yourPassword, correctPassword) & yourUsername.equals(correctUsername);
		
		if(!isCorrect) {
			right.setVisible(true);
		} else {
			right.setVisible(false); // Go to gaming lobby.
		}
	}

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			public void run() {
				Login log = new Login();
				log.setVisible(true);
			}
		});
	}
}
