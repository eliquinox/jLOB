package delta;

import org.apache.commons.lang3.builder.ToStringBuilder;
import state.Limit;

public class Placement implements Delta {

    private long id = GLOBAL_ID.incrementAndGet();
    private final long timestamp;
    private Limit limit;
    private long size;

    public Placement(Limit limit, long size){
        this.timestamp = System.nanoTime();
        this.limit = limit;
        this.size = size;
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