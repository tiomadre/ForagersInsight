package com.doltandtio.foragersinsight.core.registry;

import com.doltandtio.foragersinsight.core.ForagersInsight;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FISounds {
    public static final DeferredRegister<SoundEvent> SOUNDS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ForagersInsight.MOD_ID);

    //Handbasket
    public static final RegistryObject<SoundEvent> HANDBASKET_PICK_UP = SOUNDS.register("handbasket.handbasket.pickup",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ForagersInsight.MOD_ID, "handbasket.handbasket.pickup")));
    //Mallet
}
