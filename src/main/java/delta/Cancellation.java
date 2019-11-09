package delta;

import org.apache.commons.lang3.builder.ToStringBuilder;
import state.Limit;

public class Cancellation implements Delta {

    private long id;
    private final long timestamp;
    private final Side side;
    private final long price;
    private final long size;

    public Cancellation(Placement placement, long cancellationSize) {
        this.timestamp = System.nanoTime();
        this.id = placement.getId();
        this.side = placement.getSide();
        this.price = placement.getPrice();
        this.size = cancellationSize;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public long getSize() {
        return size;
    }

    @Override
    public long getPrice(){
        return price;
    }

    @Override
    public Side getSide() {
        return side;
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
                .append("size", size)
                .toString();
    }
}
