package xyz.mtrdevelopment.pluto.serializer;

import com.google.gson.*;
import lombok.SneakyThrows;
import org.bukkit.inventory.ItemStack;
import xyz.mtrdevelopment.pluto.utilities.SerializationUtil;

import java.lang.reflect.Type;

/**
 * ItemStackSerializer is a part of Pluto
 * Which was created / maintained by
 *
 * @author MTR
 */

public class ItemStackSerializer implements JsonSerializer<ItemStack[]>, JsonDeserializer<ItemStack[]> {

    @SneakyThrows
    @Override
    public ItemStack[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return SerializationUtil.deserializeItems(json.getAsString());
    }

    @SneakyThrows
    @Override
    public JsonElement serialize(ItemStack[] src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(SerializationUtil.serializeItems(src));
    }
}
