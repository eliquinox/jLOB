package delta;


public class Placement implements Delta {

    private final long id = GLOBAL_ID.incrementAndGet();
    private final double price;
    private long size;
    private final Side side;
    private final long timestamp;

    public Placement(double price, long size, Side side){
        this.price = price;
        this.size = size;
        this.side = side;
        this.timestamp = System.nanoTime();
    }

    public Placement cancel(Cancellation cancellation){
        // updates the placement size and
        // returns new placement object
        long newSize = getSize() - cancellation.getSize();
        return new Placement(getPrice(), newSize, getSide());
    }

    @Override
    public long getId() { return id; }

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
        Placement placement = (Placement) o;
        if (id != placement.id) return false;
        if (Double.compare(placement.price, price) != 0) return false;
        if (size != placement.size) return false;
        if (timestamp != placement.timestamp) return false;
        return side == placement.side;
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
        return "Placement{" +
                "id=" + id +
                ", price=" + price +
                ", size=" + size +
                ", side=" + side +
                ", timestamp=" + timestamp +
                '}';
    }
}
