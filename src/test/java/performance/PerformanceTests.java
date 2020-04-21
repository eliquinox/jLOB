package performance;

import cache.Cache;
import dto.Cancellation;
import dto.Placement;
import dto.Side;
import org.openjdk.jmh.annotations.*;
import state.LimitOrderBook;

import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.mock;

public class PerformanceTests {

    @State(Scope.Thread)
    public static class MyState {
        LimitOrderBook book;
        Placement placement = new Placement(Side.BID, 100, 100);
        Cancellation cancellation = new Cancellation(placement.getUuid(), 100);

        @Setup(Level.Invocation)
        public void doSetup() {
            book = LimitOrderBook.empty();
            book.place(placement);
        }

    }

    @Benchmark @BenchmarkMode(Mode.AverageTime) @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void testPlacement(MyState state) {
        /**
         * Benchmark                       Mode  Cnt    Score   Error  Units
         * PerformanceTests.testPlacement  avgt   25  127.061 ± 3.260  ns/op
         */
        state.book.place(state.placement);
    }

    @Benchmark @BenchmarkMode(Mode.AverageTime) @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void testCancellation(MyState state) {
        /**
         * Benchmark                          Mode  Cnt   Score   Error  Units
         * PerformanceTests.testCancellation  avgt   25  62.609 ± 1.535  ns/op
         */
        state.book.cancel(state.cancellation);
    }

    @Benchmark @BenchmarkMode(Mode.AverageTime) @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void testMatch(MyState state) {
        /**
         * Benchmark                   Mode  Cnt    Score   Error  Units
         * PerformanceTests.testMatch  avgt   25  122.326 ± 2.526  ns/op
         */
        state.book.place(new Placement(Side.OFFER, 100, 100));
    }
}
