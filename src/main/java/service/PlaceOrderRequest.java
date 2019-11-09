package service;

public class PlaceOrderRequest {

    private String side;
    private long price;
    private long size;


    public PlaceOrderRequest(String side, long price, long size) {
        this.side = side;
        this.price = price;
        this.size = size;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

}
