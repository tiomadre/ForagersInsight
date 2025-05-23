package com.doltandtio.foragersinsight.core.registry;

import com.doltandtio.foragersinsight.common.gui.HandbasketMenu;
import com.doltandtio.foragersinsight.core.ForagersInsight;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FIMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, ForagersInsight.MOD_ID);

    public static final RegistryObject<MenuType<HandbasketMenu>> HANDBASKET_MENU =
            MENUS.register("handbasket",
                    () -> IForgeMenuType.create(HandbasketMenu::new));
}
