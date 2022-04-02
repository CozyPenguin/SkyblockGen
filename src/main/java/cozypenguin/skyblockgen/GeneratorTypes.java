package cozypenguin.skyblockgen;

import cozypenguin.skyblockgen.mixin.GeneratorTypeAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.world.GeneratorType;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.gen.chunk.ChunkGenerator;

@Environment(EnvType.CLIENT)
public class GeneratorTypes {
    public static final GeneratorType SKYBLOCK = new GeneratorType("skyblock") {
        @Override
        protected ChunkGenerator getChunkGenerator(DynamicRegistryManager registryManager, long seed) {
            return new SkyblockChunkGenerator(registryManager, seed);
        }
    };

    public static void register() {
        GeneratorTypeAccessor.getValues().add(GeneratorTypes.SKYBLOCK);
    }
}
