package com.doltandtio.foragersinsight.data.server;

import com.doltandtio.foragersinsight.core.ForagersInsight;
import com.teamabnormals.blueprint.common.advancement.modification.AdvancementModifierProvider;
import com.teamabnormals.blueprint.common.advancement.modification.modifiers.CriteriaModifier;
import net.minecraft.advancements.critereon.ConsumeItemTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Item;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.concurrent.atomic.AtomicInteger;

public class FIAdvancements extends AdvancementModifierProvider {
    public FIAdvancements(GatherDataEvent event) {
        super(ForagersInsight.MOD_ID, event.getGenerator().getPackOutput(), event.getLookupProvider());
    }

    @Override
    protected void registerEntries(HolderLookup.Provider provider) {
        balancedDiet();
    }

    private void balancedDiet() {
        CriteriaModifier.Builder builder = CriteriaModifier.builder(this.modId);
        AtomicInteger counter = new AtomicInteger(0);
        ForgeRegistries.ITEMS.getValues().stream().filter(item -> ForgeRegistries.ITEMS.getKey(item).getNamespace().equals(ForagersInsight.MOD_ID))
                .filter(Item::isEdible).forEach(item -> builder.addCriterion("%d".formatted(counter.getAndIncrement()), ConsumeItemTrigger.TriggerInstance.usedItem(
                        ItemPredicate.Builder.item().of(item).build()
                )));

        this.entry("husbandry/balanced_diet").selects("husbandry/balanced_diet").addModifier(builder.build());
    }
}
