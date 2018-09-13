package delta;

import state.Limit;

import java.util.concurrent.atomic.AtomicLong;

public interface Delta {
    AtomicLong GLOBAL_ID = new AtomicLong();
    long getId();
    Limit getLimit();
    long getSize();
    long getPrice();
    Side getSide();
    long getTimestamp();
    Type getType();
}
