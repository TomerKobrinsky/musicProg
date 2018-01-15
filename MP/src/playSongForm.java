import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class playSongForm {
    private JButton PLAYButton;
    private JPanel panel1;
    private JFrame frame;
    private session a;
    private Pattern p;

    public playSongForm(session a, Pattern p) {
        this.a = a;
        this.p = p;
        PLAYButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Player play = new Player();
                play.play(p);
            }
        });
    }

    public Container getPanel() {
        return panel1;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
}
