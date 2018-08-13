package delta;

public class Order implements Delta {

    private final double price;
    private final int size;
    private final Side side;
    private final long timestamp;


    public Order(double price, int size, Side side){
        this.price = price;
        this.size = size;
        this.side = side;
        this.timestamp = System.nanoTime();
    }

    public double getPrice() {
        return price;
    }

    public int getSize() {
        return size;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "SimpleDelta{" +
                "price=" + price +
                ", size=" + size +
                ", side=" + side +
                ", timestamp=" + timestamp +
                '}';
    }

}
