package org.featurehouse.spm.blocks;

import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.util.registry.Registry;
import org.featurehouse.annotation.Diff16and17;
import org.featurehouse.spm.util.properties.objects.StringProperty;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;

public class CrackedRockBlock extends Block {
    public CrackedRockBlock(Settings settings) {
        super(settings);
        setDefaultState(this.getStateManager().getDefaultState().with(BLOCK, "stone"));
    }

    @Diff16and17
    protected static final Collection<String> MICROHAMMER_BREAKABLE = Sets.newHashSet(
            "cobblestone",
            "stone",
            "granite",
            "diorite",
            "andesite",
            "blackstone",
            "polished_blackstone",
            "chiseled_polished_blackstone",
            "polished_blackstone_bricks",
            "cracked_polished_blackstone_bricks",
            "gilded_blackstone",
            "basalt",
            "tuff",
            "calcite",
            "deepslate",
            "smooth_basalt"
    );

    public static final Property<String> BLOCK = new StringProperty("name", MICROHAMMER_BREAKABLE);

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(BLOCK);
    }

    public BlockState get(Block block) {
        Pattern pattern = Pattern.compile("^[a-z0-9_]+$");
        Function<String, Boolean> bf = s -> pattern.matcher(s).matches();

        String originRawId = Registry.BLOCK.getId(block).toString();
        String[] originId = originRawId.split(":");
        Supplier<String> sup = () -> {
            LOGGER.warn("Invalid string for block state: {}. Replaced with 'stone'!", originRawId);
            return "stone";
        };
        String ret;
        if (originId.length < 2) ret = bf.apply(originId[0]) ? originId[0] : sup.get();
        else if (originId.length > 2) ret = sup.get();
        else if (!originId[0].equals("minecraft") || !bf.apply(originId[1])) ret = sup.get();
        else ret = originId[1];

        return this.stateManager.getDefaultState().with(BLOCK, ret);
    }
}
