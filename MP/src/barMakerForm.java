import javax.swing.*;
import java.awt.*;

public class barMakerForm {
    private JPanel panel;
    private JButton[] buttons;
    private JFrame frame;

    public barMakerForm(session a) {
        String[] notes = a.getKeyNotes();
        buttons = new JButton[notes.length+1];
        for (int i = 0; i < notes.length; i++){
            buttons[i] = new JButton(notes[i]);
            panel.add(buttons[i]);
        }
        buttons[buttons.length-1] = new JButton("Break");
        panel.add(buttons[buttons.length-1]);

    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JPanel getPanel() {
        return panel;
    }
}
