package com.doltandtio.foragersinsight.common.serializeable.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ReplaceItemModifier extends LootModifier {
    public static final Supplier<Codec<ReplaceItemModifier>> CODEC = Suppliers.memoize(() ->
            RecordCodecBuilder.create(inst -> codecStart(inst)
                    .and(ForgeRegistries.ITEMS.getCodec().fieldOf("new").forGetter(m -> m.newItem))
                    .and(ForgeRegistries.ITEMS.getCodec().fieldOf("replace").forGetter(m -> m.replaceItem))
                    .and(Codec.FLOAT.optionalFieldOf("chance", 1.0F).forGetter(m -> m.chance))
                    .and(Codec.BOOL.optionalFieldOf("destroy_on_fail", false).forGetter(m -> m.destroyOnFail))
                    .apply(inst, ReplaceItemModifier::new)));

    private final Item newItem;
    private final Item replaceItem;
    private final float chance;
    private final boolean destroyOnFail;

    protected ReplaceItemModifier(LootItemCondition[] conditionsIn, Item item, Item replaceItem, float chance, boolean destroyOnFail) {
        super(conditionsIn);
        this.newItem = item;
        this.replaceItem = replaceItem;
        this.chance = Mth.clamp(chance, 0.0F, 1.0F);
        this.destroyOnFail = destroyOnFail;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> loot, LootContext lootContext) {
        ObjectArrayList<ItemStack> modifiedLoot = new ObjectArrayList<>(loot.size());
        Boolean shouldReplace = null;

        for (ItemStack stack : loot) {
            if (stack.is(this.replaceItem)) {
                if (shouldReplace == null) {
                    shouldReplace = this.chance >= 1.0F || (this.chance > 0.0F && lootContext.getRandom().nextFloat() < this.chance);
                }

                if (shouldReplace) {
                    modifiedLoot.add(new ItemStack(this.newItem, stack.getCount()));
                } else if (!this.destroyOnFail) {
                    modifiedLoot.add(stack);
                }
            } else {
                modifiedLoot.add(stack);
            }
        }

        return modifiedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}