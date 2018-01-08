
import org.jfugue.pattern.Pattern;

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
        Socket client = new Socket(host, port);
        System.out.println("Client successfully connected to server!");
        PrintStream output = new PrintStream(client.getOutputStream());

        // create a new thread for server messages handling
        this.messagesHandler = new ReceivedMessagesHandler(client.getInputStream(), client, output);
        new Thread(this.messagesHandler).start();

        output.println(song);


    }

    public ReceivedMessagesHandler getMessagesHandler() {
        System.out.println(this.messagesHandler);
        return messagesHandler;
    }
}

class ReceivedMessagesHandler implements Runnable {
    private Socket client;
    private PrintStream output;
    private InputStream server;
    private String newSong;
    private String tempo;


    public ReceivedMessagesHandler(InputStream server, Socket client, PrintStream output) {
        this.server = server;
        this.client = client;
        this.output = output;
        this.newSong = null;
        this.tempo = null;
    }

    @Override
    public void run() {
        // receive server messages and print out to screen
        Scanner s = new Scanner(server);
        this.newSong = s.nextLine();
        this.tempo = s.nextLine();
        System.out.println(newSong+"    "+ tempo);
        s.close();
        try {
            output.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNewSong() {
        return newSong;
    }

    public String getTempo() {
        return tempo;
    }
}