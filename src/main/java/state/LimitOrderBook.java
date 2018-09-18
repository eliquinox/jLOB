package state;

import delta.Cancellation;
import delta.Placement;
import delta.Side;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectRBTreeMap;
import it.unimi.dsi.fastutil.longs.LongComparators;

public class LimitOrderBook implements OrderBook{

    // TODO: Analyse memory footprint of returning new LimitOrderBook on various state updates

    private final long timestamp;
    private Long2ObjectRBTreeMap<Limit> bids;
    private Long2ObjectRBTreeMap<Limit> offers;
    private Long2ObjectOpenHashMap<Placement> placements;

    private LimitOrderBook(){
        this.timestamp = System.nanoTime();
        this.bids = new Long2ObjectRBTreeMap<>(LongComparators.OPPOSITE_COMPARATOR);
        this.offers = new Long2ObjectRBTreeMap<>(LongComparators.NATURAL_COMPARATOR);
        this.placements = new Long2ObjectOpenHashMap<>();
    }

    private LimitOrderBook(Long2ObjectRBTreeMap bids, Long2ObjectRBTreeMap offers, Long2ObjectOpenHashMap placements){
        this.timestamp = System.nanoTime();
        this.bids = bids;
        this.offers = offers;
        this.placements = placements;
    }

    public static LimitOrderBook empty(){
        return new LimitOrderBook();
    }

    public boolean isEmpty(){
        return bids.isEmpty() && offers.isEmpty() && placements.isEmpty();
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public LimitOrderBook place(Placement placement) {
        if (placements.containsKey(placement.getId()))
            return this;
        if (placement.getSide() == Side.BID)
            return bid(placement);
        else
            return offer(placement);
    }

    private LimitOrderBook bid(Placement placement){
        long remainingQuantity = placement.getSize();
        Limit limit  = getBestLimit(offers);
        while (remainingQuantity > 0 && limit != null && limit.getPrice() <= placement.getPrice()) {
            remainingQuantity = limit.match(placement.getSize());
            if (limit.isEmpty())
                limit.remove(placement);
            limit = getBestLimit(offers);
        }
        if (remainingQuantity > 0) {
            placements.put(placement.getId(), place(bids, placement));
        }
        return new LimitOrderBook(bids, offers, placements);
    }

    private LimitOrderBook offer(Placement placement){
        long remainingQuantity = placement.getSize();
        Limit limit = getBestLimit(bids);
        while (remainingQuantity > 0 && limit != null && limit.getPrice() >= placement.getPrice()) {
            remainingQuantity = limit.match(placement.getSize());
            if (limit.isEmpty())
                bids.remove(limit.getPrice());
            limit = getBestLimit(bids);
        }
        if (remainingQuantity > 0) {
            placements.put(placement.getId(), place(offers, placement));
        }
        return new LimitOrderBook(bids, offers, placements);
    }

    private Placement place(Long2ObjectRBTreeMap<Limit> levels, Placement placement) {
        Limit level = levels.get(placement.getPrice());
        if (level == null) {
            level = new Limit(placement.getSide(), placement.getPrice());
            levels.put(placement.getPrice(), level);
        }
        return level.place(placement);
    }

    @Override
    public LimitOrderBook cancel(Cancellation cancellation) {
        Placement placement = placements.get(cancellation.getId());
        if (placement == null)
            return this;
        placement.reduce(cancellation.getSize());
        if (placement.getSize() == 0L) {
            remove(placement);
            placements.remove(placement.getId());
        }
        return new LimitOrderBook(bids, offers, placements);
    }

    private void remove(Placement placement){
        Limit limit = placement.getLimit();
        limit.remove(placement);
        if (limit.isEmpty())
            remove(limit);
    }

    private void remove(Limit limit){
        if (limit.getSide() == Side.BID)
            bids.remove(limit.getPrice());
        else
            offers.remove(limit.getPrice());
    }

    private Limit getBestLimit(Long2ObjectRBTreeMap<Limit> levels) {
        if (levels.isEmpty())
            return null;
        return levels.get(levels.firstLongKey());
    }

}
