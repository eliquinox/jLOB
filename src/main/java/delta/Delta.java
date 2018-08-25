package delta;

import java.util.concurrent.atomic.AtomicLong;

public interface Delta {
    AtomicLong GLOBAL_ID = new AtomicLong();
    long getId();
    Double getPrice();
    Long getSize();
    Side getSide();
    long getTimestamp();
    Type getType();
}
