package com.tiomadre.foragersinsight.core.registry;

import com.mojang.serialization.Codec;
import com.tiomadre.foragersinsight.core.ForagersInsight;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FIDataComponents {

    public static final DeferredRegister.DataComponents DATA_COMPONENTS =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, ForagersInsight.MOD_ID);

    public static final Supplier<DataComponentType<Boolean>> BOOTWAXED =
            DATA_COMPONENTS.registerComponentType("bootwaxed", builder ->
                    builder.persistent(Codec.BOOL)
                            .networkSynchronized(ByteBufCodecs.BOOL)
            );


}
