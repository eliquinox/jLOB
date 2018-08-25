package state;

import delta.Placement;
import delta.Side;
import exceptions.LimitPlacementMismatchException;
import junit.framework.TestCase;
import org.junit.Test;
import java.util.Arrays;


public class LimitTest extends TestCase {

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


    public void testLimitPlace() throws LimitPlacementMismatchException {
        Placement placement = new Placement(100.0, 10L, Side.BID);
        Placement placement1 = new Placement(100.0, 10L, Side.BID);
        Placement placement2 = new Placement(100.0, 10L, Side.BID);
        Limit limit = new Limit(Side.BID, 100.0);
        assertEquals(placement.getSide(), limit.getSide());
        assertEquals(placement.getPrice(), limit.getPriceLevel());
        limit.place(placement);limit.place(placement1);limit.place(placement2);
        assertEquals(Long.valueOf(30L), limit.getTotalVolume());
        assertEquals(limit.getPlacements(), Arrays.asList(placement, placement1, placement2));
    }

//    @Test(expected = LimitPlacementMismatchException.class)
//    public void testLimitPlaceMismatch() throws LimitPlacementMismatchException {
//        //TODO: Enforce same price levels between placements and limit; .getTotalVolume() handle the boiler plate
//        Placement placement = new Placement(99.0, 10L, Side.BID);
//        Limit limit = new Limit(Side.BID, 100.0);
//        limit.place(placement);
//    }

    public void testLimitRemove() throws LimitPlacementMismatchException {
        Placement placement = new Placement(100.0, 10L, Side.BID);
        Placement placement1 = new Placement(100.0, 10L, Side.BID);
        Placement placement2 = new Placement(100.0, 10L, Side.BID);
        Limit limit = new Limit(Side.BID, 100.0);
        assertEquals(placement.getSide(), limit.getSide());
        assertEquals(placement.getPrice(), limit.getPriceLevel());
        limit.place(placement);limit.place(placement1);limit.place(placement2);
        limit.remove(placement);limit.remove(placement1);limit.remove(placement2);
        assertTrue(limit.isEmpty());
        assertEquals(Long.valueOf(0), limit.getTotalVolume());
    }

    public void testLimitSize() throws LimitPlacementMismatchException {
        Placement placement = new Placement(100.0, 10L, Side.BID);
        Limit limit = new Limit(Side.BID, 100.0);
        limit.place(placement);
        assertEquals(1, limit.getPlacementCount());

    }

}
