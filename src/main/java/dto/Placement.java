package dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.Instant;
import java.util.UUID;

public class Placement {

    private UUID uuid = UUID.randomUUID();
    private final Instant timestamp;
    private final Side side;
    private final long price;
    private long size;

    public Placement(Side side, long price, long size) {
        this.timestamp = Instant.now();
        this.side = side;
        this.price = price;
        this.size = size;
    }

    public Placement(String side, long price, long size) {
        this.timestamp = Instant.now();
        this.side = Side.fromString(side);
        this.price = price;
        this.size = size;
    }

    public Placement copy() {
        return builder()
                .withUuid(this.uuid)
                .withInstant(this.timestamp)
                .withSide(this.side)
                .withPrice(this.price)
                .withSize(this.size)
                .build();
    }

    public static Builder builder() {
        return new Builder();
    }

    private Placement(Builder builder) {
        this.uuid = builder.uuid;
        this.timestamp = builder.timestamp;
        this.side = builder.side;
        this.price = builder.price;
        this.size = builder.size;
    }

    private static class Builder {

        private UUID uuid;
        private Instant timestamp;
        private Side side;
        private long price;
        private long size;

        Builder withUuid(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        Builder withInstant(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        Builder withSide(Side side) {
            this.side = side;
            return this;
        }

        Builder withPrice(long price) {
            this.price = price;
            return this;
        }

        Builder withSize(long size) {
            this.size = size;
            return this;
        }

        Placement build() {
            return new Placement(this);
        }
    }

    public UUID getUuid() {
        return uuid;
    }

    public long getSize() {
        return size;
    }

    public long getPrice(){
        return price;
    }

    public Side getSide() {
        return side;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void reduce(long size){
        this.size -= size;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", uuid)
                .append("timestamp", timestamp)
                .append("side", getSide())
                .append("price", getPrice())
                .append("size", size)
                .toString();
    }
}