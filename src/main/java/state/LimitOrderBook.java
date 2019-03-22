package state;

import delta.Cancellation;
import delta.Placement;
import delta.Side;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectRBTreeMap;
import it.unimi.dsi.fastutil.longs.LongComparators;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * L3 Limit Order Book implementation.
 *
 * Bids and offers are kept sorted in reverse natural and natural orders respectively
 * by the virtue of {@code Long2ObjectRBTreeMap<Limit>}s
 *
 * {@code Limit}s represent price levels in an order book and wrap around a collection of {@code Placement}s.
 *
 */

public class LimitOrderBook {

    private Long2ObjectRBTreeMap<Limit> bids;
    private Long2ObjectRBTreeMap<Limit> offers;
    private Long2ObjectOpenHashMap<Placement> placements;

    public LimitOrderBook(Long2ObjectRBTreeMap<Limit> bids,
                          Long2ObjectRBTreeMap<Limit> offers,
                          Long2ObjectOpenHashMap<Placement> placements){
        this.bids = bids;
        this.offers = offers;
        this.placements = placements;
    }

    private LimitOrderBook(){
        this.bids = new Long2ObjectRBTreeMap<>(LongComparators.OPPOSITE_COMPARATOR);
        this.offers = new Long2ObjectRBTreeMap<>(LongComparators.NATURAL_COMPARATOR);
        this.placements = new Long2ObjectOpenHashMap<>();
    }

    public static LimitOrderBook empty(){
        return new LimitOrderBook();
    }

    public boolean isEmpty(){
        return bids.isEmpty() && offers.isEmpty() && placements.isEmpty();
    }

    public void place(Placement placement) {
        if (placements.containsKey(placement.getId()))
            return;
        if (placement.getSide() == Side.BID)
            bid(placement);
        else
            offer(placement);
    }

    private void bid(Placement placement){
        long remainingQuantity = placement.getSize();
        Limit limit  = getBestLimit(offers);
        while (remainingQuantity > 0 && limit != null && limit.getPrice() <= placement.getPrice()) {
            remainingQuantity = limit.match(placement, placements);
            if (limit.isEmpty())
                offers.remove(limit.getPrice());
            limit = getBestLimit(offers);
        }
        if (remainingQuantity > 0) {
            placements.put(placement.getId(), place(bids, placement));
        }
    }

    private void offer(Placement placement){
        long remainingQuantity = placement.getSize();
        Limit limit = getBestLimit(bids);
        while (remainingQuantity > 0 && limit != null && limit.getPrice() >= placement.getPrice()) {
            remainingQuantity = limit.match(placement, placements);
            if (limit.isEmpty())
                bids.remove(limit.getPrice());
            limit = getBestLimit(bids);
        }
        if (remainingQuantity > 0) {
            placements.put(placement.getId(), place(offers, placement));
        }
    }

    private Placement place(Long2ObjectRBTreeMap<Limit> levels, Placement placement) {
        Limit level = levels.get(placement.getPrice());
        if (level == null) {
            level = new Limit(placement.getSide(), placement.getPrice());
            levels.put(placement.getPrice(), level);
        }
        return level.place(placement);
    }

    public void cancel(Cancellation cancellation) {
        Placement placement = placements.get(cancellation.getId());
        if (placement == null)
            return;
        if (cancellation.getSize() >= placement.getSize())
            return;
        if (cancellation.getSize() > 0) {
            placement.reduce(cancellation.getSize());
        } else {
            remove(placement);
            placements.remove(placement.getId());
        }
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

    private Limit getBestLimit(Long2ObjectRBTreeMap<Limit> levels){
        if (levels.isEmpty())
            return null;
        return levels.get(levels.firstLongKey());
    }

    public long getMidPrice(){
        return (bids.firstLongKey() + offers.firstLongKey()) / 2;
    }

    public long getBestBid(){
        return bids.firstLongKey();
    }

    public long getBestOffer(){
        return offers.firstLongKey();
    }

    public long getBestBidAmount(){
        return bids.get(bids.firstLongKey()).getVolume();
    }

    public long getBestOfferAmount(){
        return offers.get(offers.firstLongKey()).getVolume();
    }

    private double getAveragePrice(long size, Long2ObjectRBTreeMap<Limit> levels) {
        long psizesum = 0L, sizesum = 0L;
        int i = 0, c = levels.size();
        for(Limit limit : levels.values()){
            long unfilled_size = size - sizesum;
            long price = limit.getPrice();
            long volume = limit.getVolume();
            long s = Math.min(unfilled_size, volume);
            sizesum += s;
            psizesum += s * price;
            if(sizesum >= size)
                return sizesum == 0. ? 0. : psizesum / size;
        }
        return sizesum < size ? Double.NaN : 0.;
    }

    public double getAverageSalePrice(long size){
        return getAveragePrice(size, bids);
    }

    public double getAveragePurchasePrice(long size){
        return getAveragePrice(size, offers);
    }

    @Override
    public String toString(){
        return new ToStringBuilder(this)
                .append("bids", bids)
                .append("offers", offers)
                .append("placements", placements)
                .toString();
    }

    public String info(){
        StringBuilder builder = new StringBuilder();
        builder.append("| Timestamp: " + System.nanoTime());
        long bidLimits = bids.values().size();
        long offerLimits = bids.values().size();
        long bidPlacements = bids.values().stream().map(Limit::getPlacementCount).reduce(0, (a, b) -> a + b);
        long offerPlacements = offers.values().stream().map(Limit::getPlacementCount).reduce(0, (a, b) -> a + b);
        long bidVolume = bids.values().stream().map(Limit::getVolume).reduce(0L, (a, b) -> a + b);
        long offerVolume = offers.values().stream().map(Limit::getVolume).reduce(0L, (a, b) -> a + b);
        builder.append(" | Bid Placments: " + bidPlacements + " | Bid Volume: " + bidVolume + " | Bid Limits: " + bidLimits +
                       " | Offer Placements: " + offerPlacements + " | Offer Volume: " + offerVolume + " | Offer Limits: " + offerLimits);
        return builder.toString();
    }

    public String bestBidOffer(){
        StringBuilder builder = new StringBuilder();
        builder.append("| Timestamp: " + System.nanoTime());
        builder.append(
                "| Best Bid Price: " + bids.firstLongKey() +
                "| Best Bid Volume: " + bids.get(bids.firstLongKey()).getVolume() +
                "| Best Offer Price: " + offers.firstLongKey() +
                "| Best Offer Volume: " + offers.get(offers.firstLongKey()).getVolume()
        );
        return builder.toString();
    }
}