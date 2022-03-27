package cozypenguin.skyblockgen;

import java.util.Random;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.SimpleStructurePiece;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;

public class IslandGenerator extends SimpleStructurePiece {

    public IslandGenerator(StructureManager manager, NbtCompound compound) {
        super(SkyblockGen.SKYBLOCK_ISLAND_PIECE, compound, manager, identifier -> new StructurePlacementData());
    }

    public IslandGenerator(StructureManager manager, Identifier identifier, BlockPos pos) {
        super(SkyblockGen.SKYBLOCK_ISLAND_PIECE, 0, manager, identifier, identifier.toString(), new StructurePlacementData(), pos);
    }

    @Override
    protected void handleMetadata(String metadata, BlockPos pos, ServerWorldAccess world, Random random, BlockBox boundingBox) {

    }
}