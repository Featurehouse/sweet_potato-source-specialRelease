package org.featurehouse.spm.items;

import net.minecraft.item.*;
import org.featurehouse.spm.item.tool.MicroToolMaterial;

public class MicrohammerItem extends ToolItem implements Vanishable {
    public MicrohammerItem(Settings settings) {
        super(MicroToolMaterial.INSTANCE, settings);
    }
}
