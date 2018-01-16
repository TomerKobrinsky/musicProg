
import org.jfugue.pattern.Pattern;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class connectServer {

    private String host;
    private int port;
    private ReceivedMessagesHandler messagesHandler;
    private Socket client;
    private PrintStream output;

    public connectServer(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            this.client = new Socket(host, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Client successfully connected to server!");
        try {
            this.output = new PrintStream(client.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendSong(String req, String song){
        try {
            this.messagesHandler = new ReceivedMessagesHandler(client.getInputStream(), client, output, req);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(this.messagesHandler).start();
        output.println(req);
        output.println(song);
    }
    public void sendName(String req, String name){
        try {
            this.messagesHandler = new ReceivedMessagesHandler(client.getInputStream(), client, output, req);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(this.messagesHandler).start();
        output.println(req);
        output.println(name);
    }

    public ReceivedMessagesHandler getMessagesHandler() {
        System.out.println(this.messagesHandler);
        return messagesHandler;
    }

    public void sendMood(String req, String mood, String name) {
        try {
            this.messagesHandler = new ReceivedMessagesHandler(client.getInputStream(), client, output, req);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(this.messagesHandler).start();
        output.println(req);
        output.println(mood);
        output.println(name);

    }
}

class ReceivedMessagesHandler implements Runnable {
    private Socket client;
    private PrintStream output;
    private InputStream server;
    private String req;
    private String newSong;
    private String tempo;
    private String partnerName;
    private int songKeyNum;


    public ReceivedMessagesHandler(InputStream server, Socket client, PrintStream output, String req) {
        this.server = server;
        this.client = client;
        this.output = output;
        this.req = req;
    }

    @Override
    public void run() {
        Scanner s = new Scanner(server);
        if(req.equals("SONG")){
            this.newSong = s.nextLine();
            this.tempo = s.nextLine();
            System.out.println(newSong+"    "+ tempo);
        }

        else if(req.equals("MOOD")){
            partnerName = s.nextLine();
            songKeyNum = Integer.parseInt(s.nextLine());
            System.out.println(partnerName);
        }
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

    public String getPartnerName() {
        return partnerName;
    }

    public int getSongKeyNum() {
        return songKeyNum;
    }

}