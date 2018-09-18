package state;

import delta.Placement;
import delta.Side;
import junit.framework.TestCase;


public class LimitTest extends TestCase {

    //TODO: Check for correct limit / side price arguments on construction

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
        Placement placement = new Placement(limit, 10L);
        Placement placement1 = new Placement(limit, 10L);
        Placement placement2 = new Placement(limit, 10L);
        assertEquals(placement.getSide(), limit.getSide());
        assertEquals(placement.getPrice(), limit.getPrice());
        limit.place(placement);limit.place(placement1);limit.place(placement2);
        assertEquals(3, limit.getPlacementCount());
        assertEquals(30L, limit.getVolume());
    }

    public void testLimitRemove() {
        Limit limit = new Limit(Side.BID, 100L);
        Placement placement = new Placement(limit, 10L);
        Placement placement1 = new Placement(limit, 10L);
        Placement placement2 = new Placement(limit, 10L);
        limit.place(placement);limit.place(placement1);limit.place(placement2);
        limit.remove(placement);limit.remove(placement1);limit.remove(placement2);
        assertTrue(limit.isEmpty());
    }

    public void testLimitPartialMatch() {
        Limit limit = new Limit(Side.BID, 100L);
        Limit limit1 = new Limit(Side.OFFER, 100L);
        Placement placement = new Placement(limit, 10L);
        Placement placement1 = new Placement(limit1, 5L);
        limit.place(placement); limit1.place(placement1);
        limit.match(placement1.getSize());
        assertEquals(5L, placement.getSize());
        assertEquals(5L, limit.getVolume());
    }

    public void testLimitFullMatch(){
        Limit limit = new Limit(Side.BID, 100L);
        Limit limit1 = new Limit(Side.OFFER, 100L);
        Placement placement = new Placement(limit, 10L);
        Placement placement1 = new Placement(limit1, 10L);
        limit.place(placement); limit1.place(placement1);
        limit.match(placement1.getSize());
        assertEquals(0L, limit.getVolume());
    }

    public void testLimitOverMatch(){
        Limit limit = new Limit(Side.BID, 100L);
        Limit limit1 = new Limit(Side.OFFER, 100L);
        Placement placement = new Placement(limit, 10L);
        Placement placement1 = new Placement(limit1, 20L);
        limit.place(placement); limit1.place(placement1);
        long remainder = limit.match(placement1.getSize());
        assertEquals(0L, limit.getVolume());
        assertEquals(10L, remainder);
    }

}
