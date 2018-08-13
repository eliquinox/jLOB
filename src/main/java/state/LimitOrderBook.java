package state;

import delta.Order;

public interface LimitOrderBook extends State{

    Order getBestBid();

    Order getBestOffer();

    double getAverageBid();

    double getAverageOffer();

    double getMid();

    double getSpread();

}
