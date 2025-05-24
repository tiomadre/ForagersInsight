package com.doltandtio.foragersinsight.core.registry;
import com.doltandtio.foragersinsight.common.enchantments.ConcussiveEnchantment;
import com.doltandtio.foragersinsight.common.enchantments.FarmhandEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class FIEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, "foragersinsight");

    public static final RegistryObject<Enchantment> FARMHAND =
            ENCHANTMENTS.register("farmhand", FarmhandEnchantment::new);

    public static final RegistryObject<Enchantment> CONCUSSIVE =
            ENCHANTMENTS.register("concussive", () -> new ConcussiveEnchantment(Enchantment.Rarity.UNCOMMON));

    public static void register() {
        ENCHANTMENTS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}