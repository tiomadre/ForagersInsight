package com.doltandtio.foragersinsight.core.registry;

import com.doltandtio.foragersinsight.common.effect.ChilledEffect;
import com.doltandtio.foragersinsight.common.effect.MedicinalEffect;
import com.doltandtio.foragersinsight.common.effect.VigorEffect;
import com.doltandtio.foragersinsight.core.ForagersInsight;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FIMobEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, ForagersInsight.MOD_ID);

    public static final RegistryObject<MobEffect> CHILLED = MOB_EFFECTS.register("chilled", ChilledEffect::new);
    public static final RegistryObject<MobEffect> MEDICINAL = MOB_EFFECTS.register("medicinal", MedicinalEffect::new);
    public static final RegistryObject<MobEffect> VIGOR = MOB_EFFECTS.register("vigor", VigorEffect::new);
}
