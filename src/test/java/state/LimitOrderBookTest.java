package state;

import com.google.common.collect.Ordering;
import delta.Placement;
import delta.Side;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LimitOrderBookTest extends TestCase {

    private LimitOrderBook book;

    @Before
    protected void setUp() throws Exception {
        book = LimitOrderBook.empty();
    }

    @Test
    public void buy(){
        Limit limit = new Limit(Side.OFFER, 100);
        Limit limit1 = new Limit(Side.BID, 100);
        Placement placement = new Placement(limit, 10);
        Placement placement1 = new Placement(limit1, 10);
        book.place(placement);
        book.place(placement1);
        System.out.println(limit);
        System.out.println(limit);
    }

}