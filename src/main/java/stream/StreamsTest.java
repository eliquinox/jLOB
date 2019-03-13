package stream;

import com.google.common.base.Preconditions;
import delta.Placement;
import delta.Side;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectRBTreeMap;
import state.Limit;
import state.LimitOrderBook;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class StreamsTest {

    private static final Random RND = new Random();
    private static int[] nPlacementPicker = {4, 8, 16, 32, 64}; // Mesh to pick n of placements per limit from;
    private static int[] vPlacementPicker = {128, 256, 512, 1024, 2048}; // Mesh to pick volume of placements from;


    static LimitOrderBook getRandomizedOrderBook(int limitsPerSide, int tickSize, long mid){
        Preconditions.checkState(mid - limitsPerSide * tickSize >= 0, "Cannot have negative prices");
        LimitOrderBook limitOrderBook = LimitOrderBook.empty();
        long midTick = mid * tickSize;
        LongStream.range(mid - limitsPerSide, mid + limitsPerSide)
                .forEach(price -> {
                    int nps = nPlacementPicker[RND.nextInt(nPlacementPicker.length)];
                    long p = price * tickSize;
                    if(p < midTick){
                        Limit limit = new Limit(Side.BID, p);
                        IntStream.range(0, nps).forEach(i -> {
                            int amount = vPlacementPicker[RND.nextInt(vPlacementPicker.length)];
                            Placement placement = new Placement(limit, amount);
                            limitOrderBook.place(placement);
                        });} else {
                        Limit limit = new Limit(Side.OFFER, p);
                        IntStream.range(0, nps).forEach(i -> {
                            int amount = vPlacementPicker[RND.nextInt(vPlacementPicker.length)];
                            Placement placement = new Placement(limit, amount);
                            limitOrderBook.place(placement);
                        });
                    }
                });
        return limitOrderBook;
    }

    static Stream<Placement> placementStream(int limitsPerSide, int tickSize, long mid, double probMkt){
        //TODO: Debug staleness when matching
        long midTick = mid * tickSize;
        return RND.longs(mid - limitsPerSide, mid + limitsPerSide)
                .mapToObj(price -> {
                    long p = price * tickSize;
                    int amount = vPlacementPicker[RND.nextInt(vPlacementPicker.length)];
                    if(Math.random() >= 1 - probMkt){
                        if(p < midTick){
                            Limit limit = new Limit(Side.BID, p);
                            return new Placement(limit, amount);
                        } else {
                            Limit limit = new Limit(Side.OFFER, p);
                            return new Placement(limit, amount);
                        }
                    } else {
                        if(p < midTick){
                            Limit limit = new Limit(Side.OFFER, midTick - tickSize);
                            return new Placement(limit, amount);
                        } else {
                            Limit limit = new Limit(Side.BID, midTick + tickSize);
                            return new Placement(limit, amount);
                        }
                    }
                });
    }

    public static void main(String[] args){
        LimitOrderBook book = getRandomizedOrderBook(50, 10, 1000);
        placementStream(50, 10, 1000, 0.0).limit(1000)
                .forEach(p -> {
                    book.place(p);
                    System.out.println(book.info());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });

    }
}
