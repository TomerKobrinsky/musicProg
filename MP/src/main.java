import javax.swing.*;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

public class main {
	public static void main(String[] args){

		session a= new session();

		JFrame frame = new JFrame();
		welcomeForm wf = new welcomeForm(a);
		frame.setContentPane(wf.getPanel());
		wf.setFrame(frame);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1500, 1000); // << not working!!!
		frame.setVisible(true);
<<<<<<< HEAD
		*/
=======
>>>>>>> 19c1bc037488a0a6787255eca0d0ec0abf4bfce3

	}
}
