import org.jfugue.pattern.Pattern;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class playSongForm {
    private JButton PLAYButton;
    private JPanel panel1;
    private JFrame frame;

    public playSongForm(session a, Pattern p) {

    }

    public Container getPanel() {
        return panel1;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
}
