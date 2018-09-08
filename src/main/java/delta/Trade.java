package delta;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import state.Limit;

public class Trade implements Delta {

    private final long id = GLOBAL_ID.incrementAndGet();
    private final long timestamp;
    private final Limit limit;
    private final long size;
    private final Side side;
    private final Type type = Type.TRADE;

    public Trade(Limit limit, long size, Side side){
        this.timestamp = System.nanoTime();
        this.limit = limit;
        this.size = size;
        this.side = side;
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

}
