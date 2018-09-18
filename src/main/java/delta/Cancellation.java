package delta;

import org.apache.commons.lang3.builder.ToStringBuilder;
import state.Limit;

public class Cancellation implements Delta {

    private long id;
    private final long timestamp;
    private final Limit limit;
    private final long size;

    public Cancellation(Placement placement, long cancellationSize) {
        this.timestamp = System.nanoTime();
        this.id = placement.getId();
        this.limit = placement.getLimit();
        this.size = cancellationSize;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public Limit getLimit() {
        return limit;
    }

    @Override
    public long getSize() {
        return size;
    }

    @Override
    public long getPrice(){
        return limit.getPrice();
    }

    @Override
    public Side getSide() {
        return limit.getSide();
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("timestamp", timestamp)
                .append("limit", limit)
                .append("size", size)
                .toString();
    }
}
