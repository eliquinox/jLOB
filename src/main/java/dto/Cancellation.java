package dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.UUID;

public class Cancellation {

    private UUID id;
    private final long timestamp;
    private final long size;

    public Cancellation(UUID placementId, long cancellationSize) {
        this.timestamp = System.nanoTime();
        this.id = placementId;
        this.size = cancellationSize;
    }

    public UUID getId() {
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
