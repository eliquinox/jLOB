package dto;

import org.junit.jupiter.api.Test;
import static dto.Placement.placement;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlacementTest {

    private final Placement placement = placement()
            .withSide(Side.BID)
            .withPrice(100)
            .withSize(10)
            .build();

    @Test
    public void testPlacementCreation(){
        assertEquals(100L, placement.getPrice());
        assertEquals(10L, placement.getSize());
        assertEquals(Side.BID, placement.getSide());
    }

    @Test
    public void testCancellationCreation(){
        Cancellation cancellation = new Cancellation(placement.getUuid(),5);
        assertEquals(100L, placement.getPrice());
        assertEquals(10L, placement.getSize());
        assertEquals(5L, cancellation.getSize());
        assertEquals(cancellation.getPlacementUuid(), placement.getUuid());
    }

    @Test
    public void testPartialReduce(){
        placement.reduce(5L);
        assertEquals(5L, placement.getSize());
    }

    @Test
    public void testFullReduce(){
        placement.reduce(10L);
        assertEquals(0L, placement.getSize());
    }

}