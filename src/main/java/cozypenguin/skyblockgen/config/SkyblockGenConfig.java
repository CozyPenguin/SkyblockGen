package cozypenguin.skyblockgen.config;

import java.util.HashSet;

import net.minecraft.util.math.BlockPos;

public class SkyblockGenConfig {
    public final BlockPos spawnpoint;
    public final HashSet<String> skyblockDimensions;

    public SkyblockGenConfig() {
        spawnpoint = BlockPos.ORIGIN.up(90);
        // dimensions
        skyblockDimensions = new HashSet<>();
        skyblockDimensions.add("minecraft:overworld");
        skyblockDimensions.add("minecraft:the_nether");
        skyblockDimensions.add("minecraft:the_end");
    }

    public SkyblockGenConfig(BlockPos spawnpoint, HashSet<String> skyblockDimensions) {
        this.spawnpoint = spawnpoint;
        this.skyblockDimensions = skyblockDimensions;
    }
}
