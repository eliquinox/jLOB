import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import connectivity.http.deserializers.CancellationDeserializer;
import connectivity.http.deserializers.PlacementDeserializer;
import dto.Cancellation;
import dto.Placement;
import dto.Side;
import spark.Filter;
import state.LimitOrderBook;

import java.math.BigDecimal;
import java.util.Map;

import static spark.Spark.*;


public class LimitOrderBookHttpApplication {

    private static final LimitOrderBook LIMIT_ORDER_BOOK = LimitOrderBook.empty();
    private static final GsonBuilder gsonBuilder = new GsonBuilder();
    private static Gson gson;

    public static void main(String[] args) {
        registerTypeAdapters();
        registerEndpoints();
    }

    private static void registerTypeAdapters() {
        gsonBuilder.registerTypeAdapter(Placement.class, new PlacementDeserializer());
        gsonBuilder.registerTypeAdapter(Cancellation.class, new CancellationDeserializer());
        gson = gsonBuilder.create();
    }

    private static void registerEndpoints() {
        get("book", ((request, response) -> gson.toJson(LIMIT_ORDER_BOOK)));

        post("book", ((request, response) -> {
            Placement placement = gson.fromJson(request.body(), Placement.class);
            LIMIT_ORDER_BOOK.place(placement);
            return gson.toJson(placement);
        }));

        delete("book", ((request, response) -> {
            Cancellation cancellation = gson.fromJson(request.body(), Cancellation.class);
            LIMIT_ORDER_BOOK.cancel(cancellation);
            return gson.toJson(cancellation);
        }));

        post("vwap", (request, response) -> {
            JsonParser parser = new JsonParser();
            JsonObject element = parser.parse(request.body()).getAsJsonObject();
            String action = element.get("action").getAsString();
            Side side = Side.fromString(action);
            long size = element.get("size").getAsLong();
            BigDecimal price = side == Side.BID ? LIMIT_ORDER_BOOK.getAveragePurchasePrice(size) :
                    LIMIT_ORDER_BOOK.getAverageSalePrice(size);
            return gson.toJson(Map.of("price", price));
        });

        after((Filter) (request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET");
        });

    }

}
