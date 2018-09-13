package state;

import delta.Placement;
import delta.Side;
import delta.Trade;
import exceptions.LimitPlacementMismatchException;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.lang.management.PlatformLoggingMXBean;
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

    public long match(long tradeSize){
        while (tradeSize > 0 && !placements.isEmpty()) {
            Placement placement = placements.get(0);
            long orderSize = placement.getSize();
            if (orderSize > tradeSize) {
                placement.reduce(tradeSize);
                tradeSize = 0;
            } else {
                placements.remove(0);
                tradeSize -= orderSize;
            }
        }
        return tradeSize;
    }

    public long getVolume(){
        return placements.stream()
                .map(Placement::getSize)
                .reduce(0L, (a,b) -> a + b);
    }

    public int getPlacementCount(){
        return placements.size();
    }

    public boolean isEmpty(){
        return placements.isEmpty();
    }

}