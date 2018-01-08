import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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

    public sessionFrame(session session , bolleanBar bar )
    {
        this.frameSsession = session;
        frameBar = bar;
        int barSize = bar.getBarSize();


        panel1 = new JPanel();
        panel1.setLayout(null);

        this.setContentPane(panel1);
        setSize(1500, 1000);
        panel1.setBackground(new Color(238,248,255));



        buttons = new JButton[barSize];
        labels = new JLabel[barSize];

        JLabel labelBar = new JLabel();

        JButton play = new JButton();


        ActionListener t  = (new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {

                JButton b = (JButton)e.getSource();
                int buttonIndex = Integer.parseInt(b.getName());

               // System.out.println(frameBar.isEmptyNoteInIndex(buttonIndex));
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

                        //                       // frameBar.changeNote(buttonIndex , "empty" , 0);


                        while (dialog.getDuration() != 0 && !isValid) //(dialog.getDuration() * 32 + buttonIndex - 1 > barSize || !f.isValidDur(buttonIndex , (int) (dialog.getDuration() * 32))))
                        {

                            notesDialog.infoBox("cant choose this duration", "error");
                            dialog.setVisible(true);
                            isValid = frameBar.changeNote(buttonIndex , dialog.getChord() , dialog.getDuration());

                        }

                        if(dialog.getChord().compareTo("empty") != 0 )
                        {
                            f.setLabel(buttonIndex, dialog.getChord());

                            Random r = new Random();
                            Color randomColor = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat());

                            b.setOpaque(true);
                            b.setBackground(randomColor);

                            for (int i = 1; i < dialog.getDuration() * 32; i++)
                            {
                                buttons[buttonIndex + i].setOpaque(true);
                                buttons[buttonIndex + i].setBackground(randomColor);
                                //frameBar.ternToFakeNoteInIndex(buttonIndex + i);
                            }
                        }

                        for (int i = buttonIndex + (int)dialog.getDuration()*32; i < barSize; i++)
                        {
                            if(frameBar.isNoteFree(i))
                            buttons[i].setBackground(null);
                            //frameBar.ternToFakeNoteInIndex(buttonIndex + i);
                        }

                    }
                    else
                    {
                        b.setBackground(currentColor);
                    }

                }
            }
        });


      ActionListener a  = (new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {

              /*
              int i = 0 ;
              int counter;
              int j;
              while(i < barSize)
              {
                  if(frameBar.isEmptyNoteInIndex(i))
                  {
                      counter = 1;
                      j = i + 1;
                      while(j < barSize && frameBar.isEmptyNoteInIndex(j))
                      {
                          counter++;
                          j++;
                      }

                      if(j < barSize)
                      {
                        // counter--;
                      }

                      frameBar.changeNote( i , "R" , (counter/32.0));
                      i = j;
                  }
                  else
                  {
                       i++;
                  }
              }
              */
           //   frameBar.printBar();

            //  if()
             frameBar.setBarToPlay();

            // for(int i < x)

             Player play = new Player();
             Pattern firstPattern = new Pattern(bar.getBarToPlay());
             firstPattern.setTempo(session.songTempo);
             System.out.println(frameBar.getSumOfLengths());


          }
      });

        ActionListener nextAction = (new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameBar.setBarToPlay();
                String song = frameBar.getBarToPlay();

                try {
                    sendSong send = new sendSong("192.168.0.108", 12345, song);
                    send.run();
                    while (send.getMessagesHandler().getNewSong() == null){
                        try {
                           Thread.sleep(1000);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                    System.out.println(send.getMessagesHandler().getNewSong());
                    Pattern p = send.getMessagesHandler().getNewSong();
                    p.setTempo(Integer.parseInt(send.getMessagesHandler().getTempo()));

                    dispose();
                    JFrame frame = new JFrame();
                    playSongForm form = new playSongForm(session, p);
                    frame.setContentPane(form.getPanel());
                    form.setFrame(frame);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setSize(1500, 1000); // << not working!!!
                    frame.setVisible(true);

                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        });



                play.setText("Play");

        play.addActionListener(a);

        labelBar.setText("Bar");
        //panel1.


        play.setBounds(400, 400, 100 ,50);
        panel1.add(play);

        nextButton = new JButton("Next");
        nextButton.setBounds(900, 400, 100 ,50);
        nextButton.addActionListener(nextAction);
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
            buttons[i].addActionListener(t);

            panel1.add(labels[i]);
            panel1.add(buttons[i]);

            counter++;
        }
    }

    public void setLabel(int index , String text)
    {
        labels[index].setText(text);
    }

    public void setLButtonsEnabled(int index , boolean enabled) {
        buttons[index].setEnabled(enabled);
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

}
