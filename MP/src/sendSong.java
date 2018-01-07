
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class sendSong {

    private String host;
    private int port;
    private String song;
    private ReceivedMessagesHandler messagesHandler;


    public sendSong(String host, int port, String song) {
        this.host = host;
        this.port = port;
        this.song = song;
    }

    public void run() throws UnknownHostException, IOException {
        // connect client to server
        Socket client = new Socket(host, port);
        System.out.println("Client successfully connected to server!");

        // create a new thread for server messages handling
        this.messagesHandler = new ReceivedMessagesHandler(client.getInputStream());
        new Thread(messagesHandler).start();

        Scanner sc = new Scanner(System.in);
        // read messages from keyboard and send to server
        System.out.println("Send messages: ");
        PrintStream output = new PrintStream(client.getOutputStream());
        System.out.println(song);
        output.close();
        sc.close();
        client.close();

    }

    public ReceivedMessagesHandler getMessagesHandler() {
        return messagesHandler;
    }
}

class ReceivedMessagesHandler implements Runnable {

    private InputStream server;
    private String newSong;
    private String tempo;


    public ReceivedMessagesHandler(InputStream server) {
        this.server = server;
        this.newSong = null;
        this.tempo = null;
    }

    @Override
    public void run() {
        // receive server messages and print out to screen
        Scanner s = new Scanner(server);
        while (s.hasNextLine()) {
            newSong = s.nextLine();
        }
        s.close();
    }

    public String getNewSong() {
        return newSong;
    }

    public String getTempo() {
        return tempo;
    }
}