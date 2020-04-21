package dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlacementTest {

    @Test
    public void testPlacementCreation(){
        Placement placement = new Placement(Side.BID, 100,10L);
        assertEquals(100L, placement.getPrice());
        assertEquals(10L, placement.getSize());
        assertEquals(Side.BID, placement.getSide());
    }

    @Test
    public void testCancellationCreation(){
        Placement placement = new Placement(Side.BID, 100,10L);
        Cancellation cancellation = new Cancellation(placement.getUuid(),5);
        assertEquals(100L, placement.getPrice());
        assertEquals(10L, placement.getSize());
        assertEquals(5L, cancellation.getSize());
    }

    @Test
    public void testPartialReduce(){
        Placement placement = new Placement(Side.BID, 100,10L);
        placement.reduce(5L);
        assertEquals(5L, placement.getSize());
    }

    @Test
    public void testFullReduce(){
        Placement placement = new Placement(Side.BID, 100,10L);
        placement.reduce(10L);
        assertEquals(0L, placement.getSize());
    }

}