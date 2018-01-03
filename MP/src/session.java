public class session {
	public enum Mood {
		Happy, Sad
	}

	public enum keyType {
		Major, Minor
	}

	private String keyNotes[] = new String[7];
	private String chordNotes[] = new String[7];

	static int songTempo;
	static keyType songKeyType;
	static String songKey;
	static int songKeyNum;

	/**
	 * sets the tempo of the song
	 */
	private void setTempo() {
		songTempo = 60 + (int) (120 * Math.random());
	}

	private String intToStringNote(int input) {
		switch (input) {
		case 0:
			return "C";
		case 1:
			return "C#";

		case 2:
			return "D";

		case 3:
			return "D#";

		case 4:
			return "E";

		case 5:
			return "F";

		case 6:
			return "F#";

		case 7:
			return "G";

		case 8:
			return "G#";

		case 9:
			return "A";

		case 10:
			return "A#";

		case 11:
			return "B";

		default:
			System.err.println("error with note");
			return null;
		}
	}

	/**
	 * sets the key of the song
	 */
	private void setKeyRoot() {
		songKeyNum = (int) (12 * Math.random());
		songKey = intToStringNote(songKeyNum);
	}

	/**
	 * sets keyType according to The Mood Input
	 * 
	 * @param Input
	 */
	private void setKeyType(Mood Input) {
		switch (Input) {
		case Happy:
			songKeyType = session.keyType.Major;
			break;
		case Sad:
			songKeyType = session.keyType.Minor;
			break;
		default:
			System.err.println("error with keyType");
			break;
		}
	}

	/**
	 * sets the key of the song and the relevant notes to use
	 * 
	 * @param Input
	 *            - the mood the user is interested in
	 */
	private void setKey(Mood Input) {
		setKeyRoot();
		setKeyType(Input);

		switch (songKeyType) {
		case Major:
			for (int i = 0, currentNote = songKeyNum; i < keyNotes.length; i++) {
				keyNotes[i] = intToStringNote(currentNote);
				currentNote += 2;
				if (i == 2) {
					currentNote--;
				}
				if (currentNote > 11) {
					currentNote -= 12;
				}
			}
			break;
		case Minor:
			for (int i = 0, currentNote = songKeyNum; i < keyNotes.length; i++) {
				keyNotes[i] = intToStringNote(currentNote);
				currentNote += 2;
				if ((i == 1) || (i == 4)) {
					currentNote--;
				}
				if (currentNote > 11) {
					currentNote -= 12;
				}
			}
			break;

		}

	}

	private void setChords() {
		chordNotes = keyNotes.clone();
		switch (songKeyType) {
		case Major:
			chordNotes[0] += "maj";
			chordNotes[1] += "min";
			chordNotes[2] += "min";
			chordNotes[3] += "maj";
			chordNotes[4] += "maj";
			chordNotes[5] += "min";
			chordNotes[6] += "dim";
			break;

		case Minor:
			chordNotes[0] += "min";
			chordNotes[1] += "dim";
			chordNotes[2] += "maj";
			chordNotes[3] += "min";
			chordNotes[4] += "min";
			chordNotes[5] += "maj";
			chordNotes[6] += "maj";
			break;
		}
	}

	/**
	 * initialize the current session
	 */
	public session() {
		Mood Input = Mood.Happy;
		setKey(Input);
		setChords();
		setTempo();

	//	System.out.println("the tempo is: " + songTempo);
	//	System.out.println("the key is: " + songKey + songKeyType);
		//Player play = new Player();
	//	for (int i = 0; i < keyNotes.length; i++) {
	//		System.out.println(keyNotes[i]);
		//	play.play(chordNotes[i] + "W ");
	//	}
	//	play.play(chordNotes[0] + "W ");
		//System.out.println(chordNotes[0]);
	}

	public String[] getKeyNotes() {
		return keyNotes;
	}

	public static String getSongKey() {
		return songKey;
	}

	public static int getSongTempo() {
		return songTempo;
	}
	public String[] getChordNotes() {
		return chordNotes;
	}

}
