package state;

import delta.Placement;
import delta.Side;
import junit.framework.TestCase;

import java.util.Arrays;

public class StateTest extends TestCase{

    @Override
    protected void tearDown() throws Exception {
        System.gc();
        Thread.sleep(100);
    }

    public void testLimitCreation() {
        Limit limit = new Limit(Side.BID, 100.0);
        assertEquals(Side.BID, limit.getSide());
        assertEquals(100.0, limit.getPriceLevel());
    }

    public void testLimitPlace(){
        Placement placement = new Placement(100.0, 10, Side.BID);
        Limit limit = new Limit(Side.BID, 100.0);
        limit.place(placement);
        assertEquals(limit.getPlacements(), Arrays.asList(placement));
        assertEquals(placement.getPrice(), limit.getPriceLevel());
        assertEquals(placement.getSide(), limit.getSide());
        assertEquals(placement.getSize(), limit.getTotalVolume().get().longValue());
    }

    public void testLimitPlacePriceMismatch(){
        //TODO: Enforce same price levels between placements and limit; .getTotalVolume() handle the boiler plate
        Placement placement = new Placement(99, 10, Side.BID);
        Limit limit = new Limit(Side.BID, 100.0);
        limit.place(placement);
        assertEquals(limit.getPlacements(), Arrays.asList(placement));
        assertEquals(placement.getPrice(), limit.getPriceLevel());
        assertEquals(placement.getSide(), limit.getSide());
        assertEquals(placement.getSize(), limit.getTotalVolume().get().longValue());
    }

    public void testLimitRemove(){
        Placement placement = new Placement(99, 10, Side.BID);
        Limit limit = new Limit(Side.BID, 100.0);
        limit.place(placement);
        limit.remove(placement);
        assertTrue(limit.isEmpty());
        assertEquals(0, limit.getTotalVolume().get().longValue());
    }

}
