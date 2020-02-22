import connectivity.http.LimitOrderBookApi;

import static spark.Spark.*;


public class LimitOrderBookHttpApplication {

    public static void main(String[] args) {
        path("book", () -> {
            get("", LimitOrderBookApi.getLimitOrderBook);
            post("", LimitOrderBookApi.placeOrder);
            delete("", LimitOrderBookApi.reduceOrder);
        });
        post("vwap", LimitOrderBookApi.getVwap);
    }
}
