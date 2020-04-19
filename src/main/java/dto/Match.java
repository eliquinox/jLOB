package dto;

import java.time.Instant;
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
}
