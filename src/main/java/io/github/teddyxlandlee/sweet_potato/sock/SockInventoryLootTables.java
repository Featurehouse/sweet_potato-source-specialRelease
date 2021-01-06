package io.github.teddyxlandlee.sweet_potato.sock;

import io.github.teddyxlandlee.sweet_potato.SPMMain;
import net.minecraft.loot.LootTable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;

public final class SockInventoryLootTables {
    private SockInventoryLootTables() {}

    public static final Identifier SOCK_INVENTORY = new Identifier(SPMMain.MODID, "gameplay/xmas_socks");

    public static LootTable fromId(ServerWorld serverWorld, Identifier identifier) {
        return serverWorld.getServer().getLootManager().getTable(identifier);
    }
}
