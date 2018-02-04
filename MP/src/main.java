import javax.swing.*;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

public class main {
    public static void main(String[] args) {
        connectServer client = new connectServer("127.0.0.1", 12345);
        connectServer client2 = new connectServer("127.0.0.1", 12345);
        client2.sendMood("MOOD","Happy", "khen");
        client2.sendSong("SONG","Dw");

        JFrame frame = new JFrame();
        welcomeForm wf = new welcomeForm(client);
        frame.setContentPane(wf.getPanel());
        wf.setFrame(frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1500, 1000); // << not working!!!
        frame.setVisible(true);

//"127.0.0.1"

    }
}
