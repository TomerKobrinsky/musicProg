import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class notesDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonDelete;
    private JComboBox durationBox;
    private JButton[] buttons;
    private session a;
    private JLabel label1;
    private JLabel label2;
    private String chord;
    private double duration;
    private JButton selectedButton;
    private selectedNote noteToPlay;
   // private Pattern patternToPlay;
    private Player player;


    public notesDialog(session a) {
        this.a = a;
        setLayout(null);

        player = new Player();
        noteToPlay = new selectedNote("empty" , 0);

        setModal(true);
        getRootPane().setDefaultButton(buttonOK);


        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onDelete();
            }
        });

        buttonDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onDelete();
            }
        });



        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        Container pane = getContentPane();
        pane.setLayout(null);
        createButtons(pane);


    }

    private void createButtons(Container pane) {
        ActionListener buttonAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(chord != null)
                {
                    selectedButton.setEnabled(true);
                    selectedButton.setBackground(null);
                }
                JButton b = (JButton) e.getSource();
                b.setOpaque(true);
                b.setEnabled(false);
                b.setBackground(Color.green);

                if(b.getText().compareTo("Rest") == 0)
                {
                    chord = "R";
                }
                else
                    {

                    chord = b.getText();
                    noteToPlay.setNotePitch(chord);

                    playNote(0.5);
                }
                selectedButton = b;
            }
        };

        String[] notes = this.a.getKeyNotes();
        buttons = new JButton[notes.length+1];
        int x = 0;
        for (int i = 0; i < notes.length; i++){
            buttons[i] = new JButton(notes[i]);
            buttons[i].setBounds(10 + x,30,40,40);
            buttons[i].addActionListener(buttonAction);
            pane.add(buttons[i]);
            x += 40;
        }
        buttons[buttons.length-1] = new JButton("Rest");
        buttons[buttons.length-1].setBounds(100,75,100,25);
        buttons[buttons.length-1].addActionListener(buttonAction);
        pane.add(buttons[buttons.length-1]);
        this.buttonOK.setBounds(230,200,70,30);
        this.buttonDelete.setBounds(0,200,70,30);
        this.buttonDelete.setText("Delete");
        String[] list = { "1", "1/2","1/4" , "1/8", "1/16", "1/32" };
        this.durationBox = new JComboBox(list);
        this.durationBox.setBounds(100, 110,100,100);
        this.label1 = new JLabel("Choose Chord");
        this.label2 = new JLabel("Choose Duration");
        this.label1.setBounds(100,5,100,20);
        this.label2.setBounds(100,120,200,20);

        pane.add(this.label1);
        pane.add(this.label2);
        pane.add(this.buttonOK);
        pane.add(this.buttonDelete);
        pane.add(this.durationBox);


    }

    private void onOK() {

        String durationString = this.durationBox.getSelectedItem().toString();
        switch (durationString) {
            case "1":
                duration = 1;
                break;
            case "1/2":
                duration = 1/2.0;
                break;
            case "1/4":
                duration = 1/4.0;
                break;
            case "1/8":
                duration = 1/8.0;
                break;
            case "1/16":
                duration = 1/16.0;
                break;
            case "1/32":
                duration = 1/32.0;
                break;
        }
        if (this.chord != null) {
            dispose();
        }

        //playNote(duration);
    }

    private void onDelete()
    {
        this.chord = "empty";
        this.duration = 0;

        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public double getDuration() {
        return duration;
    }

    public String getChord() {
        return chord;
    }

    public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

    public void playNote(double duration)
    {
        if(player != null)
        {
            noteToPlay.setLength(duration);
            noteToPlay.setNoteToPlay();

            Pattern patternToPlay = new Pattern(noteToPlay.getNoteToPlay());
            patternToPlay.setTempo(session.songTempo);
            player.play(patternToPlay);
        }
        else
        {
            System.out.println("BOO");
        }
    }

}
