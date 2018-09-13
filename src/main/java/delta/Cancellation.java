package delta;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import state.Limit;

public class Cancellation implements Delta {

    private long id;
    private final long timestamp;
    private final Limit limit;
    private final long size;
    private final Type type = Type.CANCELLATION;

    public Cancellation(Placement placement, long cancellationSize) {
        this.timestamp = System.nanoTime();
        this.id = placement.getId(); //Different delta ID
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
    public Type getType() {
        return type;
    }

}
