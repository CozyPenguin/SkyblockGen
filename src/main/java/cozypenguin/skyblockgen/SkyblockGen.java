package cozypenguin.skyblockgen;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.structure.StructureSet;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cozypenguin.skyblockgen.config.ConfigAdapter;
import cozypenguin.skyblockgen.config.SkyblockGenConfig;
import cozypenguin.skyblockgen.island.IslandFeature;
import cozypenguin.skyblockgen.island.IslandGenerator;
import cozypenguin.skyblockgen.island.IslandStructurePlacement;

public class SkyblockGen implements ModInitializer, ClientModInitializer {
    public static final String MODID = "skyblockgen";

    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    public static final TagKey<Biome> EMPTY = TagKey.of(Registry.BIOME_KEY, new Identifier(MODID, "empty"));

    public static final StructurePieceType SKYBLOCK_ISLAND_PIECE = (StructurePieceType.ManagerAware) IslandGenerator::new;
    private static final StructureFeature<DefaultFeatureConfig> SKYBLOCK_FEATURE = new IslandFeature(DefaultFeatureConfig.CODEC);
    private static final ConfiguredStructureFeature<?, ?> SKYBLOCK_CONFIGURED = SKYBLOCK_FEATURE.configure(DefaultFeatureConfig.DEFAULT, EMPTY);

    public static final Biome SKYBLOCK_BIOME = Biome.Builder.copy(BuiltinRegistries.BIOME.entryOf(BiomeKeys.THE_VOID).value())
            .generationSettings(new GenerationSettings.Builder().build()).build();

    public static final SkyblockGenConfig CONFIG;

    static {
        var path = FabricLoader.getInstance().getConfigDir().resolve("skyblockgen.json");
        Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(SkyblockGenConfig.class, new ConfigAdapter()).create();
        SkyblockGenConfig cfg = null;

        if (Files.exists(path)) {
            try (var reader = new FileReader(path.toFile())) {
                cfg = gson.fromJson(reader, SkyblockGenConfig.class);
            } catch (IOException exception) {
                LOGGER.error("error while reading config");
                cfg = new SkyblockGenConfig();
            }
        } else {
            cfg = new SkyblockGenConfig();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()))) {
                var string = gson.toJson(cfg);
                writer.write(string);
            } catch (IOException exception) {
                LOGGER.error("error while writing new config to disk");
            }
        }

        CONFIG = cfg;
    }

    @Override
    public void onInitialize() {
        Registry.register(Registry.CHUNK_GENERATOR, new Identifier(MODID, "skyblock"), SkyblockChunkGenerator.CODEC);

        Registry.register(Registry.STRUCTURE_PIECE, new Identifier(MODID, "skyblock_island_piece"), SKYBLOCK_ISLAND_PIECE);
        Registry.register(Registry.STRUCTURE_FEATURE, new Identifier(MODID, "skyblock_island"), SKYBLOCK_FEATURE);

        RegistryKey<ConfiguredStructureFeature<?, ?>> skyblockIslandConfigured = RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY,
                new Identifier(MODID, "skyblock_island"));
        var islandEntry = BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, skyblockIslandConfigured.getValue(), SKYBLOCK_CONFIGURED);

        var islandsKey = RegistryKey.of(Registry.STRUCTURE_SET_KEY, new Identifier(MODID, "skyblock_islands"));

        BuiltinRegistries.add(BuiltinRegistries.STRUCTURE_SET, islandsKey, new StructureSet(islandEntry, new IslandStructurePlacement()));

        BuiltinRegistries.add(BuiltinRegistries.BIOME, new Identifier(SkyblockGen.MODID, "skyblock_biome"), SKYBLOCK_BIOME);
    }

    @Override
    public void onInitializeClient() {
        GeneratorTypes.register();
    }

}
