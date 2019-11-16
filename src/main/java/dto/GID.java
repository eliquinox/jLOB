package dto;

import java.util.concurrent.atomic.AtomicLong;

public interface GID {
    AtomicLong GLOBAL_ID = new AtomicLong();
}
