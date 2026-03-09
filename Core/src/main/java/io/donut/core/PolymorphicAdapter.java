package io.donut.core;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * TODO: The delegateGson is a plain new Gson(), which means nested polymorphic fields
 *  (e.g. a BaseComponent inside a BaseEntity) will NOT use this adapter during
 *  serialization/deserialization. This causes the concrete type info to be lost for
 *  nested objects. To fix this, consider switching to a TypeAdapterFactory approach,
 *  which allows delegating back to the parent Gson instance without infinite recursion.
 */
public class PolymorphicAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {
    private final Gson delegateGson = new Gson();

    @Override
    public JsonElement serialize(T src, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.addProperty("type", src.getClass().getName());

        // Serialize the object using its concrete class (not the declared type) to capture all subclass-specific fields
        obj.add("data", delegateGson.toJsonTree(src, src.getClass()));
        return obj;
    }

    @Override
    public T deserialize(JsonElement json, Type type, JsonDeserializationContext ctx) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        String className = obj.get("type").getAsString();
        try {
            Class<?> loadedClass = Class.forName(className);
            // Deserialize the "data" field into the resolved concrete type, restoring the original object
            return (T) delegateGson.fromJson(obj.get("data"), loadedClass);
        } catch (ClassNotFoundException e) {
            //throw new JsonParseException("Unknown type: " + className, e);
            System.err.println("Mod removed - missing class: " + className + ". Skipping entity/component.");
            return null;
        }
    }

}
