package stream;

import delta.Placement;
import delta.Side;
import it.unimi.dsi.fastutil.longs.Long2ObjectRBTreeMap;
import state.Limit;
import state.LimitOrderBook;

import java.util.stream.Stream;

public class StreamsTest {

    static LimitOrderBook getRandomizedOrderBook(int limitsPerSide, int placementsPerLimit, long mid){
        //TODO: Implement a randomized book
        return null;
    }

    public static void main(String[] args){
        LimitOrderBook book = LimitOrderBook.empty();
        Stream<Placement> stream = DeltaStreams.getDummyPlacementStream();
        stream.forEach(p -> {
            System.out.println(p);
            book.place(p);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(book);
        });
    }
}
