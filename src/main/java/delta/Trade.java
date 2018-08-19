package delta;

public class Trade implements Delta {

    private final long id = GLOBAL_ID.incrementAndGet();
    private final double price;
    private final long size;
    private final Side side;
    private final long timestamp;

    public Trade(double price, long size, Side side){
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
    public double getPrice() {
        return price;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trade trade = (Trade) o;
        if (id != trade.id) return false;
        if (Double.compare(trade.price, price) != 0) return false;
        if (size != trade.size) return false;
        if (timestamp != trade.timestamp) return false;
        return side == trade.side;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) (size ^ (size >>> 32));
        result = 31 * result + side.hashCode();
        result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
        return result;
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
