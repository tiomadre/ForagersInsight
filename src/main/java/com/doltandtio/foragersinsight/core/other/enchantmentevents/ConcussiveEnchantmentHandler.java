package com.doltandtio.foragersinsight.core.other.enchantmentevents;

import com.doltandtio.foragersinsight.common.item.MalletItem;
import com.doltandtio.foragersinsight.core.registry.FIEnchantments;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "foragersinsight", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ConcussiveEnchantmentHandler {
    private static final String TAG_LAST_STUN = "ConcussiveLastStun";
    private static final float DAMAGE_MULTIPLIER = 1.1f;
    private static final int BASE_COOLDOWN_SEC = 12;
    private static final int BASE_STUN_TICKS = 40;
    private static final int STUN_PER_LEVEL_TICKS = 10;

    @SubscribeEvent
    public static void onCriticalHit(CriticalHitEvent event) {
        Player player = event.getEntity();
        ItemStack stack = player.getItemBySlot(EquipmentSlot.MAINHAND);
        if (!(stack.getItem() instanceof MalletItem || stack.getItem() instanceof ShovelItem)) {
            return;
        }
        int level = EnchantmentHelper.getItemEnchantmentLevel(FIEnchantments.CONCUSSIVE.get(), stack);
        if (level <= 0) return;

        event.setDamageModifier(event.getDamageModifier() * DAMAGE_MULTIPLIER);
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        DamageSource src = event.getSource();
        Entity attacker = src.getEntity();
        if (!(attacker instanceof Player player)) return;

        ItemStack stack = player.getItemBySlot(EquipmentSlot.MAINHAND);
        if (!(stack.getItem() instanceof MalletItem || stack.getItem() instanceof ShovelItem)) {
            return;
        }
        int level = EnchantmentHelper.getItemEnchantmentLevel(FIEnchantments.CONCUSSIVE.get(), stack);
        if (level <= 0) return;

        LivingEntity target = event.getEntity();
        long now = target.level().getGameTime();
        CompoundTag data = target.getPersistentData();
        long last = data.getLong(TAG_LAST_STUN);
        long cooldownTicks = (BASE_COOLDOWN_SEC - (level - 1)) * 20L;
        if (now - last < cooldownTicks) return;

        int stunTicks = BASE_STUN_TICKS + (level - 1) * STUN_PER_LEVEL_TICKS;

        // apply full immobilization (slowness) + blindness
        target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, stunTicks, 255, false, false, true));
        target.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, stunTicks, 0, false, false, true));

        data.putLong(TAG_LAST_STUN, now);
    }
}
