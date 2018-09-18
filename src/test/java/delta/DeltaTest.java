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

    public void testPartialReduce(){
        Limit limit = new Limit(Side.BID, 100);
        Placement placement = new Placement(limit,10L);
        placement.reduce(5L);
        assertEquals(5L, placement.getSize());
    }

    public void testFullReduce(){
        Limit limit = new Limit(Side.BID, 100);
        Placement placement = new Placement(limit,10L);
        placement.reduce(10L);
        assertEquals(0L, placement.getSize());
    }

}