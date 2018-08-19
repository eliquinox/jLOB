package delta;

import junit.framework.TestCase;

public class DeltaTest extends TestCase{

    @Override
    protected void tearDown() throws Exception {
        System.gc();
        Thread.sleep(100);
    }

    public void testPlacementCreation(){
        Placement placement = new Placement(100.0,10, Side.BID);
        assertEquals(100.0, placement.getPrice());
        assertEquals(10, placement.getSize());
        assertEquals(Side.BID, placement.getSide());
    }

    public void testCancellationCreation(){
        Placement placement = new Placement(100.0,10, Side.BID);
        Cancellation cancellation = new Cancellation(placement,5);
        assertEquals(100.0, placement.getPrice());
        assertEquals(10, placement.getSize());
        assertEquals(5, cancellation.getSize());
    }

    public void testTradeCreation(){
        Trade trade = new Trade(100.0, 10, Side.BID);
        assertEquals(100.0, trade.getPrice());
        assertEquals(10, trade.getSize());
        assertEquals(Side.BID, trade.getSide());
    }

    public void testPartialCancellation(){
        Placement placement = new Placement(100.0,10, Side.BID);
        Cancellation cancellation = new Cancellation(placement,5);
        placement = placement.cancel(cancellation);
        assertEquals(5, placement.getSize());
        assertEquals(100.0, placement.getPrice());
    }

    public void testPartialMatch(){
        Placement placement = new Placement(100.0,10, Side.BID);
        Trade trade = new Trade(100,5,Side.BID);
        placement = placement.match(trade);
        assertEquals(5, placement.getSize());
        assertEquals(100.0, placement.getPrice());
    }

}