package state;

import dto.Placement;
import dto.Side;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    public long match(Placement incomingPlacement, Object2ObjectOpenHashMap<UUID, Placement> placementIds){
        while (incomingPlacement.getSize() > 0 && !placements.isEmpty()) {
            Placement restingPlacement = placements.get(0);
            UUID restingPlacementId = restingPlacement.getId();
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
        return placements.stream().map(Placement::getSize).reduce(0L, Long::sum);
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