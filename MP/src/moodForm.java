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
                JFrame frame1 = new JFrame();
                barMakerForm bm = new barMakerForm(a);
                frame1.setContentPane(bm.getPanel());
                bm.setFrame(frame1);
                frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame1.setSize(1500, 1000); // << not working!!!
                frame1.setVisible(true);
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
