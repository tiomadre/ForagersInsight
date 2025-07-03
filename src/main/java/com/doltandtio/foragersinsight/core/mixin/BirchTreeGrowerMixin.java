package com.doltandtio.foragersinsight.core.mixin;

import com.doltandtio.foragersinsight.common.worldgen.FIConfiguredFeatures;
import com.doltandtio.foragersinsight.core.registry.FIConfig;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.BirchTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BirchTreeGrower.class)
public class BirchTreeGrowerMixin {
    @Inject(method = "getConfiguredFeature(Lnet/minecraft/util/RandomSource;Z)Lnet/minecraft/resources/ResourceKey;", at = @At("HEAD"), cancellable = true)
    private void ForagersInsight$smallChanceForSappyBirch(RandomSource rand, boolean giant, CallbackInfoReturnable<ResourceKey<ConfiguredFeature<?, ?>>> cir) {
        if (FIConfig.shouldGrowSappyBirch(rand)) {
            cir.setReturnValue(FIConfiguredFeatures.SAPPY_BIRCH_TREE_KEY);
        }
    }
}