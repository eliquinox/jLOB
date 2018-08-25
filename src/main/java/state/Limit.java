package state;

import delta.Placement;
import delta.Side;
import exceptions.LimitPlacementMismatchException;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

public class Limit implements Comparable<Limit>{

    private Side side;
    private Double priceLevel;
    private List<Placement> placements;

    public Limit(Side side, Double priceLevel) {
        this.side = side;
        this.priceLevel = priceLevel;
        this.placements = new ArrayList<>();
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

    public Long getTotalVolume(){
        return placements.stream().map(p -> p.getSize()).reduce((a,b) -> a+b).orElse(0L);
    }

    public int getPlacementCount(){
        return placements.size();
    }

    public void place(Placement placement) throws LimitPlacementMismatchException {
        if(!placement.getPrice().equals(priceLevel)|| placement.getSide() != side)
            throw new LimitPlacementMismatchException("Placement and limit levels mismatch!");
        this.placements.add(placement);
    }

    public void remove(Placement placement) throws LimitPlacementMismatchException {
        if(!placement.getPrice().equals(priceLevel)|| placement.getSide() != side)
            throw new LimitPlacementMismatchException("Placement and limit levels mismatch!");
        this.placements.remove(placement);
    }

    public boolean isEmpty() {
        return placements.isEmpty();
    }

    @Override
    public int compareTo(Limit o) {
        // Comparator.naturalOrder() will handle sorting as expected
        // binarySearch to be ran without specifying a Comparator
        return side == Side.OFFER ? o.getPriceLevel().compareTo(priceLevel) :
                priceLevel.compareTo(o.getPriceLevel());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Limit limit = (Limit) o;
        return new EqualsBuilder()
                .append(side, limit.side)
                .append(priceLevel, limit.priceLevel)
                .append(placements, limit.placements)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(side)
                .append(priceLevel)
                .append(placements)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("side", side)
                .append("priceLevel", priceLevel)
                .append("placements", placements)
                .toString();
    }
}