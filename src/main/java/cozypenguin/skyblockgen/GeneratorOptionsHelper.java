package cozypenguin.skyblockgen;

import com.mojang.serialization.Lifecycle;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;

public class GeneratorOptionsHelper {
    public static Registry<DimensionOptions> getGeneratorOptionsRegistry(Registry<DimensionOptions> dimensionOptions, Registry<DimensionType> dimensionTypes,
            DynamicRegistryManager registryManager, long seed) {
        MutableRegistry<DimensionOptions> replaced = new SimpleRegistry<>(Registry.DIMENSION_KEY, Lifecycle.experimental(), null);

        for (var entry : dimensionOptions.getEntrySet()) {
            var dimensionKey = entry.getKey();
            var dimensionIdf = dimensionKey.getValue();
            if (SkyblockGen.CONFIG.skyblockDimensions.contains(dimensionIdf.toString())) {
                var entryOptions = dimensionOptions.get(dimensionKey);
                var dimensionEntry = entryOptions == null ? dimensionTypes.getOrCreateEntry(RegistryKey.of(Registry.DIMENSION_TYPE_KEY, dimensionIdf))
                        : entryOptions.getDimensionTypeSupplier();

                ChunkGenerator generator;

                if (entryOptions == null) {
                    generator = new SkyblockChunkGenerator(registryManager, seed, dimensionIdf);
                } else {
                    var oldGenerator = entryOptions.getChunkGenerator();
                    if (oldGenerator instanceof NoiseChunkGenerator) {
                        generator = SkyblockChunkGenerator.fromNoiseChunkGenerator((NoiseChunkGenerator) oldGenerator, dimensionIdf);
                    } else {
                        generator = oldGenerator;
                    }
                }

                replaced.add(dimensionKey, new DimensionOptions(dimensionEntry, generator), Lifecycle.stable());
            } else {
                replaced.add(dimensionKey, entry.getValue(), dimensionOptions.getEntryLifecycle(entry.getValue()));
            }
        }

        if (!replaced.containsId(new Identifier("overworld"))) {
            var entryOptions = dimensionOptions.get(DimensionOptions.OVERWORLD);
            var overworldEntry = entryOptions == null ? dimensionTypes.getOrCreateEntry(DimensionType.OVERWORLD_REGISTRY_KEY) : entryOptions.getDimensionTypeSupplier();
            if (SkyblockGen.CONFIG.skyblockDimensions.contains("minecraft:overworld")) {
                replaced.add(DimensionOptions.OVERWORLD, new DimensionOptions(overworldEntry, new SkyblockChunkGenerator(registryManager, seed, new Identifier("overworld"))),
                        Lifecycle.stable());
            } else {
                replaced.add(DimensionOptions.OVERWORLD, new DimensionOptions(overworldEntry, GeneratorOptions.createOverworldGenerator(registryManager, seed)),
                        Lifecycle.stable());
            }
        }

        return replaced;
    }
}
