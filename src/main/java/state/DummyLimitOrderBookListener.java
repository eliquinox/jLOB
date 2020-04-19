package state;

import dto.Cancellation;
import dto.Match;
import dto.Placement;

public class DummyLimitOrderBookListener implements LimitOrderBookListener {

    @Override
    public void onPlacement(Placement placement) {

    }

    @Override
    public void onCancellation(Cancellation cancellation) {

    }

    @Override
    public void onMatch(Match match) {

    }
}
