package com.tiomadre.foragersinsight.core.mixin;

import com.tiomadre.foragersinsight.common.worldgen.FIConfiguredFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TreeGrower.class)
public class SingleSaplingMixin {
    @Inject(method = "getConfiguredFeature", at = @At("HEAD"), cancellable = true)
    private void foragersInsight$growSingleDarkOakAsYoungTree(RandomSource random, boolean hasFlowers,
                                                              CallbackInfoReturnable<ResourceKey<ConfiguredFeature<?, ?>>> cir) {
        cir.setReturnValue(FIConfiguredFeatures.YOUNG_DARK_OAK_TREE_KEY);
    }
}