package io.github.teddyxlandlee.sweet_potato;

import io.github.teddyxlandlee.sweet_potato.items.ChristmasSockItem;
import io.github.teddyxlandlee.sweet_potato.screen.GrinderScreen;
import io.github.teddyxlandlee.sweet_potato.screen.SeedUpdaterScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.render.RenderLayer;

import io.github.teddyxlandlee.sweet_potato.sock.model.SockModelProviders;
import net.minecraft.util.Identifier;

import static io.github.teddyxlandlee.sweet_potato.SPMMain.MODID;

@Environment(EnvType.CLIENT)
public class ClientInitializer implements ClientModInitializer {
    //TODO: Enchanted Leaves: Color
    @Override
    public void onInitializeClient() {
        /*ScreenProviderRegistry.INSTANCE.<SeedUpdaterScreenHandler>registerFactory(new Identifier(
                SPMMain.MODID, "seed_updating"
        ), (handler) -> {
            assert MinecraftClient.getInstance().player != null;
            return new SeedUpdaterScreen(handler, MinecraftClient.getInstance().player.inventory,
                    new TranslatableText(SPMMain.SEED_UPDATER_TRANSLATION_KEY));
        });*/
        ScreenRegistry.register(SPMMain.SEED_UPDATER_SCREEN_HANDLER_TYPE, SeedUpdaterScreen::new);
        ScreenRegistry.register(SPMMain.GRINDER_SCREEN_HANDLER_TYPE, GrinderScreen::new);

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                SPMMain.SEED_UPDATER,
                SPMMain.PURPLE_POTATO_CROP, SPMMain.RED_POTATO_CROP, SPMMain.WHITE_POTATO_CROP,

                SPMMain.ENCHANTED_ACACIA_SAPLING, SPMMain.ENCHANTED_BIRCH_SAPLING,
                SPMMain.ENCHANTED_DARK_OAK_SAPLING, SPMMain.ENCHANTED_OAK_SAPLING,
                SPMMain.ENCHANTED_JUNGLE_SAPLING, SPMMain.ENCHANTED_SPRUCE_SAPLING,

                SPMMain.ENCHANTED_SUGAR_CANE,

                SPMMain.POTTED_ENCHANTED_ACACIA_SAPLING,
                SPMMain.POTTED_ENCHANTED_BIRCH_SAPLING,
                SPMMain.POTTED_ENCHANTED_DARK_OAK_SAPLING,
                SPMMain.POTTED_ENCHANTED_JUNGLE_SAPLING,
                SPMMain.POTTED_ENCHANTED_OAK_SAPLING,
                SPMMain.POTTED_ENCHANTED_SPRUCE_SAPLING,

                SPMMain.ENCHANTED_BEETROOTS_CROP, SPMMain.ENCHANTED_CARROTS_CROP,
                SPMMain.ENCHANTED_VANILLA_POTATOES_CROP, SPMMain.ENCHANTED_WHEAT_CROP,

                ///////////////// CHRISTMAS RELEASE /////////////////
                SPMXmas.CHRISTMAS_TREE_SAPLING, SPMXmas.POTTED_CHRISTMAS_TREE_SAPLING
        );
		SockModelProviders.register(SPMMain.PEEL, new Identifier(MODID, "bundled"),
                (stack, world, entity) -> ChristmasSockItem.bundled(stack) ? 1.0F : 0.0F
        );
    }
}
