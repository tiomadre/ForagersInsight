package com.doltandtio.naturesdelight.core.registry;

import com.doltandtio.naturesdelight.core.NaturesDelight;
import com.teamabnormals.blueprint.common.effect.BlueprintMobEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class NDMobEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, NaturesDelight.MOD_ID);

    public static final RegistryObject<MobEffect> CHILLED = MOB_EFFECTS.register("chilled",
            () -> new BlueprintMobEffect(MobEffectCategory.BENEFICIAL, 0xc2ecff));
    public static final RegistryObject<MobEffect> MEDICINAL = MOB_EFFECTS.register("medicinal",
            () -> new BlueprintMobEffect(MobEffectCategory.BENEFICIAL, 0x0a5f38));
}
