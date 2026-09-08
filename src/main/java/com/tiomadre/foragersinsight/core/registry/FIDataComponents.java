package com.tiomadre.foragersinsight.core.registry;

import com.mojang.serialization.Codec;
import com.tiomadre.foragersinsight.core.ForagersInsight;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class FIDataComponents {

    public static final DeferredRegister.DataComponents DATA_COMPONENTS =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, ForagersInsight.MOD_ID);



    //public static final DeferredHolder<DataComponentType<?>, DataComponentType<>>


    //this prob needs to be rewritten oopsie
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> BOOTWAXED =
            DATA_COMPONENTS.registerComponentType("bootwaxed", builder ->
                    builder.persistent(Codec.BOOL)
                            .networkSynchronized(ByteBufCodecs.BOOL)
            );


    private static <T>DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name,
                                                                                          UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return DATA_COMPONENTS.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }



    public static void register(IEventBus eventBus) {
        DATA_COMPONENTS.register(eventBus);
    }

}
