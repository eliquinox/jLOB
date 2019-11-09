package delta;

import java.util.concurrent.atomic.AtomicLong;

public interface Delta {
    AtomicLong GLOBAL_ID = new AtomicLong();
    long getId();
    long getSize();
    long getPrice();
    Side getSide();
    long getTimestamp();
}
