package cozypenguin.skyblockgen.island;

import java.util.function.Predicate;

import com.mojang.serialization.Codec;

import cozypenguin.skyblockgen.SkyblockChunkGenerator;
import cozypenguin.skyblockgen.SkyblockGen;
import net.minecraft.structure.StructureGeneratorFactory;
import net.minecraft.structure.StructurePiecesCollector;
import net.minecraft.structure.StructurePiecesGenerator;
import net.minecraft.structure.StructureGeneratorFactory.Context;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.GenerationStep.Feature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class IslandFeature extends StructureFeature<DefaultFeatureConfig> {
    private static final Identifier SKYBLOCK_ISLAND = new Identifier(SkyblockGen.MODID, "skyblock_island");

    public IslandFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec,
                StructureGeneratorFactory.simple((Predicate<Context<DefaultFeatureConfig>>) c -> c.chunkGenerator() instanceof SkyblockChunkGenerator, IslandFeature::addPieces));
    }

    private static void addPieces(StructurePiecesCollector collector, StructurePiecesGenerator.Context<DefaultFeatureConfig> context) {
        var pos = new BlockPos(context.chunkPos().getStartX(), 64, context.chunkPos().getStartZ());

        collector.addPiece(new IslandGenerator(context.structureManager(), SKYBLOCK_ISLAND, pos));

    }

    @Override
    public Feature getGenerationStep() {
        return Feature.SURFACE_STRUCTURES;
    }
}
