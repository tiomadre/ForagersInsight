package com.doltandtio.foragersinsight.core.registry;

import com.doltandtio.foragersinsight.common.worldgen.trees.decorator.SappyBirchLogDecorator;
import com.doltandtio.foragersinsight.core.ForagersInsight;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FITreeDecoratorTypes {
    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATOR_TYPES =
            DeferredRegister.create(ForgeRegistries.TREE_DECORATOR_TYPES, ForagersInsight.MOD_ID);

    public static final RegistryObject<TreeDecoratorType<SappyBirchLogDecorator>> SAPPY_BIRCH_LOG_DECORATOR =
            TREE_DECORATOR_TYPES.register("sappy_birch_log_decorator",
                    () -> new TreeDecoratorType<>(SappyBirchLogDecorator.CODEC));
}
