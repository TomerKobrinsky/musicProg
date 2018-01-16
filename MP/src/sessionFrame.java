import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
public class sessionFrame extends JFrame {

    private JPanel panel1;
    private JButton buttons[];
    private JLabel labels[];
    private bolleanBar frameBar;
    private session frameSsession;
    private JButton nextButton;
    private connectServer client;
    private JLabel header;

    public sessionFrame(connectServer client, session session, bolleanBar bar, String myName, String partnerName)
    {
        this.client = client;
        frameSsession = session;
        frameBar = bar;

        int barSize = bar.getBarSize();

        buttons = new JButton[barSize];
        labels = new JLabel[barSize];
        panel1 = new JPanel();

        panel1.setLayout(null);
        panel1.setBackground(new Color(238,248,255));

        setContentPane(panel1);
        setSize(1500, 1000);

        header = new JLabel();
        header.setText("Hey "+myName+" you're gonna create a song with "+ partnerName);
        header.setBounds(300,10, 600,200);
        panel1.add(header);
        initBarButtonsAndLabels();


    }

    public void setLabel(int index , String text)
    {
        labels[index].setText(text);
    }

    public boolean isValidDur(int index , int len)
    {
        boolean isFree = true;

        for(int i = 1 ; i < len ; i++)
        {
            if(!frameBar.isEmptyNoteInIndex(index + i)) {
                isFree = false;
                break;
            }
        }


        return isFree;
    }

    private void initBarButtonsAndLabels()
    {
        JLabel labelBar = new JLabel();
        JButton play = new JButton();
        int barSize = frameBar.getBarSize();

        play.setText("Play");

        play.addActionListener(creatPlayAction());

        labelBar.setText("Bar");

        play.setBounds(400, 400, 100 ,50);
        panel1.add(play);

        nextButton = new JButton("Next");
        nextButton.setBounds(900, 400, 100 ,50);
        nextButton.addActionListener(creatNextAction());
        panel1.add(nextButton);

        int counter = 0;
        int sum = 350;

        for(int i = 0; i < barSize; i++)
        {
            if(counter == 4)
            {
                sum += 7;
                counter = 0;
            }
            labels[i] = new JLabel();
            buttons[i] = new JButton("");
            buttons[i].setName("" + i);
            buttons[i].setBounds(sum + i*20, 200, 20 ,60);
            labels[i].setBounds(sum + 2 + i*20, 180, 20 ,20);
            buttons[i].addActionListener(creatBarButtonAction());

            panel1.add(labels[i]);
            panel1.add(buttons[i]);

            counter++;
        }

    }

    private ActionListener creatBarButtonAction()
    {
        ActionListener barButtonAction   = (new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JButton b = (JButton)e.getSource();
                int buttonIndex = Integer.parseInt(b.getName());
                if(!frameBar.isFakeNoteOnindex(buttonIndex))
                {
                    Color currentColor = b.getBackground();
                    b.setBackground(Color.green);
                    notesDialog dialog = new notesDialog(frameSsession);
                    dialog.setSize(300, 260);
                    dialog.setVisible(true);

                    sessionFrame f = ((sessionFrame) b.getParent().getParent().getParent().getParent());


                    if (dialog.getChord() != null)
                    {
                        f.setLabel(buttonIndex, "");
                        boolean isValid;

                        isValid = frameBar.changeNote(buttonIndex , dialog.getChord() , dialog.getDuration());

                        while (dialog.getDuration() != 0 && !isValid) //(dialog.getDuration() * 32 + buttonIndex - 1 > barSize || !f.isValidDur(buttonIndex , (int) (dialog.getDuration() * 32))))
                        {

                            notesDialog.infoBox("cant choose this duration", "error");
                            dialog.setVisible(true);
                            isValid = frameBar.changeNote(buttonIndex , dialog.getChord() , dialog.getDuration());

                        }

                        boolean c = false;

                        if(dialog.getChord().compareTo("empty") != 0 )
                        {
                            f.setLabel(buttonIndex, dialog.getChord());

                             c = setColor(b,buttonIndex,dialog);

                        }

                        for (int i = buttonIndex + (int)dialog.getDuration()*32; i < frameBar.getBarSize(); i++)
                        {
                            if(frameBar.isNoteFree(i))
                                buttons[i].setBackground(null);
                        }
/*
                        while (!c) {

                            try {

                                wait();
                            } catch (Exception e1) {

                                System.out.println("ASDSDsd");

                            }
                        }

                        if(buttons[buttonIndex + (int)dialog.getDuration() * 32 - 1].getBackground() != null) {
                            selectedNote n = new selectedNote(dialog.getChord(), dialog.getDuration());
                            n.setNoteToPlay();
                            Player play = new Player();
                            Pattern firstPattern = new Pattern(n.getNoteToPlay());
                            firstPattern.setTempo(session.songTempo);
                            System.out.println("AAAAAAAAAAAAAAAA");
                            play.play(firstPattern);
                        }
*/
                    }
                    else
                    {
                        b.setBackground(currentColor);
                    }

                   //dialog.playNote(dialog.getDuration());

                }
            }
        });
        return barButtonAction;
    }

    private ActionListener creatPlayAction()
    {

        ActionListener playAction  = (new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameBar.setBarToPlay();
                Player play = new Player();
                Pattern firstPattern = new Pattern(frameBar.getBarToPlay());
                firstPattern.setTempo(session.songTempo);
                play.play(firstPattern);
            }
        });

        return  playAction;
    }

    private ActionListener creatNextAction()
    {
        ActionListener nextAction = (new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                frameBar.setBarToPlay();
                String song = frameBar.getBarToPlay();

                client.sendSong("SONG", song);
                //waitDialog dialog = new waitDialog();
                //dialog.setBounds(550, 250, 400, 300);
                //dialog.setVisible(true);
                while (client.getMessagesHandler().getNewSong() == null) {
                    try {
                        System.out.println("waiting");
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
                //dialog.dispose();
                Pattern p = new Pattern(client.getMessagesHandler().getNewSong());
                int tempo = Integer.parseInt(client.getMessagesHandler().getTempo());

                dispose();
                JFrame frame = new JFrame();
                playSongForm form = new playSongForm(frameSsession, p, tempo);
                frame.setContentPane(form.getPanel());
                form.setFrame(frame);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(1500, 1000); // << not working!!!
                frame.setVisible(true);

            }
        });

        return nextAction;
    }

    private boolean setColor(JButton b , int buttonIndex , notesDialog dialog)
    {
        Random r = new Random();
        Color randomColor = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat());

        b.setOpaque(true);
        b.setBackground(randomColor);

        for (int i = 1; i < dialog.getDuration() * 32; i++)
        {
            buttons[buttonIndex + i].setOpaque(true);
            buttons[buttonIndex + i].setBackground(randomColor);
            if(i == dialog.getDuration() * 32 - 1)
            {
               return true;
            }
        }

        return false;
    }
}
