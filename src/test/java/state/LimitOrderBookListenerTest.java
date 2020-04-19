package state;

import dto.Cancellation;
import dto.Match;
import dto.Placement;
import dto.Side;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LimitOrderBookListenerTest {

    private LimitOrderBook book;
    private LimitOrderBookListener listener;

    @BeforeEach
    protected void setUp() {
        listener = mock(LimitOrderBookListener.class);
        book = new LimitOrderBook(listener);
    }

    @Test
    void shouldCallOnPlacement() {
        // when
        Placement placement = new Placement(Side.BID, 1000, 100);
        book.place(placement);

        ArgumentCaptor<Placement> placementArgumentCaptor = ArgumentCaptor.forClass(Placement.class);
        verify(listener, times(1)).onPlacement(placementArgumentCaptor.capture());
        var placementArgument = placementArgumentCaptor.getValue();

        // then
        assertEquals(placement, placementArgument);
    }

    @Test
    void shouldCallOnCancel() {
        // when
        Placement placement = new Placement(Side.BID, 1000, 100);
        book.place(placement);
        Cancellation cancellation = new Cancellation(placement.getUuid(), 100);
        book.cancel(cancellation);

        ArgumentCaptor<Cancellation> cancellationArgumentCaptor = ArgumentCaptor.forClass(Cancellation.class);
        verify(listener, times(1)).onCancellation(cancellationArgumentCaptor.capture());
        var cancellationArgument = cancellationArgumentCaptor.getValue();

        ArgumentCaptor<Placement> placementArgumentCaptor = ArgumentCaptor.forClass(Placement.class);
        verify(listener, times(1)).onPlacement(placementArgumentCaptor.capture());
        var placementArgument = placementArgumentCaptor.getValue();

        // then
        assertEquals(cancellation, cancellationArgument);
        assertEquals(placement, placementArgument);
    }

    @Test
    void shouldCallOnMatchAndOnMatchOnPlacement() {
        // when
        Placement makerPlacement = new Placement(Side.BID, 1000, 100);
        book.place(makerPlacement);
        Placement takerPlacement = new Placement(Side.OFFER, 1000, 50);
        book.place(takerPlacement);

        ArgumentCaptor<Placement> placementArgumentCaptor = ArgumentCaptor.forClass(Placement.class);
        verify(listener, times(2)).onPlacement(placementArgumentCaptor.capture());
        var placementArguments = placementArgumentCaptor.getAllValues();

        ArgumentCaptor<Match> matchArgumentCaptor = ArgumentCaptor.forClass(Match.class);
        verify(listener, times(1)).onMatch(matchArgumentCaptor.capture());
        var matchArgument = matchArgumentCaptor.getValue();
        var match = new Match(makerPlacement.getUuid(), takerPlacement.getUuid(), takerPlacement.getSize());

        // then
        assertThat(placementArguments, containsInAnyOrder(makerPlacement, takerPlacement));
        assertEquals(matchArgument.getMakerPlacementId(), match.getMakerPlacementId());
        assertEquals(matchArgument.getTakerPlacementId(), match.getTakerPlacementId());
        assertEquals(matchArgument.getSize(), match.getSize());
    }
}
