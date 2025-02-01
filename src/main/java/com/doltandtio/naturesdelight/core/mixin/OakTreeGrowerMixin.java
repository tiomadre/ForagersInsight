package com.doltandtio.naturesdelight.core.mixin;

import com.doltandtio.naturesdelight.common.worldgen.NDConfiguredFeatures;
import com.doltandtio.naturesdelight.core.NaturesDelight;
import com.doltandtio.naturesdelight.core.registry.NDConfig;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.OakTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(OakTreeGrower.class)
public class OakTreeGrowerMixin {
    @Inject(method = "getConfiguredFeature(Lnet/minecraft/util/RandomSource;Z)Lnet/minecraft/resources/ResourceKey;", at = @At("HEAD"), cancellable = true)
    private void NaturesDelight$OakSapingRandomlyHaveAppleTree(RandomSource rand, boolean p_256536_, CallbackInfoReturnable<ResourceKey<ConfiguredFeature<?, ?>>> cir) {
        if (NDConfig.shouldGrowBountifulTree(rand)) {
            cir.setReturnValue(NDConfiguredFeatures.APPLE_TREE_KEY);
        }
    }
}
