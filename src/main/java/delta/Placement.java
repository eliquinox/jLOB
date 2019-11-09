package delta;

import org.apache.commons.lang3.builder.ToStringBuilder;
import state.Limit;

public class Placement implements Delta {

    private final long id = GLOBAL_ID.incrementAndGet();
    private final long timestamp;
    private final Side side;
    private final long price;
    private long size;

    public Placement(Side side, long price, long size){
        this.timestamp = System.nanoTime();
        this.side = side;
        this.price = price;
        this.size = size;
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

    public void reduce(long size){
        this.size -= size;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("timestamp", timestamp)
                .append("side", getSide())
                .append("price", getPrice())
                .append("size", size)
                .toString();
    }
}