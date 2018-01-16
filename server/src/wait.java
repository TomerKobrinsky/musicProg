import java.io.PrintStream;
import java.net.Socket;

public class wait {
    String name;
    PrintStream printStreamClient;

    public wait(String name, PrintStream printStreamClient) {
        this.name = name;
        this.printStreamClient = printStreamClient;
    }
}
