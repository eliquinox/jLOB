package service;

import com.google.gson.Gson;
import state.LimitOrderBook;

import static spark.Spark.*;


public class LimitOrderBookService {

    private static final LimitOrderBook  LIMIT_ORDER_BOOK = LimitOrderBook.empty();

    public static void main(String[] args) {

        post("place-order", ((request, response) -> {
            PlaceOrderRequest orderRequest = new Gson().fromJson(request.body(), PlaceOrderRequest.class);
            System.out.println(orderRequest.getPrice());
            return "";
        }));

    }
}
