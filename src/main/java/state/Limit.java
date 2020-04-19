package state;

import dto.Match;
import dto.Placement;
import dto.Side;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Limit {
    private final Side side;

    private final long price;
    private final List<Placement> placements;
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

    public long match(Placement takerPlacement, Object2ObjectOpenHashMap<UUID, Placement> placementSet,
                      Consumer<Match> matchCallback) {
        while (takerPlacement.getSize() > 0 && !placements.isEmpty()) {
            Placement makerPlacement = placements.get(0);
            UUID restingPlacementId = makerPlacement.getUuid();
            long orderSize = makerPlacement.getSize();
            if (orderSize > takerPlacement.getSize()) {
                makerPlacement.reduce(takerPlacement.getSize());
                takerPlacement.reduce(takerPlacement.getSize());
            } else {
                placements.remove(0);
                takerPlacement.reduce(orderSize);
                placementSet.remove(restingPlacementId);
            }
            matchCallback.accept(new Match(makerPlacement.getUuid(), takerPlacement.getUuid(), orderSize));
        }
        return takerPlacement.getSize();
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