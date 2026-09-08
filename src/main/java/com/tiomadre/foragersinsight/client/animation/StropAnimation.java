package com.tiomadre.foragersinsight.client.animation;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.tiomadre.foragersinsight.core.ForagersInsight;
import com.tiomadre.foragersinsight.core.registry.FIItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderHandEvent;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;


//@Mod.EventBusSubscriber(
//        modid = ForagersInsight.MOD_ID,
//        value = Dist.CLIENT,
//        bus = Mod.EventBusSubscriber.Bus.FORGE
//)
public final class StropAnimation {

    private StropAnimation() {
    }

    @SubscribeEvent
    public static void onRenderHand(RenderHandEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;

        if (player == null) {
            return;
        }

        ItemStack mainHand = player.getMainHandItem();
        ItemStack offHand = player.getOffhandItem();

        if (!shouldPlayStroppingAnimation(player, mainHand, offHand)) {
            return;
        }

        PoseStack poseStack = event.getPoseStack();
        float partialTick = event.getPartialTick();

        float animationTime =
                offHand.getUseDuration(player) -
                        player.getUseItemRemainingTicks() +
                        partialTick;

        float rub = Mth.sin(animationTime * 1.8F);
        float lift = Mth.sin(animationTime * 3.6F) * 0.025F;
        poseStack.pushPose();

        if (event.getHand() == InteractionHand.MAIN_HAND) {
            poseStack.translate(
                    -0.24F + rub * 0.08F,
                    0.27F + lift,
                    -0.32F
            );
            poseStack.mulPose(Axis.XP.rotationDegrees(-38.0F));
            poseStack.mulPose(Axis.YP.rotationDegrees(-34.0F + rub * 5.0F));
            poseStack.mulPose(Axis.ZP.rotationDegrees(25.0F + rub * 4.0F));
            poseStack.scale(0.92F, 0.92F, 0.92F);
        } else {
            poseStack.translate(
                    0.46F - rub * 0.08F,
                    0.27F - lift,
                    -0.32F
            );
            poseStack.mulPose(Axis.XP.rotationDegrees(-38.0F));
            poseStack.mulPose(Axis.YP.rotationDegrees(34.0F - rub * 5.0F));
            poseStack.mulPose(Axis.ZP.rotationDegrees(-25.0F - rub * 4.0F));
            poseStack.scale(0.92F, 0.92F, 0.92F);
        }
    }

    @SubscribeEvent
    public static void onRenderPlayer(RenderPlayerEvent.Pre event) {
        Player player = event.getEntity();
        ItemStack mainHand = player.getMainHandItem();
        ItemStack offHand = player.getOffhandItem();

        if (!shouldPlayStroppingAnimation(player, mainHand, offHand)) {
            return;
        }

        float animationTime =
                offHand.getUseDuration(event.getEntity()) -
                        player.getUseItemRemainingTicks() +
                        event.getPartialTick();
        float rub = Mth.sin(animationTime * 1.8F);

        PlayerModel<?> model = event.getRenderer().getModel();
        poseThirdPersonArm(model.rightArm, -1.05F, -0.26F + rub * 0.08F, 0.28F + rub * 0.06F);
        poseThirdPersonArm(model.leftArm, -1.05F, 0.42F - rub * 0.08F, -0.28F - rub * 0.06F);
    }

    private static void poseThirdPersonArm(ModelPart arm, float xRot, float yRot, float zRot) {
        arm.xRot = xRot;
        arm.yRot = yRot;
        arm.zRot = zRot;
    }

    private static boolean shouldPlayStroppingAnimation(
            Player player,
            ItemStack mainHand,
            ItemStack offHand
    ) {
        if (!offHand.is(FIItems.STROP.get())) {
            return false;
        }

        if (!player.isUsingItem()) {
            return false;
        }

        if (player.getUsedItemHand() != InteractionHand.OFF_HAND) {
            return false;
        }

        return canRepair(mainHand);
    }

    private static boolean canRepair(ItemStack tool) {
        return !tool.is(FIItems.STROP.get()) && !tool.isEmpty() && tool.isDamageableItem() && tool.isDamaged();
    }
}