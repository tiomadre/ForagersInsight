package com.tiomadre.foragersinsight.data.server;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public record DiffuserInput(String name, String icon, String translationKey, String descriptionKey, double radius) {
    public static final int STANDARD_DURATION = 12000;

    public static final Codec<DiffuserInput> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("name").forGetter(DiffuserInput::name),
            Codec.STRING.fieldOf("icon").forGetter(DiffuserInput::icon),
            Codec.STRING.fieldOf("translationkey").forGetter(DiffuserInput::translationKey),
            Codec.STRING.fieldOf("descriptionkey").forGetter(DiffuserInput::descriptionKey),
            Codec.DOUBLE.fieldOf("radius").forGetter(DiffuserInput::radius)
        ).apply(instance, DiffuserInput::new));
}
