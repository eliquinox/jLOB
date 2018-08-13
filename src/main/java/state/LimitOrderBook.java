package state;

import delta.Delta;
import delta.Order;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public interface LimitOrderBook extends State{

    Order getBestBid();

    Order getBestOffer();

    double getAverageBid();

    double getAverageOffer();

    double getMid();

    double getSpread();

    LimitOrderBook apply(Function<Delta, State> operator);

}
