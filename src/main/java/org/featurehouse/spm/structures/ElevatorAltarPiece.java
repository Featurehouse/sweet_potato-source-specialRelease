package org.featurehouse.spm.structures;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.*;
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;
import org.featurehouse.spm.SPMFools;

import java.util.List;
import java.util.Random;

public class ElevatorAltarPiece extends SimpleStructurePiece {
    private final BlockRotation rotation;
    private final Identifier template;

    public ElevatorAltarPiece(StructureManager mgr, BlockPos pos, Identifier template, BlockRotation rotation) {
        super(Structures.ELEVATOR_ALTAR_PIECE, 0);
        this.pos = pos;
        this.rotation = rotation;
        this.template = template;

        init(mgr);
    }

    public ElevatorAltarPiece(StructureManager mgr, CompoundTag compoundTag) {
        super(Structures.ELEVATOR_ALTAR_PIECE, compoundTag);
        this.template = new Identifier(compoundTag.getString("Template"));
        this.rotation = BlockRotation.valueOf(compoundTag.getString("Rot"));

        init(mgr);
    }

    private void init(StructureManager mgr) {
        Structure structure = mgr.getStructureOrBlank(this.template);
        StructurePlacementData placementData = new StructurePlacementData()
                .setRotation(rotation)
                .setMirror(BlockMirror.NONE)
                .addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS);
        this.setStructureData(structure, pos, placementData);
    }

    @Override
    protected void toNbt(CompoundTag tag) {
        super.toNbt(tag);
        tag.putString("Template", this.template.toString());
        tag.putString("Rot", rotation.name());
    }

    @Override
    protected void handleMetadata(String metadata, BlockPos pos, ServerWorldAccess serverWorldAccess, Random random, BlockBox boundingBox) {
    }

    private static final Identifier ID = new Identifier(SPMFools.FOOLS_ID, "elevator_altar");

    protected static void addPieces(StructureManager mgr, BlockPos pos, BlockRotation rotation, List<StructurePiece> pieces) {
        pieces.add(new ElevatorAltarPiece(mgr, pos, ID, rotation));
    }
}
