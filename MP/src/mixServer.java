
import java.io.IOException;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;

public class mixServer {
    private HttpServer server;

    public mixServer() throws IOException {
        try {
            this.server = HttpServer.create(new InetSocketAddress(8000), 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.createContext("/",new myHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

}