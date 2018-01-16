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
    private wait waitHappy;
    private wait waitSad;


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

            PrintStream printStreamClient = new PrintStream(client.getOutputStream());
            // add client message to list
            this.clients.add(printStreamClient);

            // create a new thread for client handling
            new Thread(new ClientHandler(this, printStreamClient, client.getInputStream(), this.clients.size(), this.songs)).start();
        }
    }

    public wait getWaitHappy() {
        return waitHappy;
    }

    public wait getWaitSad() {
        return waitSad;
    }

    public void setWaitHappy(wait waitHappy) {
        this.waitHappy = waitHappy;
    }

    public void setWaitSad(wait waitSad) {
        this.waitSad = waitSad;
    }

    void generateSongsTogether(String song1, String song2, int songTempo) {

        String song = "V0 I[Piano] " + song1 + " V1 I[flute] " + song2;
        for (PrintStream client : this.clients) {
            client.println(song);
            client.println(songTempo);
        }
    }

    public void createPartners(wait waitClient, String name, PrintStream printStreamClient) {
        int songKeyNum = (int) (12 * Math.random());
        waitClient.printStreamClient.println(name);
        waitClient.printStreamClient.println(songKeyNum);
        printStreamClient.println(waitClient.name);
        printStreamClient.println(songKeyNum);
    }
}

class ClientHandler implements Runnable {

    private final String[] songs;
    private int counter;
    private Server server;
    private InputStream clientInputStream;
    private PrintStream printStreamClient;
    private String name;

    public ClientHandler(Server server, PrintStream printStreamClient, InputStream clientInputStream, int size, String[] songs) {
        this.server = server;
        this.clientInputStream = clientInputStream;
        this.printStreamClient = printStreamClient;
        this.counter = size;
        this.songs = songs;
    }

    @Override
    public void run() {
        int songTempo = 60 + (int) (120 * Math.random());

        // when there is a new message, broadcast to all
        Scanner sc = new Scanner(this.clientInputStream);

        while (sc.hasNextLine()) {
            String req = sc.nextLine();
            if(req.equals("MOOD")){
                String mood = sc.nextLine();
                String name = sc.nextLine();
                if(mood.equals("Happy") && server.getWaitHappy() == null){
                    server.setWaitHappy(new wait(name,printStreamClient));
                }else if(mood.equals("Happy") && server.getWaitHappy() != null){
                    server.createPartners(server.getWaitHappy(), name, printStreamClient);
                }else if(mood.equals("Sad") && server.getWaitSad() == null){
                    server.setWaitSad(new wait(name,printStreamClient));
                }else if(mood.equals("Sad") && server.getWaitSad() != null){
                    server.createPartners(server.getWaitSad(), name, printStreamClient);
                }
            }
            else if(counter == 1 && req.equals("SONG")) {
                songs[0] = sc.nextLine();
            }
            else if(counter == 1 && req.equals("SONG")) {
                songs[1] = sc.nextLine();
                server.generateSongsTogether(songs[0], songs[1], songTempo);
            }
        }
        sc.close();

    }
}