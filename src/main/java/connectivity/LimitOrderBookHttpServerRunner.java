package connectivity;

import com.google.inject.Inject;
import connectivity.http.LimitOrderBookApi;
import state.LimitOrderBook;

import static spark.Spark.path;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.delete;


public class LimitOrderBookHttpServerRunner implements ServerRunner {

    private static final String ORIGIN = "";
    private final LimitOrderBookApi limitOrderBookApi;

    @Inject
    public LimitOrderBookHttpServerRunner(LimitOrderBook limitOrderBook) {
        this.limitOrderBookApi = new LimitOrderBookApi(limitOrderBook);
    }

    @Override
    public void run() {
        path("book", () -> {
            get(ORIGIN, limitOrderBookApi.getLimitOrderBook);
            post(ORIGIN, limitOrderBookApi.placeOrder);
            put(ORIGIN, limitOrderBookApi.reduceOrder);
        });
        post("vwap", limitOrderBookApi.getVwap);
    }
}
