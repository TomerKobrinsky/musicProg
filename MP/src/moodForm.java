import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.locks.LockSupport;

public class moodForm {
    private JFrame frame;
    private JPanel panel1;
    private JComboBox textComboBox;
    private JLabel header;
    private JButton nextButton;
    private connectServer client;

    public moodForm(connectServer client, String name) {
        header.setText("Hello "+ name);
        this.client = client;
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.sendMood("MOOD", textComboBox.getSelectedItem().toString(), name);
                //waitDialog dialog = new waitDialog();
                //dialog.setBounds(550,250,400,300);
               // dialog.setVisible(true);
                while (client.getMessagesHandler().getPartnerName() == null) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
                String partnerName = client.getMessagesHandler().getPartnerName();
                int songKeyNum = client.getMessagesHandler().getSongKeyNum();
                //dialog.dispose();
                session session = new session((String) textComboBox.getSelectedItem(), songKeyNum);
                sessionFrame f = new sessionFrame(client, session, new bolleanBar(1), name, partnerName);
                frame.dispose();
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
