package delta;

import junit.framework.TestCase;

public class DeltaTest extends TestCase{

    @Override
    protected void tearDown() throws Exception {
        System.gc();
        Thread.sleep(100);
    }

    public void testPlacementCreation(){
        Placement placement = new Placement(100.0,10, Side.Bid);
        System.out.println(placement);
        assertEquals(100.0, placement.getPrice());
        assertEquals(10, placement.getSize());
        assertEquals(Side.Bid, placement.getSide());
    }

    public void testCancellationCreation(){
        Placement placement = new Placement(100.0,10, Side.Bid);
        Cancellation cancellation = new Cancellation(placement,5);
        assertEquals(100.0, placement.getPrice());
        assertEquals(10, placement.getSize());
        assertEquals(5, cancellation.getSize());
    }

    public void testCancellation(){
        Placement placement = new Placement(100.0,10, Side.Bid);
        Cancellation cancellation = new Cancellation(placement,5);
        placement = placement.cancel(cancellation);
        assertEquals(5, placement.getSize());
        assertEquals(100.0, placement.getPrice());
    }

}
