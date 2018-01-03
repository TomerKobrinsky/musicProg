
import javax.swing.*;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

public class testForBarAndSession {
    public static void main(String[] args) {
        session a = new session();
        String[] notes = new String[a.getKeyNotes().length + 1];
        for (int i = 0; i < a.getKeyNotes().length; i++) {
            notes[i] = a.getKeyNotes()[i];
        }
        notes[a.getKeyNotes().length] = notes[0];
        bar bar = new bar(4 / 4);
        for (int i = 0; i < notes.length; i++) {
            bar.setCurrentNote(notes[i], 0.125);

            //bar.setCurrentNote(notes[a.getKeyNotes().length], 0.5);
            //bar.setCurrentNote(notes[0], 0.125);
        }
        bar.setBarToPlay();
        System.out.println(bar.getBarToPlay());
        bar.changeNote(0, notes[3], 0.0625);
        //bar.changeNote(0, notes[2], 0.5);
        //	bar.changeNote(2, notes[6], 0.125);
        //bar.setCurrentNote("empty", 0.125);
        bar.setBarToPlay();
        Player play = new Player();
        Pattern firstPattern = new Pattern(bar.getBarToPlay());
        firstPattern.setTempo(session.songTempo);

        play.play(firstPattern);
        System.out.println(bar.getBarToPlay());


    }
}


