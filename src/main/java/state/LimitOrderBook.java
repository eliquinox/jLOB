package state;

import delta.Cancellation;
import delta.Placement;
import delta.Side;
import delta.Trade;


public interface LimitOrderBook extends State{

    Placement getBestBidPlacement();
    Placement getBestOfferPlacement();
    Limit getBestBidLimit();
    Limit getBestOfferLimit();
    Limit getLimit(Side side, Double priceLevel);
    LimitOrderBook place(Placement placement);
    LimitOrderBook match(Trade trade);
    LimitOrderBook cancel(Cancellation cancellation);
    boolean isEmpty();

}
