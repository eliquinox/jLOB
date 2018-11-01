package state;

import delta.Cancellation;
import delta.Placement;


public interface OrderBook extends State{
    void place(Placement placement);
    void cancel(Cancellation cancellation);
}
