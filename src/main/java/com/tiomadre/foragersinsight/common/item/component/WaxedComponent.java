package com.tiomadre.foragersinsight.common.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.tiomadre.foragersinsight.core.other.toolevents.WaxedBoots;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record WaxedComponent(boolean isWaxed, int duration, int enchantLevel) {

    public static final Codec<WaxedComponent> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.BOOL.fieldOf("iswaxed").forGetter(WaxedComponent::isWaxed),
                    Codec.INT.fieldOf("duration").forGetter(WaxedComponent::duration),
                    Codec.INT.fieldOf("enchantlevel").forGetter(WaxedComponent::enchantLevel)
            ).apply(instance, WaxedComponent::new)
    );
    public static final StreamCodec<ByteBuf, WaxedComponent> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, WaxedComponent::isWaxed,
            ByteBufCodecs.INT, WaxedComponent::duration,
            ByteBufCodecs.INT, WaxedComponent::enchantLevel,
            WaxedComponent::new
    );


}
