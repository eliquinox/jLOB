package state;

import delta.Cancellation;
import delta.Placement;
import delta.Side;
import delta.Trade;

import java.util.ArrayList;
import java.util.List;

public class ListLimitOrderBook implements LimitOrderBook{
    //TODO: Protect state better by returning new objects upon state updates
    private final List<Limit> bids;
    private final List<Limit> offers;
    private final long timestamp;

    private ListLimitOrderBook(List<Limit> bids, List<Limit> offers){
        this.bids = bids;
        this.offers = offers;
        this.timestamp = System.nanoTime();
    }

    public static LimitOrderBook empty(){
        return new ListLimitOrderBook(new ArrayList<>(), new ArrayList<>());
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public Placement getBestBidPlacement() {
        return bids.get(0).getPlacements().get(0);
    }

    @Override
    public Placement getBestOfferPlacement() {
        return offers.get(0).getPlacements().get(0);
    }

    @Override
    public Limit getBestBidLimit() {
        return bids.get(0);
    }

    @Override
    public Limit getBestOfferLimit() {
        return offers.get(0);
    }

    @Override
    public Limit getLimit(Side side, Double priceLevel) {
        if(side == Side.BID)
            return bids.stream()
                    .filter(l -> l.getPriceLevel().equals(priceLevel))
                    .findFirst()
                    .orElse(addLimit(side, priceLevel));
        else
            return offers.stream()
                    .filter(l -> l.getPriceLevel().equals(priceLevel))
                    .findFirst()
                    .orElse(addLimit(side, priceLevel));
    }

    @Override
    public LimitOrderBook place(Placement placement) {
        Limit limit = getLimit(placement.getSide(), placement.getPrice());
        limit.place(placement);
        return new ListLimitOrderBook(bids, offers);
    }

    @Override
    public LimitOrderBook match(Trade trade) {
        return null;
    }

    @Override
    public LimitOrderBook cancel(Cancellation cancellation) {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return bids.isEmpty() && offers.isEmpty();
    }

    private Limit addLimit(Side side, Double priceLevel){
        Limit limit = new Limit(side, priceLevel);
        if(side == Side.BID){
            bids.add(limit);
        } else {
            offers.add(limit);
        }
        return limit;
    }

}
