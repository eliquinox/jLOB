package state;

import delta.Cancellation;
import delta.Placement;
import delta.Side;
import delta.Trade;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectRBTreeMap;
import it.unimi.dsi.fastutil.longs.LongComparators;

public class LimitOrderBook implements OrderBook{

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
        this.bids = new Long2ObjectRBTreeMap<>(LongComparators.OPPOSITE_COMPARATOR);
        this.offers = new Long2ObjectRBTreeMap<>(LongComparators.NATURAL_COMPARATOR);
        this.placements = new Long2ObjectOpenHashMap<>();
    }

    public static LimitOrderBook empty(){
        return new LimitOrderBook();
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
        Limit limit  = getBestLevel(offers);
        while (remainingQuantity > 0 && limit != null && limit.getPrice() <= placement.getPrice()) {
            remainingQuantity = limit.match(placement.getSize());
            if (limit.isEmpty())
                limit.remove(placement);
            limit = getBestLevel(offers);
        }
        if (remainingQuantity > 0) {
            placements.put(placement.getId(), add(bids, placement));
        }
        return new LimitOrderBook(bids, offers, placements);
    }

    private LimitOrderBook offer(Placement placement){
        long remainingQuantity = placement.getSize();
        Limit limit = getBestLevel(bids);
        while (remainingQuantity > 0 && limit != null && limit.getPrice() >= placement.getPrice()) {
            remainingQuantity = limit.match(placement.getSize());
            if (limit.isEmpty())
                bids.remove(limit.getPrice());
            limit = getBestLevel(bids);
        }
        if (remainingQuantity > 0) {
            placements.put(placement.getId(), add(offers, placement));
        }
        return new LimitOrderBook(bids, offers, placements);
    }

    private Placement add(Long2ObjectRBTreeMap<Limit> levels, Placement placement) {
        Limit level = levels.get(placement.getPrice());
        if (level == null) {
            level = new Limit(placement.getSide(), placement.getPrice());
            levels.put(placement.getPrice(), level);
        }
        return level.place(placement);
    }

    @Override
    public LimitOrderBook match(Trade trade) {
        return null;
    }

    @Override
    public LimitOrderBook cancel(Cancellation cancellation) {
        return null;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    private Limit getBestLevel(Long2ObjectRBTreeMap<Limit> levels) {
        if (levels.isEmpty())
            return null;
        return levels.get(levels.firstLongKey());
    }

}
