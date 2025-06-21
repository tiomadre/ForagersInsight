package com.doltandtio.foragersinsight.core.mixin;

import com.doltandtio.foragersinsight.common.worldgen.FIConfiguredFeatures;
import com.doltandtio.foragersinsight.core.registry.FIConfig;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.SpruceTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpruceTreeGrower.class)
public class SpruceTreeGrowerMixin {
    @Inject(method = "getConfiguredMegaFeature", at = @At("HEAD"), cancellable = true)
    private void ForagersInsight$SmallChanceForSpruceSaplingsToGrowAnSpruceTipTree(RandomSource rand, CallbackInfoReturnable<ResourceKey<ConfiguredFeature<?, ?>>> cir) {
        if (FIConfig.shouldGrowBountifulTree(rand)) {
            cir.setReturnValue(FIConfiguredFeatures.SPRUCE_TIP_TREE_KEY);
        }
    }
}
