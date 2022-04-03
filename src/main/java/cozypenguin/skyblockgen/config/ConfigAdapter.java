package cozypenguin.skyblockgen.config;

import java.lang.reflect.Type;
import java.util.HashSet;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import net.minecraft.util.math.BlockPos;

public class ConfigAdapter implements JsonSerializer<SkyblockGenConfig>, JsonDeserializer<SkyblockGenConfig> {

    @Override
    public JsonElement serialize(SkyblockGenConfig config, Type type, JsonSerializationContext jsc) {
        var json = new JsonObject();

        // spawnpoint
        var pos = new JsonArray();
        pos.add(config.spawnpoint.getX());
        pos.add(config.spawnpoint.getY());
        pos.add(config.spawnpoint.getZ());

        json.add("spawnpoint", pos);

        var dimensions = new JsonArray();
        for (String string : config.skyblockDimensions) {
            dimensions.add(string);
        }
        json.add("dimensions", dimensions);

        return json;
    }

    @Override
    public SkyblockGenConfig deserialize(JsonElement json, Type type, JsonDeserializationContext jsc) throws JsonParseException {
        var spawnpoint = json.getAsJsonObject().get("spawnpoint").getAsJsonArray();
        var skyblockDimensions = new HashSet<String>();
        for (JsonElement element : json.getAsJsonObject().get("dimensions").getAsJsonArray()) {
            skyblockDimensions.add(element.getAsString());
        }

        return new SkyblockGenConfig(new BlockPos(spawnpoint.get(0).getAsDouble(), spawnpoint.get(1).getAsDouble(), spawnpoint.get(2).getAsDouble()), skyblockDimensions);
    }

}
