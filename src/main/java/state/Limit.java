package state;

import com.sun.javaws.exceptions.InvalidArgumentException;
import delta.Placement;
import delta.Side;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.function.Function;

public class Limit implements Comparable<Limit>{

    private Side side;
    private double priceLevel;
    private List<Placement> placements;

    public Limit(Side side, Double priceLevel) {
        this.side = side;
        this.priceLevel = priceLevel;
        this.placements = new ArrayList<>();
    }

    private Limit(Side side, Double priceLevel, List<Placement> placements) {
        this.side = side;
        this.priceLevel = priceLevel;
        this.placements = placements;
    }

    public Side getSide() {
        return side;
    }

    public Double getPriceLevel() {
        return priceLevel;
    }

    public List<Placement> getPlacements() {
        return new ArrayList<>(placements);
    }

    public Optional<Long> getTotalVolume(){
        return placements.stream()
                .map(p -> p.getSize())
                .reduce((a,b) -> a+b);
    }

    public int getPlacementCount(){
        return placements.size();
    }

    // Places the placement on the limit
    public void place(Placement placement) {
        this.placements.add(placement);
    }

    // Removes the placement from the limit
    public void remove(Placement placement){
        this.placements.remove(placement);
    }

    @Override
    public int compareTo(Limit o) {
        return getPriceLevel().compareTo(o.getPriceLevel());
    }

    public boolean isEmpty() {
        return placements.isEmpty();
    }
}