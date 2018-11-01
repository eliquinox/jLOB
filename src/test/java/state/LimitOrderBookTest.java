package state;

import com.google.common.collect.Ordering;
import delta.Placement;
import delta.Side;
import junit.framework.TestCase;
import org.junit.Before;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LimitOrderBookTest extends TestCase {


    @Before
    protected void setUp() throws Exception {
        System.gc();
        Thread.sleep(100);
    }

    public void testCreateEmpty(){
        LimitOrderBook book = LimitOrderBook.empty();
        assertTrue(book.isEmpty());
    }

}