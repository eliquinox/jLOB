package state;

import dto.Cancellation;
import dto.Match;
import dto.Placement;

public interface LimitOrderBookListener {
    void onPlacement(Placement placement);
    void onCancellation(Cancellation cancellation);
    void onMatch(Match match);
}
