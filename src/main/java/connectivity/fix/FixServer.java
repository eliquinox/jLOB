package connectivity.fix;

import quickfix.*;
import quickfix.field.MsgType;
import state.LimitOrderBook;

import java.util.concurrent.CountDownLatch;

public class FixServer implements Application {

    private final LimitOrderBook LIMIT_ORDER_BOOK = LimitOrderBook.empty();

    public static Runnable serverRunnable = () -> {
        try {
            start();
        } catch (InterruptedException | ConfigError e) {
            e.printStackTrace();
        }
    };


    @Override
    public void onCreate(SessionID sessionId) {
        System.out.println("On create");
    }

    @Override
    public void onLogon(SessionID sessionId) {
        System.out.println("On logon");
    }

    @Override
    public void onLogout(SessionID sessionId) {
        System.out.println("On logout");
    }

    @Override
    public void toAdmin(Message message, SessionID sessionId) {
        System.out.println("To admin " + message.toXML());
    }

    @Override
    public void fromAdmin(Message message, SessionID sessionId) {
        System.out.println("From admin " + message.toXML());
    }

    @Override
    public void toApp(Message message, SessionID sessionId) {
        System.out.println("To app " + message.toXML());
    }

    @Override
    public void fromApp(Message message, SessionID sessionId) throws FieldNotFound {
        System.out.println("From App " + message.toXML());
        String msgType = message.getHeader().getString(MsgType.FIELD);
        Message response = FixMessageHandler.lookUp(msgType).handle(message, LIMIT_ORDER_BOOK);
        try {
            Session.sendToTarget(response, sessionId);
        } catch (SessionNotFound sessionNotFound) {
            sessionNotFound.printStackTrace();
        }
    }

    public static void start() throws ConfigError, InterruptedException {
        System.out.println(System.getProperty("user.dir"));
        SessionSettings settings = new SessionSettings("resources/fix.server.cfg");

        Application application = new FixServer();
        MessageStoreFactory messageStoreFactory = new MemoryStoreFactory();
        LogFactory logFactory = new ScreenLogFactory( true, true, true);
        MessageFactory messageFactory = new DefaultMessageFactory();

        Acceptor initiator = new SocketAcceptor(
                application,
                messageStoreFactory,
                settings,
                logFactory,
                messageFactory
        );
        initiator.start();

        CountDownLatch latch = new CountDownLatch(1);
        latch.await();
    }
}
