import connectivity.http.LimitOrderBookApi;

import static spark.Spark.*;


public class LimitOrderBookHttpApplication {

    private static final String ORIGIN = "";

    public static void main(String[] args) {
        path("book", () -> {
            get(ORIGIN, LimitOrderBookApi.getLimitOrderBook);
            post(ORIGIN, LimitOrderBookApi.placeOrder);
            delete(ORIGIN, LimitOrderBookApi.reduceOrder);
        });
        post("vwap", LimitOrderBookApi.getVwap);
    }
}
