package com.doltandtio.naturesdelight.core.mixin;

import com.doltandtio.naturesdelight.common.worldgen.NDConfiguredFeatures;
import com.doltandtio.naturesdelight.core.registry.NDConfig;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.DarkOakTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DarkOakTreeGrower.class)
public class DarkOakTreeGrowerMixin {
    @Inject(method = "getConfiguredMegaFeature", at = @At("HEAD"), cancellable = true)
    private void DoltModHow$SmallChanceForDarkOakSaplingsToGrowAnAcornTree(RandomSource rand, CallbackInfoReturnable<ResourceKey<ConfiguredFeature<?, ?>>> cir) {
        if (NDConfig.shouldGrowBountifulTree(rand)) {
            cir.setReturnValue(NDConfiguredFeatures.ACORN_TREE_KEY);
        }
    }
}
