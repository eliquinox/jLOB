package delta;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Placement implements Delta {

    private final long id = GLOBAL_ID.incrementAndGet();
    private final Double price;
    private Long size;
    private final Side side;
    private final long timestamp;
    private final Type type = Type.PLACEMENT;

    public Placement(Double price, Long size, Side side){
        this.price = price;
        this.size = size;
        this.side = side;
        this.timestamp = System.nanoTime();
    }

    public Placement cancel(Cancellation cancellation){
        long newSize = getSize() - cancellation.getSize();
        return new Placement(getPrice(), newSize, getSide());
    }

    public Placement match(Trade trade){
        //TODO: DRY! evaluate redundancy given the same logic as cancellation
        long newSize = getSize() - trade.getSize();
        return new Placement(getPrice(), newSize, getSide());
    }

    @Override
    public long getId() { return id; }

    @Override
    public Double getPrice() {
        return price;
    }

    @Override
    public Long getSize() {
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Placement placement = (Placement) o;
        return new EqualsBuilder()
                .append(id, placement.id)
                .append(price, placement.price)
                .append(timestamp, placement.timestamp)
                .append(size, placement.size)
                .append(side, placement.side)
                .append(type, placement.type)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(price)
                .append(size)
                .append(side)
                .append(timestamp)
                .append(type)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Placement{" +
                "id=" + id +
                ", price=" + price +
                ", size=" + size +
                ", side=" + side +
                ", timestamp=" + timestamp +
                '}';
    }
}