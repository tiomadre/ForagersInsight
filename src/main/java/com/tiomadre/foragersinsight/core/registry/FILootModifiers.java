package com.tiomadre.foragersinsight.core.registry;

import com.mojang.serialization.MapCodec;
import com.tiomadre.foragersinsight.common.serializeable.loot.ReplaceItemModifier;
import com.tiomadre.foragersinsight.core.ForagersInsight;
import com.mojang.serialization.Codec;

import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;


public class FILootModifiers {
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIERS =
            DeferredRegister.create(NeoForgeRegistries.GLOBAL_LOOT_MODIFIER_SERIALIZERS, ForagersInsight.MOD_ID);

    public static final Supplier<MapCodec<? extends IGlobalLootModifier>> REPLACE_ITEM =
            LOOT_MODIFIERS.register("replace_item", ReplaceItemModifier.CODEC);
}
