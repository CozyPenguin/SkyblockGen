package cozypenguin.skyblockgen.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.world.GeneratorType;

@Mixin(GeneratorType.class)
public interface GeneratorTypeAccessor {
    @Accessor("VALUES")
    public static List<GeneratorType> getValues() {
        throw new AssertionError();
    }
}
