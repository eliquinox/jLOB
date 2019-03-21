package state;

import delta.Placement;
import delta.Side;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;

import java.util.ArrayList;
import java.util.List;

public class Limit {
    private final Side side;

    private final long price;
    private List<Placement> placements;
    public Limit(Side side, long price) {
        this.side = side;
        this.price = price;
        this.placements = new ArrayList<>();
    }

    public Side getSide() {
        return side;
    }

    public long getPrice() {
        return price;
    }

    public Placement place(Placement placement){
        placements.add(placement);
        return placement;
    }

    public Placement remove(Placement placement){
        placements.remove(placement);
        return placement;
    }

    public long match(Placement incomingPlacement, Long2ObjectOpenHashMap<Placement> placementIds){
        while (incomingPlacement.getSize() > 0 && !placements.isEmpty()) {
            Placement restingPlacement = placements.get(0);
            long restingPlacementId = restingPlacement.getId();
            long orderSize = restingPlacement.getSize();
            if (orderSize > incomingPlacement.getSize()) {
                restingPlacement.reduce(incomingPlacement.getSize());
                incomingPlacement.reduce(incomingPlacement.getSize());
            } else {
                placements.remove(0);
                incomingPlacement.reduce(orderSize);
                placementIds.remove(restingPlacementId);
            }
        }
        return incomingPlacement.getSize();
    }

    public long getVolume(){
        return placements.stream().map(Placement::getSize).reduce(0L, (a,b) -> a + b);
    }

    public int getPlacementCount(){
        return placements.size();
    }

    public boolean isEmpty(){
        return placements.isEmpty();
    }

    @Override
    public String toString() {
        return "Limit{" +
                "side=" + side +
                ", price=" + price +
                ", placements=" + placements +
                '}';
    }

}