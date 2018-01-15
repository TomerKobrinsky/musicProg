import org.jfugue.pattern.Pattern;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {

    private int port;
    private List<PrintStream> clients;
    private ServerSocket server;
    private String[] songs;

    public static void main(String[] args) throws IOException {
        new Server(12345).run();
    }

    public Server(int port) {
        this.port = port;
        this.clients = new ArrayList<PrintStream>();
        this.songs = new String[2];
    }

    public void run() throws IOException {
        server = new ServerSocket(port) {
            protected void finalize() throws IOException {
                this.close();
            }
        };
        System.out.println("Port 12345 is now open.");

        while (true) {
            // accepts a new client
            Socket client = server.accept();
            System.out.println("Connection established with client: " + client.getInetAddress().getHostAddress());

            // add client message to list
            this.clients.add(new PrintStream(client.getOutputStream()));

            // create a new thread for client handling
            new Thread(new ClientHandler(this, client.getInputStream(), this.clients.size(), this.songs)).start();
        }
    }

    void generateSongsTogether(String song1, String song2, int songTempo) {

        String song = "V0 I[Piano] " + song1 + " V1 I[flute] " + song2;
        for (PrintStream client : this.clients) {
            client.println(song);
            client.println(songTempo);
        }
    }
}

class ClientHandler implements Runnable {

    private final String[] songs;
    private int counter;
    private Server server;
    private InputStream client;

    public ClientHandler(Server server, InputStream client, int size, String[] songs) {
        this.server = server;
        this.client = client;
        this.counter = size;
        this.songs = songs;
    }

    @Override
    public void run() {
        int songTempo = 60 + (int) (120 * Math.random());


        // when there is a new message, broadcast to all
        Scanner sc = new Scanner(this.client);
        while (sc.hasNextLine()) {
            if(counter == 1)
                songs[0] = sc.nextLine();
            if(counter == 2) {
                songs[1] = sc.nextLine();
                server.generateSongsTogether(songs[0], songs[1], songTempo);
            }
        }
        sc.close();
    }
}