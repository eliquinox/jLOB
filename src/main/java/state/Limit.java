package state;

import delta.Placement;
import delta.Side;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Limit {

    private Side side;
    private Double priceLevel;
    private List<Placement> placements;

    public Limit(Side side, Double priceLevel) {
        this.side = side;
        this.priceLevel = priceLevel;
        this.placements= new ArrayList<>();
    }

    public Side getSide() {
        return side;
    }

    public Double getPriceLevel() {
        return priceLevel;
    }

    public List<Placement> getOrders() {
        return placements;
    }

    public boolean isEmpty() {
        return placements.isEmpty();
    }

    private Limit apply(Placement placement, Function<Placement, Limit> function) {
        return function.apply(placement);
    }

    public Limit place(Limit limit, Placement placement){
        return apply(placement, o -> {
           limit.placements.add(o);
           return limit;
        });
    }

    public Limit remove(Limit limit, Placement placement){
        return apply(placement, o -> {
            limit.placements.remove(o);
            return limit;
        });
    }

    public Limit place(long qty, Placement placement){
        return null;
    }

    public Limit remove(long qty, Placement placement){
        return null;
    }

}