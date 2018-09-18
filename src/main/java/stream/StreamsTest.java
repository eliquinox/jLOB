package stream;

import delta.Placement;
import state.LimitOrderBook;

import java.util.stream.Stream;

public class StreamsTest {
    public static void main(String[] args){
        LimitOrderBook book = LimitOrderBook.empty();
        Stream<Placement> stream = DeltaStreams.getDummyPlacementStream();
        stream.forEach(p -> {
            System.out.println(p);
            //book.place(p);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //System.out.println(book);
        });
    }
}
