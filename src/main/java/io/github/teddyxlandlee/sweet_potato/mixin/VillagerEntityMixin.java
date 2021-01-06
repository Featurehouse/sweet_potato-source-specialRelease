package io.github.teddyxlandlee.sweet_potato.mixin;

import io.github.teddyxlandlee.sweet_potato.SPMMain;
import io.github.teddyxlandlee.sweet_potato.SPMXmas;
import io.github.teddyxlandlee.sweet_potato.christmatato.ChristmatatoHelper;
import io.github.teddyxlandlee.sweet_potato.util.TagsHelper;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({VillagerEntity.class})
public abstract class VillagerEntityMixin {
    @Inject(
            at = {@At("HEAD")},
            method = {"interactMob"},
            cancellable = true
    )
    private void christmatatoOnClick(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> callbackInfoReturnable) {
        if (hand == Hand.MAIN_HAND) {
            ItemStack itemStack2 = player.getStackInHand(hand);
            if (TagsHelper.isIn(itemStack2.getItem(), SPMMain.RAW_SWEET_POTATOES) && player.getStackInHand(Hand.OFF_HAND).getItem() == SPMXmas.TREATING_BOWL) {
                //if (itemStack2.getItem().isIn(SPMMain.RAW_SWEET_POTATOES) && player.getStackInHand(Hand.OFF_HAND).getItem() == SPMXmas.TREATING_BOWL) {
                ItemStack itemStack3 = itemStack2.copy();
                if (!player.abilities.creativeMode) itemStack2.decrement(1);
                ChristmatatoHelper.init(itemStack3, player, (VillagerEntity) (Object) this);
                //return ActionResult.success(player.world.isClient);
                callbackInfoReturnable.setReturnValue(ActionResult.success(player.world.isClient));
            }
        }
    }
}
