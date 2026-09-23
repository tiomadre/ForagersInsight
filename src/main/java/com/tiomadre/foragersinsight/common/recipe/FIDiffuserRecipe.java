package com.tiomadre.foragersinsight.common.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.tiomadre.foragersinsight.core.registry.FIRecipeSerializers;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.RecipeMatcher;

public class FIDiffuserRecipe implements Recipe<FIDiffuserInput> {

    public static final int INPUT_SLOTS = 3;
    private final String scentName;
    private final NonNullList<Ingredient> inputItems;
    private final int scentDuration;
    private final double scentRadius;
    private final Holder<MobEffect> effect;
    private final int effectAmplifier;
    private final String icon;
    private final String translationKey;
    private final String descriptionKey;


    public FIDiffuserRecipe(String scentName, NonNullList<Ingredient> inputItems, int scentDuration, double scentRadius, Holder<MobEffect> effect, int effectAmplifier,
        String icon, String translationKey, String descriptionKey) {
        this.scentName= scentName;
        this.inputItems = inputItems;
        this.scentDuration = scentDuration;
        this.scentRadius = scentRadius;
        this.effect=effect;
        this.effectAmplifier=effectAmplifier;
        this.icon= icon;
        this.translationKey=translationKey;
        this.descriptionKey=descriptionKey;

    }

    public String getScentName(){
        return this.scentName;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.inputItems;
    }

    public int getScentDuration() {
        return this.scentDuration;
    }

    public double getScentRadius() {
        return this.scentRadius;
    }

    public Holder<MobEffect> getMobEffect(){
        return this.effect;
    }

    public int getEffectAmplifier() {
        return this.effectAmplifier;
    }

    public String getIcon(){
        return this.icon;
    }

    public String getTranslationKey(){
        return this.translationKey;
    }

    public String getDescriptionKey(){
        return this.descriptionKey;
    }


    @Override
    public boolean matches(FIDiffuserInput inv, Level level) {
        java.util.List<ItemStack> inputs = new java.util.ArrayList<>();
        int i = 0;

        for (int j = 0; j < INPUT_SLOTS; ++j) {
            ItemStack itemstack = inv.getItem(j);
            if (!itemstack.isEmpty()) {
                ++i;
                inputs.add(itemstack);
            }
        }
        return i == this.inputItems.size() && RecipeMatcher.findMatches(inputs, this.inputItems) != null;
    }

    @Override
    public ItemStack assemble(FIDiffuserInput fiDiffuserInput, HolderLookup.Provider provider) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return false;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return FIRecipeSerializers.DIFFUSER_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return FIRecipeSerializers.DIFFUSER_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<FIDiffuserRecipe>{

        public static final MapCodec<FIDiffuserRecipe> CODEC = RecordCodecBuilder.mapCodec(inst ->inst.group(
                Codec.STRING.fieldOf("scentname").forGetter(FIDiffuserRecipe::getScentName),
                Ingredient.LIST_CODEC_NONEMPTY.fieldOf("ingredients").xmap(ingredients -> {
                    NonNullList<Ingredient> nonNullList = NonNullList.create();
                    nonNullList.addAll(ingredients);
                    return nonNullList;
                },ingredients -> ingredients).forGetter(FIDiffuserRecipe::getIngredients),
                Codec.INT.optionalFieldOf("scentduration", 1200).forGetter(FIDiffuserRecipe::getScentDuration),
                Codec.DOUBLE.optionalFieldOf("scentradius",8.0 ).forGetter(FIDiffuserRecipe::getScentRadius),
                MobEffect.CODEC.fieldOf("scenteffect").forGetter(FIDiffuserRecipe::getMobEffect),
                Codec.INT.optionalFieldOf("amplifier",0).forGetter(FIDiffuserRecipe::getEffectAmplifier),
                Codec.STRING.fieldOf("icon").forGetter(FIDiffuserRecipe::getIcon),
                Codec.STRING.fieldOf("translationkey").forGetter(FIDiffuserRecipe::getTranslationKey),
                Codec.STRING.fieldOf("descriptionkey").forGetter(FIDiffuserRecipe::getDescriptionKey)
        ).apply(inst, FIDiffuserRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, FIDiffuserRecipe> STREAM_CODEC = StreamCodec.of(FIDiffuserRecipe.Serializer::toNetwork, FIDiffuserRecipe.Serializer::fromNetwork);

        public Serializer() {
        }

        @Override
        public MapCodec<FIDiffuserRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, FIDiffuserRecipe> streamCodec() {
            return null;
        }

        private static FIDiffuserRecipe fromNetwork(RegistryFriendlyByteBuf buffer){
            int i = buffer.readVarInt();
            String scentName= buffer.toString();
            NonNullList<Ingredient> inputItems = NonNullList.withSize(i, Ingredient.EMPTY);
            inputItems.replaceAll(ignored -> Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));
            int scentDuration = buffer.readVarInt();
            double scentRadius= buffer.readDouble();
            Holder<MobEffect> effect = MobEffect.STREAM_CODEC.decode(buffer);
            int effectAmplifier = buffer.readVarInt();
            String icon = buffer.toString();
            String translationKey = buffer.toString();
            String descriptionKey = buffer.toString();
            return new FIDiffuserRecipe(scentName,inputItems,scentDuration,scentRadius,effect,effectAmplifier, icon, translationKey, descriptionKey);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, FIDiffuserRecipe recipe){
            for (Ingredient ingredient : recipe.inputItems) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, ingredient);
                buffer.writeVarInt(recipe.scentDuration);
                buffer.writeDouble(recipe.scentRadius);
                MobEffect.STREAM_CODEC.encode(buffer, recipe.effect);
                buffer.writeVarInt(recipe.effectAmplifier);
            }
        }

    }

}
