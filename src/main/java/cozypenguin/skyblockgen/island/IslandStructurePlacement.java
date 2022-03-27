package cozypenguin.skyblockgen.island;

import com.mojang.serialization.Codec;

import cozypenguin.skyblockgen.SkyblockGen;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.placement.StructurePlacement;
import net.minecraft.world.gen.chunk.placement.StructurePlacementType;

public class IslandStructurePlacement implements StructurePlacement {
    public static final Codec<IslandStructurePlacement> CODEC = Codec.unit(IslandStructurePlacement::new);

    public static final StructurePlacementType<IslandStructurePlacement> ISLAND_STRUCTURE_PLACEMENT = Registry.register(Registry.STRUCTURE_PLACEMENT,
            new Identifier(SkyblockGen.MODID, "island_placement"), () -> CODEC);

    @Override
    public boolean isStartChunk(ChunkGenerator chunkGenerator, long l, int i, int j) {
        return i == 0 && j == 0;
    }

    @Override
    public StructurePlacementType<?> getType() {
        return ISLAND_STRUCTURE_PLACEMENT;
    }

}
