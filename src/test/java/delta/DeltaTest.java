package delta;

import junit.framework.TestCase;
import state.Limit;

public class DeltaTest extends TestCase{

    @Override
    protected void tearDown() throws Exception {
        System.gc();
        Thread.sleep(100);
    }

    public void testPlacementCreation(){
        Limit limit = new Limit(Side.BID, 100);
        Placement placement = new Placement(limit,10L);
        assertEquals(100L, placement.getPrice());
        assertEquals(10L, placement.getSize());
        assertEquals(Side.BID, placement.getSide());
    }

    public void testCancellationCreation(){
        Limit limit = new Limit(Side.BID, 100);
        Placement placement = new Placement(limit,10L);
        Cancellation cancellation = new Cancellation(placement,5);
        assertEquals(100L, placement.getPrice());
        assertEquals(10L, placement.getSize());
        assertEquals(5L, cancellation.getSize());
    }

    public void testTradeCreation(){
        Limit limit = new Limit(Side.BID, 100);
        Trade trade = new Trade(limit, 10L, Side.BID);
        assertEquals(100L, trade.getPrice());
        assertEquals(10L, (long) trade.getSize());
        assertEquals(Side.BID, trade.getSide());
    }

    public void testPartialCancellation(){
        Limit limit = new Limit(Side.BID, 100);
        Placement placement = new Placement(limit,10L);
        Cancellation cancellation = new Cancellation(placement,5);
        placement.reduce(cancellation.getSize());
        assertEquals(cancellation.getId(), placement.getId());
        assertEquals(5, placement.getSize());
        assertEquals(100, placement.getPrice());
    }

    public void testFullCancellation(){
        Limit limit = new Limit(Side.BID, 100);
        Placement placement = new Placement(limit,10L);
        Cancellation cancellation = new Cancellation(placement,10);
        placement.reduce(cancellation.getSize());
        assertEquals(cancellation.getId(), placement.getId());
        assertEquals(0, placement.getSize());
        assertEquals(100, placement.getPrice());
    }

    public void testPartialMatch(){
        Limit limit = new Limit(Side.BID, 100);
        Placement placement = new Placement(limit,10L);
        Trade trade = new Trade(limit,5L,Side.BID);
        placement.reduce(trade.getSize());
        assertFalse(trade.getId() == placement.getId());
        assertEquals(5L, placement.getSize());
        assertEquals(100, placement.getPrice());
    }

    public void testFullMatch(){
        Limit limit = new Limit(Side.BID, 100);
        Placement placement = new Placement(limit,10L);
        Trade trade = new Trade(limit,10L,Side.BID);
        placement.reduce(trade.getSize());
        assertFalse(trade.getId() == placement.getId());
        assertEquals(0, placement.getSize());
        assertEquals(100, placement.getPrice());
    }

}