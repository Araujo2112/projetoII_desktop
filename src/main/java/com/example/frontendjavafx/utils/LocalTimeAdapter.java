package com.example.frontendjavafx.utils;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalTime;

public class LocalTimeAdapter implements JsonSerializer<LocalTime>, JsonDeserializer<LocalTime> {

    @Override
    public JsonElement serialize(LocalTime localTime, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(localTime.toString());
    }

    @Override
    public LocalTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return LocalTime.parse(json.getAsString());
    }
}
