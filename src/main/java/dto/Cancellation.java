package dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.Instant;
import java.util.UUID;

public class Cancellation {

    private final UUID placementUuid;
    private final Instant timestamp;
    private final long size;

    public Cancellation(UUID placementId, long cancellationSize) {
        this.timestamp = Instant.now();
        this.placementUuid = placementId;
        this.size = cancellationSize;
    }

    public UUID getPlacementUuid() {
        return placementUuid;
    }

    public long getSize() {
        return size;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", placementUuid)
                .append("timestamp", timestamp)
                .append("size", size)
                .toString();
    }
}
