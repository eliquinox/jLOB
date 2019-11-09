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
        Placement placement = new Placement(Side.BID, 100,10L);
        assertEquals(100L, placement.getPrice());
        assertEquals(10L, placement.getSize());
        assertEquals(Side.BID, placement.getSide());
    }

    public void testCancellationCreation(){
        Placement placement = new Placement(Side.BID, 100,10L);
        Cancellation cancellation = new Cancellation(placement.getId(),5);
        assertEquals(100L, placement.getPrice());
        assertEquals(10L, placement.getSize());
        assertEquals(5L, cancellation.getSize());
    }

    public void testPartialReduce(){
        Placement placement = new Placement(Side.BID, 100,10L);
        placement.reduce(5L);
        assertEquals(5L, placement.getSize());
    }

    public void testFullReduce(){
        Placement placement = new Placement(Side.BID, 100,10L);
        placement.reduce(10L);
        assertEquals(0L, placement.getSize());
    }

}