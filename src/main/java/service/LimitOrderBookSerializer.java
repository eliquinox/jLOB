package service;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import state.LimitOrderBook;

import java.lang.reflect.Type;

public class LimitOrderBookSerializer implements JsonSerializer<LimitOrderBook> {
    @Override
    public JsonElement serialize(LimitOrderBook src, Type typeOfSrc, JsonSerializationContext context) {
        return null;
    }
}
