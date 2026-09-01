package com.tiomadre.foragersinsight.core.other;

import com.tiomadre.foragersinsight.common.effect.StuckEffect;
import com.tiomadre.foragersinsight.core.ForagersInsight;
import com.tiomadre.foragersinsight.core.other.toolevents.SapTrapBaitGoal;
import com.tiomadre.foragersinsight.core.other.toolevents.WaxedBoots;
import com.tiomadre.foragersinsight.core.registry.FIConfig;
import com.tiomadre.foragersinsight.core.registry.FIMobEffects;
import com.tiomadre.foragersinsight.data.server.tags.FITags;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

//@Mod.EventBusSubscriber(modid = ForagersInsight.MOD_ID)
public class FIEvents {
    private static final Map<UUID, MobEffectInstance> ODOROUS_MILK_EFFECTS = new HashMap<>();

    //Boot Wax tooltip
    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        WaxedBoots.appendTooltip(event.getItemStack(), event.getToolTip());
    }

    //Odorous Effect logic
    @SubscribeEvent
    public static void onMobJoin(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide()) return;
        if (!(event.getEntity() instanceof PathfinderMob mob)) return;

        boolean alreadyHasOdorousAvoidance = mob.goalSelector.getAvailableGoals().stream()
                .map(WrappedGoal::getGoal)
                .anyMatch(goal -> goal instanceof OdorousAvoidance);
        if (!alreadyHasOdorousAvoidance) {
            mob.goalSelector.addGoal(3, new OdorousAvoidance(mob, 1.1D, 1.25D));
        }

        boolean alreadyHasSapTrapBait = mob.goalSelector.getAvailableGoals().stream()
                .map(WrappedGoal::getGoal)
                .anyMatch(goal -> goal instanceof SapTrapBaitGoal);
        if (!alreadyHasSapTrapBait) {
            mob.goalSelector.addGoal(3, new SapTrapBaitGoal(mob));
        }
    }

    @SubscribeEvent
    public static void onMobTargetChange(LivingChangeTargetEvent event) {
        LivingEntity attacker = event.getEntity();
        LivingEntity newTarget = event.getNewAboutToBeSetTarget();

        if (!(attacker instanceof Monster monster)) return;
        if (newTarget == null || !newTarget.hasEffect(FIMobEffects.ODOROUS)) return;

        event.setNewAboutToBeSetTarget(null);
        monster.setTarget(null);
    }
    @SubscribeEvent
    public static void onMilkDrinkStart(LivingEntityUseItemEvent.Start event) {
        if (FIConfig.COMMON.milkRemovesOdorous.get()) return;
        if (!isMilk(event.getItem())) return;

        MobEffectInstance odorous = event.getEntity().getEffect(FIMobEffects.ODOROUS);
        if (odorous == null) return;

        ODOROUS_MILK_EFFECTS.put(event.getEntity().getUUID(), new MobEffectInstance(odorous));
    }

    @SubscribeEvent
    public static void onMilkDrinkFinish(LivingEntityUseItemEvent.Finish event) {
        if (FIConfig.COMMON.milkRemovesOdorous.get()) return;
        if (!isMilk(event.getItem())) return;

        MobEffectInstance odorous = ODOROUS_MILK_EFFECTS.remove(event.getEntity().getUUID());
        if (odorous == null || event.getEntity().hasEffect(FIMobEffects.ODOROUS)) return;

        event.getEntity().addEffect(odorous);
    }

    private static boolean isMilk(ItemStack stack) {
        return stack.is(Items.MILK_BUCKET) || stack.is(Tags.Items.DRINKS_MILK);
    }
    //Stuck Effect Logico
    @SubscribeEvent
    public static void onLivingTick(EntityTickEvent.Pre event) {
        LivingEntity entity = event.getEntity().getControllingPassenger();
        WaxedBoots.tick(entity);
        if (!entity.hasEffect(FIMobEffects.STUCK)) {
            StuckEffect.restoreMovementActions(entity);
            return;
        }

        StuckEffect.stopMovementActions(entity);

        if (entity instanceof Player player) {
            player.xxa = 0.0F;
            player.zza = 0.0F;
            player.setJumping(false);
        }
    }


    // Bloom Effect XP Amp n Reduction
    @SubscribeEvent
    public static void onXpChange(PlayerXpEvent.XpChange event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (!player.hasEffect(FIMobEffects.BLOOM)) return;

        int amount = scaleAmount(event.getAmount(), 1.2f, 0.8f);
        event.setAmount(amount);
    }

    @SubscribeEvent
    public static void onXpLevelChange(PlayerXpEvent.LevelChange event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (!player.hasEffect(FIMobEffects.BLOOM)) return;

        int levels = scaleAmount(event.getLevels(), 1.2f, 0.8f);
        event.setLevels(levels);
    }

    private static int scaleAmount(int amount, float positiveMultiplier, float negativeMultiplier) {
        if (amount > 0) {
            return Math.round(amount * positiveMultiplier);
        } else if (amount < 0) {
            return Math.round(amount * negativeMultiplier);
        }
        return amount;
    }
}