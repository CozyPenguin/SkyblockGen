package cozypenguin.skyblockgen;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.world.GeneratorType;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.structure.StructureSet;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cozypenguin.skyblockgen.island.IslandStructurePlacement;
import cozypenguin.skyblockgen.mixin.GeneratorTypeAccessor;

public class SkyblockGen implements ModInitializer {
    public static final String MODID = "skyblockgen";

    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    public static final TagKey<Biome> IS_OVERWORLD = TagKey.of(Registry.BIOME_KEY, new Identifier(MODID, "is_overworld"));

    public static final StructurePieceType SKYBLOCK_ISLAND_PIECE = (StructurePieceType.ManagerAware) IslandGenerator::new;
    private static final StructureFeature<DefaultFeatureConfig> SKYBLOCK_FEATURE = new IslandFeature(DefaultFeatureConfig.CODEC);
    private static final ConfiguredStructureFeature<?, ?> SKYBLOCK_CONFIGURED = SKYBLOCK_FEATURE.configure(DefaultFeatureConfig.DEFAULT, IS_OVERWORLD);

    @Override
    public void onInitialize() {
        Registry.register(Registry.CHUNK_GENERATOR, new Identifier(MODID, "skyblock_generator"), SkyblockChunkGenerator.CODEC);

        GeneratorTypeAccessor.getValues().add(SKYBLOCK);

        Registry.register(Registry.STRUCTURE_PIECE, new Identifier(MODID, "skyblock_island_piece"), SKYBLOCK_ISLAND_PIECE);
        Registry.register(Registry.STRUCTURE_FEATURE, new Identifier(MODID, "skyblock_island"), SKYBLOCK_FEATURE);

        RegistryKey<ConfiguredStructureFeature<?, ?>> skyblockIslandConfigured = RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY,
                new Identifier(MODID, "skyblock_island"));
        var islandEntry = BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, skyblockIslandConfigured.getValue(), SKYBLOCK_CONFIGURED);

        var islandsKey = RegistryKey.of(Registry.STRUCTURE_SET_KEY, new Identifier(MODID, "skyblock_islands"));

        BuiltinRegistries.add(BuiltinRegistries.STRUCTURE_SET, islandsKey, new StructureSet(islandEntry, new IslandStructurePlacement()));
    }

    private static final GeneratorType SKYBLOCK = new GeneratorType("skyblock") {
        @Override
        protected ChunkGenerator getChunkGenerator(DynamicRegistryManager registryManager, long seed) {
            return new SkyblockChunkGenerator(registryManager, seed);
        }
    };
}
