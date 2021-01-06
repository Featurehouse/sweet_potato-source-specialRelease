package io.github.teddyxlandlee.sweet_potato.mixin;

import io.github.teddyxlandlee.sweet_potato.items.ChristmasSockItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.ComposterBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ComposterBlock.class)
public class ComposterBlockMixin {
    @Inject(
            at = @At("HEAD"),
            method = {"onUse"},
            cancellable = true
    )
    private void interceptBundledSock(BlockState state, World world, BlockPos pos, PlayerEntity playerEntity, Hand hand, BlockHitResult blockHitResult, CallbackInfoReturnable<ActionResult> callback) {
        ItemStack stack = playerEntity.getStackInHand(hand);
        if (ChristmasSockItem.bundled(stack))
            callback.setReturnValue(ActionResult.PASS);
    }
}
