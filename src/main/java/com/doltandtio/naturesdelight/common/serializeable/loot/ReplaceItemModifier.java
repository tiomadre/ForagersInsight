package com.doltandtio.naturesdelight.common.serializeable.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ReplaceItemModifier extends LootModifier {
    public static final Supplier<Codec<ReplaceItemModifier>> CODEC = Suppliers.memoize(() ->
            RecordCodecBuilder.create(inst -> codecStart(inst)
                    .and(ForgeRegistries.ITEMS.getCodec().fieldOf("new").forGetter(m -> m.newItem))
                    .and(ForgeRegistries.ITEMS.getCodec().fieldOf("replace").forGetter(m -> m.replaceItem))
                    .apply(inst, ReplaceItemModifier::new)));
    protected ReplaceItemModifier(LootItemCondition[] conditionsIn, Item item, Item replaceItem) {
        super(conditionsIn);
        this.newItem = item;
        this.replaceItem = replaceItem;
    }

    private final Item newItem;
    private final Item replaceItem;

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> loot, LootContext lootContext) {
        return ObjectArrayList.wrap(loot.stream()
                .map(stack -> stack.is(this.replaceItem) ? new ItemStack(newItem, stack.getCount()) : stack)
                .toArray(ItemStack[]::new));
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
