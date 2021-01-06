package io.github.teddyxlandlee.sweet_potato.christmatato;

import com.google.common.collect.Maps;
import io.github.teddyxlandlee.sweet_potato.SPMMain;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import net.minecraft.item.ItemConvertible;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextType;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerProfession;

import java.util.Map;
import java.util.function.Consumer;

import static io.github.teddyxlandlee.sweet_potato.SPMMain.MODID;

public class ChristmatatoLootTables {
    public static final Map<VillagerProfession, Identifier> profession2tableMap = Maps.newHashMap();
    public static final Identifier ARMORER = register("armorer");
    public static final Identifier BUTCHER = register("butcher");
    public static final Identifier CARTOGRAPHER = register("cartographer");
    public static final Identifier CLERIC = register("cleric");
    public static final Identifier FARMER = register("farmer");
    public static final Identifier FISHERMAN = register("fisherman");
    public static final Identifier FLETCHER = register("fletcher");
    public static final Identifier LEATHERWORKER = register("leatherworker");
    public static final Identifier LIBRARIAN = register("librarian");
    public static final Identifier MASON = register("mason");
    public static final Identifier SHEPHERD = register("shepherd");
    public static final Identifier TOOLSMITH = register("toolsmith");
    public static final Identifier WEAPONSMITH = register("weaponsmith");
    public static final Object2FloatOpenHashMap<ItemConvertible> treatableItems = new Object2FloatOpenHashMap<>();

    /**
     * In Vanilla Minecraft, there is no loot table for {@link VillagerProfession#NONE}
     * and {@link VillagerProfession#NITWIT}. Data pack write pay attention.
     */
    public static final Identifier NONE_OR_NITWIT = register("none_or_nitwit");

    private static Identifier register(String profession) {
        return new Identifier(MODID, "gameplay/christmatato/" + profession);
    }

    public static LootTable fromId(ServerWorld serverWorld, Identifier identifier) {
        return serverWorld.getServer().getLootManager().getTable(identifier);
    }

    static {
        profession2tableMap.put(VillagerProfession.NONE, NONE_OR_NITWIT);
        profession2tableMap.put(VillagerProfession.NITWIT, NONE_OR_NITWIT);

        profession2tableMap.put(VillagerProfession.ARMORER, ARMORER);
        profession2tableMap.put(VillagerProfession.BUTCHER, BUTCHER);
        profession2tableMap.put(VillagerProfession.CARTOGRAPHER, CARTOGRAPHER);
        profession2tableMap.put(VillagerProfession.CLERIC, CLERIC);
        profession2tableMap.put(VillagerProfession.FARMER, FARMER);
        profession2tableMap.put(VillagerProfession.FISHERMAN, FISHERMAN);
        profession2tableMap.put(VillagerProfession.FLETCHER, FLETCHER);
        profession2tableMap.put(VillagerProfession.LEATHERWORKER, LEATHERWORKER);
        profession2tableMap.put(VillagerProfession.LIBRARIAN, LIBRARIAN);
        profession2tableMap.put(VillagerProfession.MASON, MASON);
        profession2tableMap.put(VillagerProfession.SHEPHERD, SHEPHERD);
        profession2tableMap.put(VillagerProfession.TOOLSMITH, TOOLSMITH);
        profession2tableMap.put(VillagerProfession.WEAPONSMITH, WEAPONSMITH);

        treatableItems.addTo(SPMMain.WHITE_POTATO, 0.10F);
        treatableItems.addTo(SPMMain.RED_POTATO, 0.06F);
        treatableItems.addTo(SPMMain.PURPLE_POTATO, 0.05F);
    }

    static final class LootContextTypeRegistry {
        private LootContextTypeRegistry() {}

        private static LootContextType register(Identifier identifier, Consumer<LootContextType.Builder> type) {
            LootContextType.Builder builder = new LootContextType.Builder();
            type.accept(builder);
            LootContextType lootContextType = builder.build();
            LootContextType lootContextType2 = LootContextTypes.MAP.put(identifier, lootContextType);
            if (lootContextType2 != null) {
                throw new IllegalStateException("Loot table parameter set " + identifier + " is already registered");
            } else {
                return lootContextType;
            }
        }

        static final LootContextType CHRISTMATATO = register(new Identifier(MODID, "christmatato"),
                builder -> builder.require(LootContextParameters.THIS_ENTITY)
        );
    }
}
