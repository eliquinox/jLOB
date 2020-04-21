package state;

import dto.Cancellation;
import dto.Match;
import dto.Placement;

import static cache.Cache.cacheLimitOrderBook;
import static db.persistence.CancellationsPersistence.persistCancellation;
import static db.persistence.MatchesPersistence.persistMatch;
import static db.persistence.PlacementsPersistence.persistPlacement;


public class PersistenceLimitOrderBookListener implements LimitOrderBookListener {

    @Override
    public void onPlacement(Placement placement, LimitOrderBook limitOrderBook) {
        persistPlacement(placement);
        cacheLimitOrderBook(limitOrderBook);
    }

    @Override
    public void onCancellation(Cancellation cancellation, LimitOrderBook limitOrderBook) {
        persistCancellation(cancellation);
        cacheLimitOrderBook(limitOrderBook);
    }

    @Override
    public void onMatch(Match match) {
        persistMatch(match);
    }
}
