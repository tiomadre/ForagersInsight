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

public class KnifeFlowersModifier extends LootModifier {
    public static final Supplier<Codec<KnifeFlowersModifier>> CODEC = Suppliers.memoize(() ->
            RecordCodecBuilder.create(inst -> codecStart(inst)
                    .and(ForgeRegistries.ITEMS.getCodec().fieldOf("new").forGetter(m -> m.newItem))
                    .and(ForgeRegistries.ITEMS.getCodec().fieldOf("replace").forGetter(m -> m.replaceItem))
                    .apply(inst, KnifeFlowersModifier::new)));
    protected KnifeFlowersModifier(LootItemCondition[] conditionsIn, Item item, Item replaceItem) {
        super(conditionsIn);
        this.newItem = item;
        this.replaceItem = replaceItem;
    }

    private final Item newItem;
    private final Item replaceItem;

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> loot, LootContext lootContext) {
        Iterator<ItemStack> lootCopy = loot.iterator();
        while (lootCopy.hasNext()) {
            ItemStack stack = lootCopy.next();
            if (stack.is(this.replaceItem)) {
                loot.remove(stack);
                loot.add(new ItemStack(newItem));
            }
        }

        return loot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
