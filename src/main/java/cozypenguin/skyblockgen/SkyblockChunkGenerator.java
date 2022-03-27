package cozypenguin.skyblockgen;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.block.BlockState;
import net.minecraft.structure.StructureSet;
import net.minecraft.util.dynamic.RegistryOps;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.Heightmap.Type;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.MultiNoiseBiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import net.minecraft.world.gen.chunk.VerticalBlockSample;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.GenerationStep.Carver;
import net.minecraft.world.gen.chunk.Blender;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;

public class SkyblockChunkGenerator extends NoiseChunkGenerator {
    public static final Codec<SkyblockChunkGenerator> CODEC = RecordCodecBuilder
            .create(instance -> method_41042(instance).and(instance.group(RegistryOps.createRegistryCodec(Registry.NOISE_WORLDGEN).forGetter(scg -> scg.noiseRegistry),
                    BiomeSource.CODEC.fieldOf("biome_source").forGetter(SkyblockChunkGenerator::getBiomeSource), Codec.LONG.fieldOf("seed").stable().forGetter(scg -> scg.seed),
                    ChunkGeneratorSettings.REGISTRY_CODEC.fieldOf("settings").forGetter(scg -> scg.settings))

            ).apply(instance, instance.stable(SkyblockChunkGenerator::new)));

    @Override
    protected Codec<? extends ChunkGenerator> getCodec() {
        return CODEC;
    }

    public SkyblockChunkGenerator(DynamicRegistryManager registryManager, long seed) {
        this(registryManager.get(Registry.STRUCTURE_SET_KEY), registryManager.get(Registry.NOISE_WORLDGEN),
                MultiNoiseBiomeSource.Preset.OVERWORLD.getBiomeSource(registryManager.get(Registry.BIOME_KEY), true), seed,
                registryManager.get(Registry.CHUNK_GENERATOR_SETTINGS_KEY).getOrCreateEntry(ChunkGeneratorSettings.OVERWORLD));
    }

    private SkyblockChunkGenerator(Registry<StructureSet> structuresRegistry, Registry<DoublePerlinNoiseSampler.NoiseParameters> noiseRegistry, BiomeSource biomeSource, long seed,
            RegistryEntry<ChunkGeneratorSettings> settings) {
        super(structuresRegistry, noiseRegistry, biomeSource, seed, settings);
    }

    @Override
    public void populateEntities(ChunkRegion region) {
    }

    @Override
    public CompletableFuture<Chunk> populateNoise(Executor executor, Blender blender, StructureAccessor structureAccessor, Chunk chunk) {
        return CompletableFuture.completedFuture(chunk);
    }

    @Override
    public void buildSurface(ChunkRegion region, StructureAccessor structures, Chunk chunk) {
    }

    @Override
    public void carve(ChunkRegion chunkRegion, long seed, BiomeAccess biomeAccess, StructureAccessor structureAccessor, Chunk chunk, Carver generationStep) {
    }

    @Override
    public int getSpawnHeight(HeightLimitView world) {
        return 64;
    }

    @Override
    public VerticalBlockSample getColumnSample(int x, int z, HeightLimitView world) {
        return new VerticalBlockSample(world.getBottomY(), new BlockState[0]);
    }

    @Override
    public int getHeight(int x, int z, Type heightmap, HeightLimitView world) {
        return world.getBottomY();
    }

    @Override
    public ChunkGenerator withSeed(long seed) {
        return new NoiseChunkGenerator(this.field_37053, this.noiseRegistry, this.populationSource.withSeed(seed), seed, this.settings);
    }
}