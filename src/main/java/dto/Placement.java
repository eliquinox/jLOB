package dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Placement implements GID {

    private final long id = GLOBAL_ID.incrementAndGet();
    private final long timestamp;
    private final Side side;
    private final long price;
    private long size;

    public Placement(Side side, long price, long size) {
        this.timestamp = System.nanoTime();
        this.side = side;
        this.price = price;
        this.size = size;
    }

    public Placement(String side, long price, long size) {
        this.timestamp = System.nanoTime();
        this.side = Side.fromString(side);
        this.price = price;
        this.size = size;
    }

    public long getId() {
        return id;
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