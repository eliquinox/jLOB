import static connectivity.fix.FixClient.clientRunnable;
import static connectivity.fix.FixServer.serverRunnable;

public class LimitOrderBookFixApplication {
    public static void main(String[] args) {
        new Thread(serverRunnable).start();
        new Thread(clientRunnable).start();
    }
}
