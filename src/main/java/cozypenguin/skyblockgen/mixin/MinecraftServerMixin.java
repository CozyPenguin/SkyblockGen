package cozypenguin.skyblockgen.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import cozypenguin.skyblockgen.SkyblockChunkGenerator;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.level.ServerWorldProperties;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

    @Inject(method = "setupSpawn", at = @At("HEAD"), cancellable = true)
    private static void setupSpawn(ServerWorld world, ServerWorldProperties worldProperties, boolean bonusChest, boolean debugWorld, CallbackInfo ci) {
        if (world.getChunkManager().getChunkGenerator() instanceof SkyblockChunkGenerator) {
            worldProperties.setSpawnPos(BlockPos.ORIGIN.up(90), 0);
            ci.cancel();
        }
    }
}
