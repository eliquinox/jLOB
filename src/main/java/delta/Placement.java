package delta;

import state.Limit;

public class Placement implements Delta {

    private long id = GLOBAL_ID.incrementAndGet();
    private final long timestamp;
    private Limit limit;
    private long size;
    private final Type type = Type.PLACEMENT;

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

    @Override
    public Type getType() {
        return type;
    }

    public void reduce(long size){
        this.size -= size;
    }

}