package service;

import com.google.gson.*;
import delta.Cancellation;

import java.lang.reflect.Type;

public class CancellationDeserializer implements JsonDeserializer<Cancellation> {

    @Override
    public Cancellation deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        return new Cancellation(
                jsonObject.get("id").getAsLong(),
                jsonObject.get("size").getAsLong()
        );
    }
}
