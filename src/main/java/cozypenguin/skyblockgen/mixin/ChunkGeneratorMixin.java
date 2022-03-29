package cozypenguin.skyblockgen.mixin;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mojang.datafixers.util.Pair;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import cozypenguin.skyblockgen.island.IslandStructurePlacement;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryEntryList;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.placement.StructurePlacement;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;

@Mixin(ChunkGenerator.class)
public class ChunkGeneratorMixin {

    @Inject(method = "locateStructure", at = @At(value = "INVOKE", target = "java/util/Map.entrySet()Ljava/util/Set;", shift = Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    @SuppressWarnings("rawtypes")
    private void locateIslandStructure(ServerWorld serverWorld, RegistryEntryList<ConfiguredStructureFeature<?, ?>> registryEntryList, BlockPos center, int radius,
            boolean skipExistingChunk, CallbackInfoReturnable<Pair<BlockPos, RegistryEntry<ConfiguredStructureFeature<?, ?>>>> ci, Set set, Set set2, double d,
            Map<StructurePlacement, Set<RegistryEntry<ConfiguredStructureFeature<?, ?>>>> map, List list) {
        for (Map.Entry<StructurePlacement, Set<RegistryEntry<ConfiguredStructureFeature<?, ?>>>> entry : map.entrySet()) {
            if (entry.getKey() instanceof IslandStructurePlacement) {
                ci.setReturnValue(Pair.of(new BlockPos(0, 90, 0),
                        (RegistryEntry<ConfiguredStructureFeature<?, ?>>) ((Set<RegistryEntry<ConfiguredStructureFeature<?, ?>>>) entry.getValue()).iterator().next()));
                ci.cancel();
            }
        }
    }
}
