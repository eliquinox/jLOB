package state;

import cache.Cache;
import com.google.inject.Inject;
import dto.Cancellation;
import dto.Match;
import dto.Placement;

import static db.persistence.CancellationsPersistence.persistCancellation;
import static db.persistence.MatchesPersistence.persistMatch;
import static db.persistence.PlacementsPersistence.persistPlacement;


public class PersistenceLimitOrderBookListener implements LimitOrderBookListener {

    private final Cache cache;

    @Inject
    public PersistenceLimitOrderBookListener(Cache cache) {
        this.cache = cache;
    }

    @Override
    public void onPlacement(Placement placement, LimitOrderBook limitOrderBook) {
        persistPlacement(placement);
        cache.cacheLimitOrderBook(limitOrderBook);
    }

    @Override
    public void onCancellation(Cancellation cancellation, LimitOrderBook limitOrderBook) {
        persistCancellation(cancellation);
        cache.cacheLimitOrderBook(limitOrderBook);
    }

    @Override
    public void onMatch(Match match) {
        persistMatch(match);
    }
}
