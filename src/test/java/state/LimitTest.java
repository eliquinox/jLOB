package state;

import dto.Placement;
import dto.Side;
import junit.framework.TestCase;


public class LimitTest extends TestCase {


    @Override
    protected void tearDown() throws Exception {
        System.gc();
        Thread.sleep(100);
    }

    public void testLimitCreation() {
        Limit limit = new Limit(Side.BID, 100L);
        assertTrue(limit.isEmpty());
        assertEquals(Side.BID, limit.getSide());
        assertEquals(100L, limit.getPrice());
    }


    public void testLimitPlace() {
        Limit limit = new Limit(Side.BID, 100L);
        Placement placement = new Placement(limit.getSide(), limit.getPrice(), 10L);
        Placement placement1 = new Placement(limit.getSide(), limit.getPrice(), 10L);
        Placement placement2 = new Placement(limit.getSide(), limit.getPrice(), 10L);
        assertEquals(placement.getSide(), limit.getSide());
        assertEquals(placement.getPrice(), limit.getPrice());
        limit.place(placement);limit.place(placement1);limit.place(placement2);
        assertEquals(3, limit.getPlacementCount());
        assertEquals(30L, limit.getVolume());
    }

    public void testLimitRemove() {
        Limit limit = new Limit(Side.BID, 100L);
        Placement placement = new Placement(limit.getSide(), limit.getPrice(), 10L);
        Placement placement1 = new Placement(limit.getSide(), limit.getPrice(), 10L);
        Placement placement2 = new Placement(limit.getSide(), limit.getPrice(), 10L);
        limit.place(placement);limit.place(placement1);limit.place(placement2);
        limit.remove(placement);limit.remove(placement1);limit.remove(placement2);
        assertTrue(limit.isEmpty());
    }

}
