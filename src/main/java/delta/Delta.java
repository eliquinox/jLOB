package delta;

import java.util.concurrent.atomic.AtomicLong;

public interface Delta {
    AtomicLong GLOBAL_ID = new AtomicLong();
    long getId();
    double getPrice();
    long getSize();
    Side getSide();
    long getTimestamp();
}
