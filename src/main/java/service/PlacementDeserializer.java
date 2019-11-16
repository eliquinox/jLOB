package service;

import com.google.gson.*;
import dto.Placement;

import java.lang.reflect.Type;

public class PlacementDeserializer implements JsonDeserializer<Placement> {

    @Override
    public Placement deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        return new Placement(
                jsonObject.get("side").getAsString(),
                jsonObject.get("price").getAsLong(),
                jsonObject.get("size").getAsLong()
        );
    }
}
