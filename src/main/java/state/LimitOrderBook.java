package state;

import com.sun.org.apache.xpath.internal.operations.Or;
import delta.Delta;
import delta.Placement;
import javafx.print.PageLayout;

import java.util.function.Function;

public interface LimitOrderBook extends State{

    Placement getBestBid();

    Placement getBestOffer();

    LimitOrderBook apply(Placement placement, Function<Placement, LimitOrderBook> function);

}
