package cozypenguin.skyblockgen;

import cozypenguin.skyblockgen.mixin.GeneratorTypeAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.world.GeneratorType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.gen.chunk.ChunkGenerator;

@Environment(EnvType.CLIENT)
public class GeneratorTypes {
    public static final GeneratorType SKYBLOCK = new GeneratorType("skyblock") {
        @Override
        protected ChunkGenerator getChunkGenerator(DynamicRegistryManager registryManager, long seed) {
            return new SkyblockChunkGenerator(registryManager, seed, new Identifier("overworld"));
        }

        @Override
        public GeneratorOptions createDefaultOptions(DynamicRegistryManager registryManager, long seed, boolean generateStructures, boolean bonusChest) {
            var dimensionTypes = registryManager.get(Registry.DIMENSION_TYPE_KEY);
            return new GeneratorOptions(seed, generateStructures, bonusChest,
                    GeneratorOptionsHelper.getGeneratorOptionsRegistry(DimensionType.createDefaultDimensionOptions(registryManager, seed), dimensionTypes, registryManager, seed));
        }
    };

    public static void register() {
        GeneratorTypeAccessor.getValues().add(GeneratorTypes.SKYBLOCK);
    }
}
