package io.github.teddyxlandlee.sweet_potato.items;

import io.github.teddyxlandlee.sweet_potato.SPMMain;
import io.github.teddyxlandlee.sweet_potato.SweetPotatoStatus;
import io.github.teddyxlandlee.sweet_potato.SweetPotatoType;
import io.github.teddyxlandlee.sweet_potato.util.PeelInserter;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import java.util.Objects;

public class EnchantedSweetPotatoItem extends EnchantedItem implements WithStatus {

    @Override
    public boolean isFood() {
        return true;
    }

    private final SweetPotatoType sweetPotatoType;

    public EnchantedSweetPotatoItem(Settings settings, SweetPotatoType type) {
        super(settings.food(Objects.requireNonNull(type.getComponent(SweetPotatoStatus.ENCHANTED)).asFoodComponent()));
        this.sweetPotatoType = type;
    }

    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        super.finishUsing(stack, world, user);
        //(PlayerEntity)user.inventory.insertStack(new ItemStack(SPMMain.PEEL));
        //user.sendPickup(new ItemEntity(world, user.getX(), user.getY(), user.getZ(), new ItemStack(SPMMain.PEEL)), 1);
        if (user instanceof PlayerEntity && !((PlayerEntity) user).abilities.creativeMode) {
            PlayerEntity playerEntity = (PlayerEntity)user;
            PeelInserter.run(playerEntity);
        }

        //if (!(user instanceof PlayerEntity && ((PlayerEntity)user).abilities.creativeMode))
        //    stack.setCount(stack.getCount() - 1);
        return stack;
    }

    private static StatusEffectInstance calcEffect() {
        return new StatusEffectInstance(StatusEffects.LUCK, 200, 1);    // Luck II 10s
        // Remember, this is just a trial.
        // The REAL calculation should be added later.
        // teddyxlandlee, please decide the details with your group. 13 Jun 2020 night
    }

    @Override
    public SweetPotatoStatus getStatus() {
        return SweetPotatoStatus.ENCHANTED;
    }

    @Override
    public SweetPotatoType asType() {
        return this.sweetPotatoType;
    }
}
