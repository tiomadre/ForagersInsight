package com.tiomadre.foragersinsight.core.registry;

import com.mojang.serialization.Codec;
import com.tiomadre.foragersinsight.common.item.component.WaxedComponent;
import com.tiomadre.foragersinsight.common.recipe.FIDiffuserRecipe;
import com.tiomadre.foragersinsight.core.ForagersInsight;
import com.tiomadre.foragersinsight.core.other.toolevents.WaxedBoots;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class FIDataComponents {

    public static final DeferredRegister.DataComponents DATA_COMPONENTS =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, ForagersInsight.MOD_ID);



    public static final DeferredHolder<DataComponentType<?>, DataComponentType<WaxedComponent>> BOOTWAXED =
            DATA_COMPONENTS.registerComponentType("bootwaxed", builder ->
                    builder.persistent(WaxedComponent.CODEC)
                            .networkSynchronized(WaxedComponent.STREAM_CODEC)
            );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Holder<MobEffect>>> AUSPICIOUS_EFFECT =
            DATA_COMPONENTS.registerComponentType("auspicious_effect", builder ->
                    builder.persistent(MobEffect.CODEC)
                            .networkSynchronized(MobEffect.STREAM_CODEC)
            );




    private static <T>DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name,
                                                                                          UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return DATA_COMPONENTS.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }



    public static void register(IEventBus eventBus) {
        DATA_COMPONENTS.register(eventBus);
    }

}
