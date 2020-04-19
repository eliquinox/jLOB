package state;

import dto.Cancellation;
import dto.Placement;
import dto.Side;
import exceptions.JLOBException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;


public class LimitOrderBookTest {

    private LimitOrderBook book;
    private LimitOrderBookListener listener;

    @BeforeEach
    protected void setUp() {
        listener = mock(LimitOrderBookListener.class);
        book = new LimitOrderBook(listener);

        book.place(new Placement(Side.OFFER, 1300, 245));
        book.place(new Placement(Side.OFFER, 1200, 25));
        book.place(new Placement(Side.OFFER, 1100, 125));

        book.place(new Placement(Side.BID, 1000, 100));
        book.place(new Placement(Side.BID, 900, 75));
        book.place(new Placement(Side.BID, 800, 125));
    }

    @Test
    public void testBuy() {
        book.place(new Placement(Side.BID, 1000, 100));
        assertEquals(1000, book.getBestBid());
        assertEquals(1100, book.getBestOffer());
        assertEquals(200, book.getBestBidAmount());
        assertEquals(125, book.getBestOfferAmount());
        assertEquals(1050, book.getMidPrice());
    }

    @Test
    public void testBuyCross() {
        book.place(new Placement(Side.BID, 1100, 100));
        assertEquals(1000, book.getBestBid());
        assertEquals(1100, book.getBestOffer());
        assertEquals(100, book.getBestBidAmount());
        assertEquals(25, book.getBestOfferAmount());
        assertEquals(1050, book.getMidPrice());
    }

    @Test
    public void testBestBuyFill() {
        book.place(new Placement(Side.BID, 1100, 125));
        assertEquals(1000, book.getBestBid());
        assertEquals(1200, book.getBestOffer());
        assertEquals(25, book.getBestOfferAmount());
        assertEquals(100, book.getBestBidAmount());
        assertEquals(1100, book.getMidPrice());
    }

    @Test
    public void testBestBuyOverfill() {
        book.place(new Placement(Side.BID, 1100, 225));
        assertEquals(1100, book.getBestBid());
        assertEquals(1200, book.getBestOffer());
        assertEquals(100, book.getBestBidAmount());
        assertEquals(25, book.getBestOfferAmount());
        assertEquals(1150, book.getMidPrice());
    }

    @Test
    public void testSell() {
        book.place(new Placement(Side.OFFER, 1100, 100));
        assertEquals(1000, book.getBestBid());
        assertEquals(1100, book.getBestOffer());
        assertEquals(100, book.getBestBidAmount());
        assertEquals(225, book.getBestOfferAmount());
        assertEquals(1050, book.getMidPrice());
    }

    @Test
    public void testBestSellCross() {
        book.place(new Placement(Side.OFFER, 1000, 50));
        assertEquals(1000, book.getBestBid());
        assertEquals(1100, book.getBestOffer());
        assertEquals(50, book.getBestBidAmount());
        assertEquals(125, book.getBestOfferAmount());
        assertEquals(1050, book.getMidPrice());
    }

    @Test
    public void testBestSellFill() {
        book.place(new Placement(Side.OFFER, 1000, 100));
        assertEquals(900, book.getBestBid());
        assertEquals(1100, book.getBestOffer());
        assertEquals(75, book.getBestBidAmount());
        assertEquals(125, book.getBestOfferAmount());
        assertEquals(1000, book.getMidPrice());
    }

    @Test
    public void testBestSellOverFill() {
        book.place(new Placement(Side.OFFER, 1000, 400));
        assertEquals(900, book.getBestBid());
        assertEquals(1000, book.getBestOffer());
        assertEquals(75, book.getBestBidAmount());
        assertEquals(300, book.getBestOfferAmount());
        assertEquals(950, book.getMidPrice());
    }

    @Test
    public void testCancellationAmountGreaterThanPlacement() {
        Placement placement = new Placement(Side.BID, 100, 100);
        Cancellation cancellation = new Cancellation(placement.getUuid(), 200);
        book.place(placement);
        assertThrows(
                JLOBException.class,
                () -> book.cancel(cancellation),
                "Placement does not exist or cancellation size is inappropriate"
        );
    }

    @Test
    public void testAveragePurchaseTop() {
        assertEquals(1100.0, book.getAveragePurchasePrice(25).doubleValue());
    }

    @Test
    public void testAveragePurchaseMultipleLevels(){
        double result = (1100. * 125. + 1200. * 25.) / 150.;
        assertEquals(result, book.getAveragePurchasePrice(150).doubleValue(), 0.01);
    }

    @Test
    public void testAveragePurchaseOverflow(){
        assertEquals(0., book.getAveragePurchasePrice(1000).doubleValue());
    }

    @Test
    public void testAveragePurchaseZero(){
        assertEquals(0., book.getAveragePurchasePrice(0).doubleValue());
    }

    @Test
    public void testAverageSaleTop(){
        assertEquals(1000., book.getAverageSalePrice(50).doubleValue());
    }

    @Test
    public void testAverageSaleMultipleLevels(){
        double result = (1000. * 100. + 900. * 75.) / 175.;
        assertEquals(result, book.getAverageSalePrice(175).doubleValue(), 0.01);
    }

    @Test
    public void testAverageSalePurchaseOverflow(){
        assertEquals(0., book.getAverageSalePrice(1000).doubleValue());
    }

    @Test
    public void testAverageSaleZero(){
        assertEquals(0., book.getAverageSalePrice(0).doubleValue());
    }

}