package state;

import delta.Placement;

import java.util.List;
import java.util.function.Function;

public class ListBasedLimitOrderBook implements LimitOrderBook{

    List<Limit> bids;
    List<Limit> offers;

    @Override
    public long getTimestamp() {
        return System.nanoTime();
    }

    @Override
    public Placement getBestBid() {
        return bids.get(0).getOrders().get(0);
    }

    @Override
    public Placement getBestOffer() {
        return offers.get(0).getOrders().get(0);
    }

    public List<Limit> getLimit(Double priceLevel){
        return null;
    }

    @Override
    public LimitOrderBook apply(Placement placement, Function<Placement, LimitOrderBook> function) {
        return function.apply(placement);
    }

    public LimitOrderBook add(LimitOrderBook book, Placement placement){
        return apply(placement, p -> {
            return null;
        });
    }
}
