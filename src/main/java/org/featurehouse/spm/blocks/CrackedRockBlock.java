package org.featurehouse.spm.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.featurehouse.spm.SPMFools;
import org.featurehouse.spm.util.properties.objects.IdentifierProperty;

public class CrackedRockBlock extends Block {
    public CrackedRockBlock(Settings settings) {
        super(settings);
        setDefaultState(this.getStateManager().getDefaultState().with(BLOCK, new Identifier("stone")));
    }

    public static final Property<Identifier> BLOCK = IdentifierProperty.Companion.ofBlocks
            ("name", SPMFools.MICROHAMMER_BREAKABLE);

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(BLOCK);
    }

    public BlockState get(Block block) {
        return this.stateManager.getDefaultState().with(BLOCK, Registry.BLOCK.getId(block));
    }
}
