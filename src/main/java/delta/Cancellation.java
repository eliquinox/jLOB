package delta;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Cancellation implements Delta {

    private final long id = GLOBAL_ID.incrementAndGet();
    private final Double price;
    private final Long size;
    private final Side side;
    private final long timestamp;
    private final Type type = Type.CANCELLATION;

    public Cancellation(Placement placement, long cancellationSize) {
        this.price = placement.getPrice();
        this.size = cancellationSize;
        this.side = placement.getSide();
        this.timestamp = System.nanoTime();
    }

    @Override
    public long getId() {
        return id;
    }

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
        Cancellation that = (Cancellation) o;
        return new EqualsBuilder()
                .append(id, that.id)
                .append(price, that.price)
                .append(size, that.size)
                .append(timestamp, that.timestamp)
                .append(side, that.side)
                .append(type, that.type)
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
        return "Cancellation{" +
                "id=" + id +
                ", price=" + price +
                ", size=" + size +
                ", side=" + side +
                ", timestamp=" + timestamp +
                '}';
    }

}
