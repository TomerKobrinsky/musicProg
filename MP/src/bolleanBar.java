public class bolleanBar {

        private double sumOfLengths;
        private double timeSignature;
        private int barSize;
        private selectedNote[] barNotes;
        int noteToSetIndex;
        private String barToPlay;

        /**
         * constructor for bar
         *
         * @param timeSignature- the time signature of the bar
         */
        public bolleanBar(double timeSignature) {
            sumOfLengths = 0;
            noteToSetIndex = 0;
            this.timeSignature = timeSignature;
            barSize = (int) (timeSignature * 32);
            barNotes = new selectedNote[barSize];
            barToPlay = "";
            // sets new bar with empty notes
            for (int i = 0; i < barNotes.length; i++) {
                barNotes[i] = new selectedNote("empty", 0);
            }
        }

        public boolean setCurrentNote(String newNotePitch, double newLength) {
            if (sumOfLengths < 1) {
                return changeNote(noteToSetIndex, newNotePitch, newLength);
            } else {
                System.out.println("bar full");
                return false;
            }
        }

        /**
         * change a specific note
         *
         * @param noteToSetIndex - the index of the note to set
         * @param newNotePitch   - the pitch of the note
         * @param newLength      - the length of the note
         */
        public boolean changeNote1(int noteToSetIndex, String newNotePitch, double newLength) {
            // the sum of the lengths of the entire notes of the bar(with the new
            // length)
            double newSumOfLengths = sumOfLengths - barNotes[noteToSetIndex].getLength() + newLength;
            // if the index of the note is not in the bar then it's an invalid index


            if ((noteToSetIndex < 0) || (noteToSetIndex > barNotes.length)) {
                System.out.println("Invalid noteToSet: " + noteToSetIndex);
                return false;
            } else
                // if the newSumOfLength is larger than the timeSignature then the new
                // length is too large
                if (newSumOfLengths > this.timeSignature) {
                    System.out.println("length to large");
                    return false;
                } else
                    // length cannot be negative
                    if (newLength < 0) {
                        System.out.println("length is negative");
                        return false;
                    } else
                        //if current note is fake note then it is need for a previous note
                        if (barNotes[noteToSetIndex].getIsFakeNote()) {
                            System.out.println("current note is fake note. cannot change");
                            return false;
                        } else {
                            // otherwise we can set a new note
                            this.noteToSetIndex = noteToSetIndex;
                            double oldLength = barNotes[noteToSetIndex].getLength();
                            barNotes[noteToSetIndex].turnToTrueNote(newNotePitch, newLength);
                            int notesToChange = (int) (32 * newLength);
                            int i = 1;
                            for (; i < notesToChange; i++) {
                                // if we try to override a non empty note then the length of the
                                // changed note is too large
                                // so the note we tried to change get shorter.
                                if (!barNotes[noteToSetIndex + i].getNotePitch().equals("empty")) {
                                    System.out.println("max length is " + i + " / 32");
                                    newSumOfLengths += -barNotes[noteToSetIndex].getLength() + (i / 32.0);
                                    barNotes[noteToSetIndex].setLength(i / 32.0);
                                    break;
                                }
                                barNotes[noteToSetIndex + i].turnToFakeNote();
                            }
                            System.out.println(oldLength);
                            if (newLength < oldLength) {
                                int NotesToDelete = (int) (oldLength * 32) - (int) (newLength * 32) -1;

                                for (; NotesToDelete >= i; NotesToDelete--) {
                                    barNotes[noteToSetIndex + NotesToDelete].turnToTrueNote("empty", 0);
                                }

                            }

                            sumOfLengths = newSumOfLengths;
                        }
            this.noteToSetIndex += (barNotes[noteToSetIndex].getLength() * 32);
            return true;
        }

        /**
         * sets a The Bar to play
         */
        public void setBarToPlay() {
            barToPlay = "";
            for (int i = 0; i < barNotes.length; ) {
                barToPlay += barNotes[i].getNoteToPlay() + " ";
                double noteLength = barNotes[i].getLength();
                if (noteLength > 0) {
                    i += (barNotes[i].getLength() * 32);
                } else {
                    i++;
                }

            }

        }

        /**
         * @return - the string to play
         */
        public String getBarToPlay() {
            return barToPlay;
        }

        public int getBarSize() {
            return barSize;
        }

        public selectedNote getNoteOnIndex(int index) {
            return barNotes[index];
        }

        public boolean isNoteFree(int index) {
            return barNotes[index].isNoteIsFree();
        }

        public void ternToFakeNoteInIndex(int index) {
            barNotes[index].turnToFakeNote();
        }

        public void ternToEmptyNoteInIndex(int index) {
            barNotes[index].turnToTrueNote("empty", 0);
        }

        public boolean isEmptyNoteInIndex(int index) {
            return (barNotes[index].getNotePitch().compareTo("empty") == 0 && !barNotes[index].getIsFakeNote());
        }

        public boolean isFakeNoteOnindex(int index) {
            return barNotes[index].getIsFakeNote();
        }

        public void printBar()
        {
            for(int i = 0 ; i < barSize ; i++)
            {
                System.out.println(i + " : " + barNotes[i].getNotePitch());
            }
        }

    public boolean changeNote(int noteToSetIndex, String newNotePitch, double newLength)
    {
        boolean isValid = true;

        if(barNotes[noteToSetIndex].isNoteIsFree())
        {
            for(int i = noteToSetIndex + 1 ; i < newLength*32 && isValid ; i++)
            {
                if(i >= barNotes.length || !barNotes[i].isNoteIsFree()  )
                {
                    isValid = false;
                }
            }

            if(isValid)
            {
                for(int i = noteToSetIndex + 1 ; i < newLength*32  ; i++)
                {
                    barNotes[i].turnToFakeNote();
                }

                sumOfLengths += newLength;

                barNotes[noteToSetIndex].setLength(newLength);
                barNotes[noteToSetIndex].setNotePitch(newNotePitch);

            }
        }
        else
        {
            for(int i = noteToSetIndex + 1 ; i < newLength*32  ; i++)
            {
                if(i >= barNotes.length || !barNotes[i].getIsFakeNote() )
                {
                    isValid = false;
                }
            }

            if(isValid)
            {

                for (int i = noteToSetIndex + (int)newLength*32 ; i < noteToSetIndex + barNotes[noteToSetIndex].getLength() ; i++)
                {
                    barNotes[i].turnToTrueNote("empty" , 0.0);
                }

                for(int i = noteToSetIndex + 1 ; i < newLength*32 ; i++)
                {
                    barNotes[i].turnToFakeNote();
                }



                sumOfLengths += (newLength - barNotes[noteToSetIndex].getLength());


                barNotes[noteToSetIndex].setLength(newLength);
                barNotes[noteToSetIndex].setNotePitch(newNotePitch);


            }
        }

        return isValid;
    }



}
