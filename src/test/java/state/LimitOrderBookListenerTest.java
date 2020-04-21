package state;

import cache.Cache;
import dto.Cancellation;
import dto.Match;
import dto.Placement;
import dto.Side;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class LimitOrderBookListenerTest {

    private LimitOrderBook book;
    private LimitOrderBookListener listener;

    private static boolean matchEqualButForTimestamp(Match expected, Match actual) {
        return expected.getMakerPlacementId().equals(actual.getMakerPlacementId())
                && expected.getTakerPlacementId().equals(actual.getTakerPlacementId())
                && expected.getSize() == actual.getSize();
    }

    @BeforeEach
    protected void setUp() {
        listener = mock(LimitOrderBookListener.class);
        Cache cache = mock(Cache.class);
        book = new LimitOrderBook(listener, cache);
    }

    @Test
    void shouldCallOnPlacementWhenPlacing() {
        // when
        Placement placement = new Placement(Side.BID, 1000, 100);
        book.place(placement);

        ArgumentCaptor<Placement> placementArgumentCaptor = ArgumentCaptor.forClass(Placement.class);
        ArgumentCaptor<LimitOrderBook> bookArgumentCaptor = ArgumentCaptor.forClass(LimitOrderBook.class);
        verify(listener, times(1)).onPlacement(
                placementArgumentCaptor.capture(),
                bookArgumentCaptor.capture()
        );
        var placementArgument = placementArgumentCaptor.getValue();
        var bookArgument = bookArgumentCaptor.getValue();

        // then
        assertEquals(placement, placementArgument);
        assertEquals(book, bookArgument);
    }

    @Test
    void shouldCallOnCancelWhenCancelling() {
        // when
        Placement placement = new Placement(Side.BID, 1000, 100);
        book.place(placement);
        Cancellation cancellation = new Cancellation(placement.getUuid(), 100);
        book.cancel(cancellation);

        ArgumentCaptor<Cancellation> cancellationArgumentCaptor = ArgumentCaptor.forClass(Cancellation.class);
        ArgumentCaptor<LimitOrderBook> bookArgumentCaptor1 = ArgumentCaptor.forClass(LimitOrderBook.class);

        verify(listener, times(1)).onCancellation(
                cancellationArgumentCaptor.capture(),
                bookArgumentCaptor1.capture()
        );
        var cancellationArgument = cancellationArgumentCaptor.getValue();

        ArgumentCaptor<Placement> placementArgumentCaptor = ArgumentCaptor.forClass(Placement.class);
        ArgumentCaptor<LimitOrderBook> bookArgumentCaptor2 = ArgumentCaptor.forClass(LimitOrderBook.class);

        verify(listener, times(1)).onPlacement(
                placementArgumentCaptor.capture(),
                bookArgumentCaptor2.capture()
        );
        var placementArgument = placementArgumentCaptor.getValue();

        // then
        assertEquals(cancellation, cancellationArgument);
        assertEquals(placement, placementArgument);
    }

    @Test
    void shouldCallOnPlacementAndOnMatchWhenMatching() {
        // when
        Placement makerPlacement = new Placement(Side.BID, 1000, 100);
        book.place(makerPlacement);
        Placement takerPlacement = new Placement(Side.OFFER, 1000, 50);
        book.place(takerPlacement);

        ArgumentCaptor<Placement> placementArgumentCaptor = ArgumentCaptor.forClass(Placement.class);
        ArgumentCaptor<LimitOrderBook> bookArgumentCaptor = ArgumentCaptor.forClass(LimitOrderBook.class);
        verify(listener, times(2)).onPlacement(
                placementArgumentCaptor.capture(),
                bookArgumentCaptor.capture()
        );
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

    @Test
    void shouldCallOnMatchMultipleTimesWhenMatchingAgainstMultipleMakers() {
        // when
        Placement makerPlacement1 = new Placement(Side.BID, 1000, 100);
        Placement makerPlacement2 = new Placement(Side.BID, 1000, 50);
        Placement makerPlacement3 = new Placement(Side.BID, 1000, 25);
        Placement takerPlacement = new Placement(Side.OFFER, 1000, 175);

        book.place(makerPlacement1);
        book.place(makerPlacement2);
        book.place(makerPlacement3);
        book.place(takerPlacement);

        ArgumentCaptor<Placement> placementArgumentCaptor = ArgumentCaptor.forClass(Placement.class);
        ArgumentCaptor<LimitOrderBook> bookArgumentCaptor = ArgumentCaptor.forClass(LimitOrderBook.class);
        verify(listener, times(4)).onPlacement(
                placementArgumentCaptor.capture(),
                bookArgumentCaptor.capture()
        );
        var placementArguments = placementArgumentCaptor.getAllValues();

        ArgumentCaptor<Match> matchArgumentCaptor = ArgumentCaptor.forClass(Match.class);
        verify(listener, times(3)).onMatch(matchArgumentCaptor.capture());
        var matchArguments = matchArgumentCaptor.getAllValues();
        var match1 = new Match(makerPlacement1.getUuid(), takerPlacement.getUuid(), 100);
        var match2 = new Match(makerPlacement2.getUuid(), takerPlacement.getUuid(), 50);
        var match3 = new Match(makerPlacement3.getUuid(), takerPlacement.getUuid(), 25);

        assertThat(placementArguments, containsInAnyOrder(makerPlacement1, makerPlacement2, makerPlacement3, takerPlacement));
        assertTrue(matchEqualButForTimestamp(match1, matchArguments.get(0)));
        assertTrue(matchEqualButForTimestamp(match2, matchArguments.get(1)));
        assertTrue(matchEqualButForTimestamp(match3, matchArguments.get(2)));
    }

    @Test
    void shouldCallOnMatchMultipleTimesWhenTakeOverflows() {
        // when
        Placement makerPlacement1 = new Placement(Side.BID, 1000, 100);
        Placement makerPlacement2 = new Placement(Side.BID, 1000, 50);
        Placement makerPlacement3 = new Placement(Side.BID, 1000, 25);
        Placement takerPlacement = new Placement(Side.OFFER, 1000, 200);

        book.place(makerPlacement1);
        book.place(makerPlacement2);
        book.place(makerPlacement3);
        book.place(takerPlacement);

        ArgumentCaptor<Placement> placementArgumentCaptor = ArgumentCaptor.forClass(Placement.class);
        ArgumentCaptor<LimitOrderBook> bookArgumentCaptor = ArgumentCaptor.forClass(LimitOrderBook.class);
        verify(listener, times(4)).onPlacement(
                placementArgumentCaptor.capture(),
                bookArgumentCaptor.capture()
        );
        var placementArguments = placementArgumentCaptor.getAllValues();

        ArgumentCaptor<Match> matchArgumentCaptor = ArgumentCaptor.forClass(Match.class);
        verify(listener, times(3)).onMatch(matchArgumentCaptor.capture());
        var matchArguments = matchArgumentCaptor.getAllValues();
        var match1 = new Match(makerPlacement1.getUuid(), takerPlacement.getUuid(), 100);
        var match2 = new Match(makerPlacement2.getUuid(), takerPlacement.getUuid(), 50);
        var match3 = new Match(makerPlacement3.getUuid(), takerPlacement.getUuid(), 25);

        assertThat(placementArguments, containsInAnyOrder(makerPlacement1, makerPlacement2, makerPlacement3, takerPlacement));
        assertTrue(matchEqualButForTimestamp(match1, matchArguments.get(0)));
        assertTrue(matchEqualButForTimestamp(match2, matchArguments.get(1)));
        assertTrue(matchEqualButForTimestamp(match3, matchArguments.get(2)));

        // check the remainder is resting
        assertThat(book.getBestOffer(), equalTo(1000L));
        assertThat(book.getBestOfferAmount(), equalTo(25L));
    }
}
