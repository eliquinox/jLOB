package state;

import delta.Cancellation;
import delta.Placement;


public interface OrderBook extends State{
    LimitOrderBook place(Placement placement);
    LimitOrderBook cancel(Cancellation cancellation);
}
