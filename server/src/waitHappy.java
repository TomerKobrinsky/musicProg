import java.io.PrintStream;
import java.net.Socket;

public class waitHappy {
    String name;
    PrintStream printStreamClient;

    public waitHappy(String name, PrintStream printStreamClient) {
        this.name = name;
        this.printStreamClient = printStreamClient;
    }
}
