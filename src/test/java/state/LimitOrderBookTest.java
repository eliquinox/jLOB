package state;

import com.google.common.collect.Ordering;
import delta.Placement;
import delta.Side;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LimitOrderBookTest extends TestCase {

    @Override
    protected void tearDown() throws Exception {
        System.gc();
        Thread.sleep(100);
    }
//
//    public void testCreateEmpty(){
//        LimitOrderBook book = LimitOrderBook.empty();
//        assertTrue(book.isEmpty());
//    }

//    public void testBidLimitOrdering(){
//        List<Limit> limits = new ArrayList<>();
//        Limit limit = new Limit(Side.BID, 100.0);
//        Limit limit2 = new Limit(Side.BID, 98.0);
//        Limit limit1 = new Limit(Side.BID, 99.0);
//        limits.add(limit2); limits.add(limit); limits.add(limit1);
//        limits.sort(Comparator.naturalOrder());
//        assertTrue(Ordering.natural().isOrdered(limits));
//        System.out.println("Bids: " + limits);
//    }
//
//    public void testOfferLimitOrdering(){
//        List<Limit> limits = new ArrayList<>();
//        Limit limit = new Limit(Side.OFFER, 100.0);
//        Limit limit2 = new Limit(Side.OFFER, 98.0);
//        Limit limit1 = new Limit(Side.OFFER, 99.0);
//        limits.add(limit2); limits.add(limit); limits.add(limit1);
//        limits.sort(Comparator.naturalOrder());
//        assertTrue(Ordering.natural().isOrdered(limits));
//        System.out.println("Offers: " + limits);
//    }
//
//    public void testPlaceNewSameLimit(){
//        LimitOrderBook book = ListLimitOrderBook.empty();
//        Placement placement = new Placement(100.0,10L, Side.BID);
//        Placement placement1 = new Placement(100.0,15L, Side.BID);
//        Placement placement2= new Placement(100.0,5L, Side.BID);
//        book.place(placement).place(placement1).place(placement2);
//        System.out.println(book.getBestBidLimit());
//
//    }
//
//    public void testPlaceExistingLimit(){
//
//    }
}