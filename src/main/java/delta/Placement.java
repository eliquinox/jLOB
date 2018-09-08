package delta;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import state.Limit;

public class Placement implements Delta {

    private long id = GLOBAL_ID.incrementAndGet();
    private final long timestamp;
    private Limit limit;
    private long size;
    private final Side side;
    private final Type type = Type.PLACEMENT;

    public Placement(Limit limit, long size, Side side){
        this.timestamp = System.nanoTime();
        this.limit = limit;
        this.size = size;
        this.side = side;
    }

    public void reduce(long size){
        this.size -= size;
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
    public Side getSide() {
        return side;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public Type getType() {
        return type;
    }

    public long getPrice(){
        return limit.getPrice();
    }

}