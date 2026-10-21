package com.tiomadre.foragersinsight.common.utility;

import com.tiomadre.foragersinsight.core.ForagersInsight;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

/**
 * Utility methods for working with translation keys local to Forager's Insight.
 */
public final class TextUtils {
    private TextUtils() {
    }
    public static MutableComponent getTranslation(String key, Object... args) {
        return Component.translatable(ForagersInsight.MOD_ID + "." + key, args);
    }

    public static MutableComponent getTextWithType(String translationType, String translationKey, Object... args) {
        return Component.translatable(translationType + "." + ForagersInsight.MOD_ID + "." + translationKey, args);
    }

    public static MutableComponent advancement(String key, Object... args) {
        return getTextWithType("advancements", key, args);
    }

}