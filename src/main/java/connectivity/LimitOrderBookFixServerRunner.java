package connectivity;

import static connectivity.fix.FixClient.clientRunnable;
import static connectivity.fix.FixServer.serverRunnable;

public class LimitOrderBookFixServerRunner implements ServerRunner {
    @Override
    public void run() {
        new Thread(serverRunnable).start();
        new Thread(clientRunnable).start();
    }
}
