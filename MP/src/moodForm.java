import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class moodForm {
    private JFrame frame;
    private JPanel panel1;
    private JComboBox textComboBox;
    private JLabel header;
    private JButton nextButton;
    private session a;

    public moodForm(session a, String name) {
        this.a = a;
        header.setText("Hello "+ name);
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                sessionFrame f = new sessionFrame(a , new bolleanBar(1));
                f.setVisible(true);
            }
        });
    }

    public Container getPanel() {
        return this.panel1;

    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
}
