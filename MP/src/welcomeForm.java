import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class welcomeForm extends JFrame{
    private JFrame frame;
    private JPanel panel1;
    private JLabel header;
    private JTextField enterYourNameTextField;
    private JButton nextButton;
    private connectServer client;

    public welcomeForm(connectServer client) {
        this.client = client;
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                JFrame frame1 = new JFrame();
                moodForm mf = new moodForm(client,enterYourNameTextField.getText());
                frame1.setContentPane(mf.getPanel());
                mf.setFrame(frame1);
                frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame1.setSize(1500, 1000);
                frame1.setVisible(true);
            }
        });
    }


    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
    public JPanel getPanel() {
        return this.panel1;
    }
}
