package delta;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Trade implements Delta {

    private final long id = GLOBAL_ID.incrementAndGet();
    private final Double price;
    private final Long size;
    private final Side side;
    private final long timestamp;
    private final Type type = Type.TRADE;

    public Trade(Double price, Long size, Side side){
        this.price = price;
        this.size = size;
        this.side = side;
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
        Trade trade = (Trade) o;
        return new EqualsBuilder()
                .append(id, trade.id)
                .append(price, trade.price)
                .append(size, trade.size)
                .append(timestamp, trade.timestamp)
                .append(side, trade.side)
                .append(type, trade.type)
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
        return "Trade{" +
                "id=" + id +
                ", price=" + price +
                ", size=" + size +
                ", side=" + side +
                ", timestamp=" + timestamp +
                '}';
    }
}
