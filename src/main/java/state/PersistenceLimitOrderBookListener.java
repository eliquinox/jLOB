package state;

import dto.Cancellation;
import dto.Match;
import dto.Placement;

import static db.persistence.CancellationsPersistence.persistCancellation;
import static db.persistence.MatchesPersistence.persistMatch;
import static db.persistence.PlacementsPersistence.persistPlacement;

public class PersistenceLimitOrderBookListener implements LimitOrderBookListener {

    @Override
    public void onPlacement(Placement placement) {
        persistPlacement(placement);
    }

    @Override
    public void onCancellation(Cancellation cancellation) {
        persistCancellation(cancellation);
    }

    @Override
    public void onMatch(Match match) {
        persistMatch(match);
    }

}
