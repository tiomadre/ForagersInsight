package com.doltandtio.foragersinsight.core.registry;

import com.doltandtio.foragersinsight.common.serializeable.loot.ReplaceItemModifier;
import com.doltandtio.foragersinsight.core.ForagersInsight;
import com.mojang.serialization.Codec;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FILootModifiers {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS =
            DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, ForagersInsight.MOD_ID);

    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> REPLACE_ITEM =
            LOOT_MODIFIERS.register("replace_item", ReplaceItemModifier.CODEC);
}
