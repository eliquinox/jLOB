package dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Match {
    private final UUID makerPlacementId;
    private final UUID takerPlacementId;
    private final Instant timestamp;
    private final long size;

    public Match(UUID makerPlacementId, UUID takerPlacementId, long size) {
        this.makerPlacementId = makerPlacementId;
        this.takerPlacementId = takerPlacementId;
        this.timestamp = Instant.now();
        this.size = size;
    }

    public UUID getMakerPlacementId() {
        return makerPlacementId;
    }

    public UUID getTakerPlacementId() {
        return takerPlacementId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public long getSize() {
        return size;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("makerPlacementId", makerPlacementId)
                .append("takerPlacementId", takerPlacementId)
                .append("timestamp", timestamp)
                .append("size", size)
                .toString();
    }
}
