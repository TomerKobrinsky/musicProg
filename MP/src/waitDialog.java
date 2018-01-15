import javax.swing.*;

public class waitDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;

    public waitDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
    }

}
