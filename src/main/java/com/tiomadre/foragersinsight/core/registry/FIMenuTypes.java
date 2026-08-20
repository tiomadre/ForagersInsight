package com.tiomadre.foragersinsight.core.registry;

import com.tiomadre.foragersinsight.common.gui.DiffuserMenu;
import com.tiomadre.foragersinsight.common.gui.HandbasketMenu;
import com.tiomadre.foragersinsight.core.ForagersInsight;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;


public class FIMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, ForagersInsight.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<HandbasketMenu>> HANDBASKET_MENU =
            MENUS.register("handbasket",
                    () -> new MenuType<>(HandbasketMenu::new, FeatureFlags.VANILLA_SET));
    public static final DeferredHolder<MenuType<?>,MenuType<DiffuserMenu>> DIFFUSER_MENU =
            MENUS.register("diffuser",
                    () -> IMenuTypeExtension.create(DiffuserMenu::new));
}
