package connectivity.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dto.Cancellation;
import dto.Placement;
import dto.Side;
import spark.Route;
import state.LimitOrderBook;

import java.math.BigDecimal;
import java.util.Map;

public class LimitOrderBookApi {
    private static final LimitOrderBook LIMIT_ORDER_BOOK = LimitOrderBook.empty();
    private static final GsonBuilder gsonBuilder = new GsonBuilder();
    private static Gson gson;

    static {
        gsonBuilder.registerTypeAdapter(Placement.class, new PlacementDeserializer());
        gsonBuilder.registerTypeAdapter(Cancellation.class, new CancellationDeserializer());
        gson = gsonBuilder.create();
    }

    public static Route getLimitOrderBook = (request, response) -> gson.toJson(LIMIT_ORDER_BOOK);

    public static Route placeOrder = ((request, response) -> {
        Placement placement = gson.fromJson(request.body(), Placement.class);
        LIMIT_ORDER_BOOK.place(placement);
        return gson.toJson(placement);
    });

    public static Route reduceOrder = ((request, response) -> {
            Cancellation cancellation = gson.fromJson(request.body(), Cancellation.class);
            LIMIT_ORDER_BOOK.cancel(cancellation);
            return gson.toJson(cancellation);
    });

    public static Route getVwap = ((request, response) -> {
        JsonParser parser = new JsonParser();
        JsonObject element = parser.parse(request.body()).getAsJsonObject();
        String action = element.get("action").getAsString();
        Side side = Side.fromString(action);
        long size = element.get("size").getAsLong();
        BigDecimal price = side == Side.BID ? LIMIT_ORDER_BOOK.getAveragePurchasePrice(size) :
                LIMIT_ORDER_BOOK.getAverageSalePrice(size);
        return gson.toJson(Map.of("price", price));
    });
}
