package delta;

public class Cancellation implements Delta {

    private final long id = GLOBAL_ID.incrementAndGet();
    private final double price;
    private final long size;
    private final Side side;
    private final long timestamp;

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
        Cancellation that = (Cancellation) o;
        if (id != that.id) return false;
        if (Double.compare(that.price, price) != 0) return false;
        if (size != that.size) return false;
        if (timestamp != that.timestamp) return false;
        return side == that.side;
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
        return "Cancellation{" +
                "id=" + id +
                ", price=" + price +
                ", size=" + size +
                ", side=" + side +
                ", timestamp=" + timestamp +
                '}';
    }

}
