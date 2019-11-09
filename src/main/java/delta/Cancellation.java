package delta;

import org.apache.commons.lang3.builder.ToStringBuilder;
import state.Limit;

public class Cancellation {

    private long id;
    private final long timestamp;
    private final long size;

    public Cancellation(long placementId, long cancellationSize) {
        this.timestamp = System.nanoTime();
        this.id = placementId;
        this.size = cancellationSize;
    }

    public long getId() {
        return id;
    }

    public long getSize() {
        return size;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("timestamp", timestamp)
                .append("size", size)
                .toString();
    }
}
