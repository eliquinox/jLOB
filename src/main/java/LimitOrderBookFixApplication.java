import quickfix.ConfigError;
import quickfix.SessionNotFound;

import static connectivity.fix.FixClient.clientRunnable;
import static connectivity.fix.FixServer.serverRunnable;

public class LimitOrderBookFixApplication {
    public static void main(String[] args) throws ConfigError, InterruptedException, SessionNotFound {
        new Thread(serverRunnable).start();
        new Thread(clientRunnable).start();
    }
}
