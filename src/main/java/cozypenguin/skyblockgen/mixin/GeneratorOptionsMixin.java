package cozypenguin.skyblockgen.mixin;

import java.util.HashSet;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import cozypenguin.skyblockgen.GeneratorOptionsHelper;
import net.minecraft.server.dedicated.ServerPropertiesHandler;
import net.minecraft.structure.StructureSet;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.GeneratorOptions;

@Mixin(GeneratorOptions.class)
public class GeneratorOptionsMixin {

    @Inject(method = "fromProperties", at = @At(value = "INVOKE", target = "java/lang/String.hashCode()I"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private static void injectSkyblockGenerator(DynamicRegistryManager registryManager, ServerPropertiesHandler.WorldGenProperties worldGenProperties,
            CallbackInfoReturnable<GeneratorOptions> cir, long seed, Registry<DimensionType> dimensionType, Registry<Biome> biome, Registry<StructureSet> structureSet,
            Registry<DimensionOptions> dimensionOptions, String levelType) {
        if (levelType.equals("skyblockgen:skyblock")) {
            HashSet<String> toReplace = new HashSet<>();
            toReplace.add("minecraft:overworld");
            toReplace.add("minecraft:the_nether");
            var options = GeneratorOptionsHelper.getGeneratorOptionsRegistry(dimensionOptions, dimensionType, registryManager, seed, toReplace);

            cir.setReturnValue(new GeneratorOptions(seed, worldGenProperties.generateStructures(), false, options));
            cir.cancel();
        }
    }
}
