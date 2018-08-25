package delta;

import junit.framework.TestCase;

public class DeltaTest extends TestCase{

    @Override
    protected void tearDown() throws Exception {
        System.gc();
        Thread.sleep(100);
    }

    public void testPlacementCreation(){
        Placement placement = new Placement(100.0,10L, Side.BID);
        assertEquals(100.0, placement.getPrice());
        assertEquals(10L, (long) placement.getSize());
        assertEquals(Side.BID, placement.getSide());
    }

    public void testCancellationCreation(){
        Placement placement = new Placement(100.0,10L, Side.BID);
        Cancellation cancellation = new Cancellation(placement,5);
        assertEquals(100.0, placement.getPrice());
        assertEquals(10L, (long) placement.getSize());
        assertEquals(5L, (long) cancellation.getSize());
    }

    public void testTradeCreation(){
        Trade trade = new Trade(100.0, 10L, Side.BID);
        assertEquals(100.0, trade.getPrice());
        assertEquals(10L, (long) trade.getSize());
        assertEquals(Side.BID, trade.getSide());
    }

    public void testPartialCancellation(){
        Placement placement = new Placement(100.0,10L, Side.BID);
        Cancellation cancellation = new Cancellation(placement,5);
        placement = placement.cancel(cancellation);
        assertEquals(5, (long) placement.getSize());
        assertEquals(100.0, placement.getPrice());
    }

    public void testPartialMatch(){
        Placement placement = new Placement(100.0,10L, Side.BID);
        Trade trade = new Trade(100.0,5L,Side.BID);
        placement = placement.match(trade);
        assertEquals(5L, (long) placement.getSize());
        assertEquals(100.0, placement.getPrice());
    }

    public void testToString(){
        Placement placement = new Placement(100.0,10L, Side.BID);
        System.out.println(placement);
    }

}