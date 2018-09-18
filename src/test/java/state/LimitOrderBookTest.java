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

    public void testCreateEmpty(){
        LimitOrderBook book = LimitOrderBook.empty();
        assertTrue(book.isEmpty());
    }

}