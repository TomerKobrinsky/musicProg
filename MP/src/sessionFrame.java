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
    private bar frameBar;
    private session frameSsession;

    public sessionFrame(session session , bar bar )
    {
        this.frameSsession = session;
        frameBar = bar;
        int barSize = bar.getBarSize();


        panel1 = new JPanel();
        panel1.setLayout(null);

        panel1.setSize(750 , 500);
        this.setContentPane(panel1);
        this.setSize(750, 500);


        buttons = new JButton[barSize];
        labels = new JLabel[barSize];

       JButton play = new JButton();


        ActionListener t  = (new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {

                JButton b = (JButton)e.getSource();
                int buttonIndex = Integer.parseInt(b.getName());

                if(!frameBar.isFakeNoteOnindex(buttonIndex))
                {
                    notesDialog dialog = new notesDialog(frameSsession);
                    dialog.setSize(300, 260);
                    dialog.setVisible(true);

                    sessionFrame f = ((sessionFrame) b.getParent().getParent().getParent().getParent());


                    if (dialog.getChord() != null)
                    {

                        f.setLabel(buttonIndex, "");
                        b.setBackground(null);
                        frameBar.ternToEmptyNoteInIndex(buttonIndex);

                        int j = 1;
                        while(j + buttonIndex  < barSize && frameBar.isFakeNoteOnindex(j + buttonIndex))
                        {
                            buttons[buttonIndex + j].setBackground(null);
                            frameBar.ternToEmptyNoteInIndex(buttonIndex + j );
                            j++;
                        }

                        while (dialog.getDuration() != 0 && (dialog.getDuration() * 32 + buttonIndex -1 > barSize || !f.isValidDur(buttonIndex, (int) (dialog.getDuration() * 32))))
                        {
                            notesDialog.infoBox("!!!", "!!!");
                            dialog.setVisible(true);
                        }


                        if(dialog.getChord().compareTo("empty") != 0 )
                        {

                            f.setLabel(buttonIndex, dialog.getChord());

                            Random r = new Random();
                            Color randomColor = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat());

                            b.setOpaque(true);
                            b.setBackground(randomColor);

                            frameBar.changeNote(buttonIndex , dialog.getChord() , dialog.getDuration());

                            for (int i = 1; i < dialog.getDuration() * 32; i++)
                            {
                                buttons[buttonIndex + i].setOpaque(true);
                                buttons[buttonIndex + i].setBackground(randomColor);
                                frameBar.ternToFakeNoteInIndex(buttonIndex + i);
                            }
                        }

                    }

                }
            }
        });


      ActionListener a  = (new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {

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

                      System.out.println(i + " " + counter);
                      labels[i].setText("R");
                      frameBar.changeNote( i , "R" , (counter/32.0));
                      i = j;
                  }
                  else
                  {
                       i++;
                  }
              }

             frameBar.setBarToPlay();
             Player play = new Player();
             Pattern firstPattern = new Pattern(bar.getBarToPlay());
             firstPattern.setTempo(session.songTempo);
             System.out.println(frameBar.getBarToPlay());


          }
      });

        play.setName("Play");

        play.addActionListener(a);

        play.setBounds(10, 300, 100 ,50);
        panel1.add(play);                                     

        int counter = 0;
        int sum = 10;

        for(int i = 0; i < barSize; i++)
        {
            if(counter == 4)
            {
                sum += 7;
                counter = 0;
            }
            labels[i] = new JLabel();
            //labels[i].setText("" + i);
            buttons[i] = new JButton("");
            //buttons[i].setFont(new Font("Ariel", Font.PLAIN, 10));
            buttons[i].setName("" + i);
            //buttons[i].setLayout(null);
            //buttons[i].getParent().getParent();
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
            if(!frameBar.isNoteFree(index + i)) {
                isFree = false;
                break;
            }
        }


        return isFree;
    }

}
