package io.github.teddyxlandlee.sweet_potato.christmatato;

import io.github.teddyxlandlee.sweet_potato.SPMXmas;
import io.github.teddyxlandlee.sweet_potato.advancements.SPMCriteria;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerProfession;

import java.util.List;
import java.util.Random;

public final class ChristmatatoHelper {
    private ChristmatatoHelper() {}

    public static void init(ItemStack mainHand, PlayerEntity player, VillagerEntity villager) {
        VillagerProfession profession = villager.getVillagerData().getProfession();
        if (!player.world.isClient) {
            Identifier identifier = ChristmatatoLootTables.profession2tableMap.get(profession);
            if (identifier == null)
                throw new RuntimeException("Invalid profession for Christmatato: " + profession);
            LootTable lootTable = ChristmatatoLootTables.fromId((ServerWorld) player.world, identifier);
            operate(mainHand, player, villager, lootTable);
        }
    }

    @Deprecated
    public static void deprecatedOperate(ItemStack mainHand, PlayerEntity player, VillagerEntity villager, LootTable lootTable) {
        if (!player.world.isClient) {
            LootContextType lootContextType = lootTable.getType();
            //LootContext ctx = new LootContext.Builder((ServerWorld) player.world).build(lootContextType);
            LootContext.Builder builder = new LootContext.Builder((ServerWorld) player.world).parameter(LootContextParameters.ORIGIN, villager.getPos());

            //builder.get(LootContextParameters.ORIGIN);
            //LootContext.Builder builder = (new LootContext.Builder((ServerWorld)this.world)).parameter(LootContextParameters.ORIGIN, Vec3d.ofCenter(this.pos)).random(this.lootTableSeed);
            LootContext ctx = builder.build(lootContextType);
            List<ItemStack> list = lootTable.generateLoot(ctx);
            if (isSuccess(ctx.getRandom(), mainHand))
                success((ServerPlayerEntity) player, villager, list);
            else
                rejected(villager);
        }
    }
    public static void operate(ItemStack mainHand, PlayerEntity playerEntity, VillagerEntity villagerEntity, LootTable lootTable) {
        if (!playerEntity.world.isClient) {
            LootContext.Builder builder = new LootContext.Builder((ServerWorld) playerEntity.world).parameter(LootContextParameters.THIS_ENTITY, villagerEntity);

            LootContext ctx = builder.build(ChristmatatoLootTables.LootContextTypeRegistry.CHRISTMATATO);
            List<ItemStack> list = lootTable.generateLoot(ctx);
            if (isSuccess(ctx.getRandom(), mainHand))
                success((ServerPlayerEntity) playerEntity, villagerEntity, list);
            else
                rejected(villagerEntity);
        }
    }

    public static boolean isSuccess(Random random, ItemStack mainHand) {
        Object2FloatOpenHashMap<ItemConvertible> map = ChristmatatoLootTables.treatableItems;
        if (!map.containsKey(mainHand.getItem()))
            return false;
        float chance = map.getFloat(mainHand.getItem());
        return random.nextFloat() <= chance;
    }

    public static void success(ServerPlayerEntity serverPlayerEntity, VillagerEntity villager, List<ItemStack> list) {
        for (ItemStack stack: list) {
            villager.dropStack(stack);
        }
        villager.playSound(SPMXmas.CHRISTMATATO_YES, villager.getSoundVolume(), villager.getSoundPitch());
        SPMCriteria.CHRISTMATATO_SUCCESS.trigger(serverPlayerEntity);
        //System.out.println("Villager accepts -- merry Christmas!");
    }

    public static void rejected(VillagerEntity villager) {
		//System.out.println("Villager rejects");
        villager.sayNo();
    }
}
