
public class selectedNote {
	// will hold the pitch of the note
	private String notePitch;
	// will hold the length of the note
	private double length;
	// will hold the valid note to play woth length
	private String noteToPlay;
	private boolean isFakeNote;

	/**
	 * constructor
	 * 
	 * @param notePitch
	 *            - the pitch of the note
	 * @param length
	 *            - the length of the note
	 */
	public selectedNote(String notePitch, double length) {
		setNotePitch(notePitch);
		if (notePitch.equals("empty")) {
			this.length = 0;
		} else {
			setLength(length);
		}
		setNoteToPlay();
		isFakeNote = false;
	}

	/**
	 * turn this note to FakeNote
	 */
	public void turnToFakeNote() {
		this.notePitch = "empty";
		this.isFakeNote = true;
		this.length = 0;
		this.noteToPlay = "";
		setNoteToPlay();
	}

	public void turnToTrueNote(String notePitch, double length) {
		setNotePitch(notePitch);
		if (notePitch.equals("empty")) {
			this.length = 0;
		} else {
			setLength(length);
		}
		setNoteToPlay();
		isFakeNote = false;
	}

	/**
	 * setter for the length of the note
	 * 
	 * @param length
	 */
	public void setLength(double length) {
		// (length <= 1) &&
		if ((length > 0)) {
			this.length = length;
			setNoteToPlay();
		} else {
			System.out.println("constructed a note with invalid length");
		}
	}

	/**
	 * setter for noteToPlay
	 */
	public void setNoteToPlay() {
		parsingWithLength();
	}

	/**
	 * setter for the pitch of the note
	 * 
	// * @param pitch
	 */
	public void setNotePitch(String notePitch) {
		this.notePitch = notePitch;
		setNoteToPlay();
	}

	/**
	 * getter for the length of the note
	 * 
	// * @param length
	 */
	public double getLength() {
		return length;
	}

	/**
	 * getter for the pitch of the note
	 * 
	// * @param pitch
	 */
	public String getNotePitch() {
		return notePitch;
	}

	public String getNoteToPlay() {
		return noteToPlay;
	}
	public Boolean getIsFakeNote(){
		return this.isFakeNote;
	}
	
	

	/**
	 * parsing to noteToPlay according to jfugue
	 * 
	 * @return noteToPLAY - Valid jfugue note
	 */
	private String parsingWithLength() {
		String noteWithLength = getNotePitch();
		double thislength = getLength();
		// length is 1
		if (thislength == 1) {
			this.noteToPlay = noteWithLength + "w";
		} else
		// length is 1/2
		if (thislength == 0.5) {
			this.noteToPlay = noteWithLength + "h";
		} else
		// length is 1/4
		if (thislength == 0.25) {
			this.noteToPlay = noteWithLength + "q";
		} else
		// length is 1/8
		if (thislength == 0.125) {
			this.noteToPlay = noteWithLength + "i";
		} else
		// length is 1/16
		if (thislength == 0.0625) {
			this.noteToPlay = noteWithLength + "s";
		} else
		// length is 1/32
		if (thislength == 0.5) {
			this.noteToPlay = noteWithLength + "t";
		} else
		// length is 0
		if (thislength == 0) {
			this.noteToPlay = "";
		}
		return this.noteToPlay;
	}

	public boolean isNoteIsFree()
	{
		boolean isFree = false;
		if(notePitch.compareTo("empty") == 0 && (!isFakeNote) )
		{
			isFree = true;
		}

		return isFree;
	}
}
