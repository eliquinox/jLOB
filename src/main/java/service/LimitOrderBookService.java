package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import delta.Cancellation;
import delta.Placement;
import state.LimitOrderBook;

import static spark.Spark.*;


public class LimitOrderBookService {

    private static final LimitOrderBook LIMIT_ORDER_BOOK = LimitOrderBook.empty();
    private static final GsonBuilder gsonBuilder = new GsonBuilder();

    public static void main(String[] args) {
        registerTypeAdapters();
        registerEndpoints();
    }

    private static void registerTypeAdapters() {
        gsonBuilder.registerTypeAdapter(Placement.class, new PlacementDeserializer());
        gsonBuilder.registerTypeAdapter(Cancellation.class, new CancellationDeserializer());
    }

    private static void registerEndpoints() {
        Gson gson = gsonBuilder.create();

        get("book", ((request, response) -> LIMIT_ORDER_BOOK), gson::toJson);

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
    }
}
