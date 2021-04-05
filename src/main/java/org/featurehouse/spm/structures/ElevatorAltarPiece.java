package org.featurehouse.spm.structures;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.class_6130;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.SimpleStructurePiece;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.featurehouse.spm.util.registries.FoolsRegistryHelper;

import java.util.Random;

public class ElevatorAltarPiece extends SimpleStructurePiece {
    public ElevatorAltarPiece(StructureManager mgr, Identifier id, BlockPos pos, BlockRotation rotation, int yOffset) {
        super(Structures.ELEVATOR_ALTAR_PIECE, 0, mgr, id, id.toString(), getStructurePlacementData(rotation), pos.down(yOffset));
    }

    private static final BlockPos SIZE = new BlockPos(9, 8, 9);
    private static final Identifier TEMPLATE = FoolsRegistryHelper.id("elevator_altar");

    public ElevatorAltarPiece(ServerWorld serverWorld, NbtCompound compoundTag) {
        super(Structures.ELEVATOR_ALTAR_PIECE, compoundTag, serverWorld,
                identifier -> getStructurePlacementData(BlockRotation.valueOf(compoundTag.getString("Rot")))
        );
    }

    private static StructurePlacementData getStructurePlacementData(BlockRotation rotation) {
        return new StructurePlacementData()
                .setRotation(rotation)
                .setMirror(BlockMirror.NONE)
                .setPosition(SIZE)
                .addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS);
    }

    @Override
    public void writeNbt(ServerWorld world, NbtCompound tag) {
        super.writeNbt(world, tag);
        tag.putString("Rot", placementData.getRotation().name());
    }

    @Override
    protected void handleMetadata(String metadata, BlockPos pos, ServerWorldAccess serverWorldAccess, Random random, BlockBox boundingBox) {
    }

    public static void addPieces(StructureManager manager, BlockPos pos, BlockRotation rotation, class_6130 arg) {
        arg.method_35462(new ElevatorAltarPiece(manager, TEMPLATE, pos, rotation, 0));
    }

    @Override
    public boolean generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos pos) {
        Identifier id = new Identifier(field_31664);
        StructurePlacementData structurePlacementData = getStructurePlacementData(this.placementData.getRotation());
        //BlockPos blockPos = SIZE;
        //BlockPos blockPos2 = this.pos.add(Structure.transform(structurePlacementData, new BlockPos(3 - blockPos.getX(), 0, -blockPos.getZ())));
        //int i = world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, blockPos2.getX(), blockPos2.getZ());
        //BlockPos blockPos3 = this.pos;
        //this.pos = this.pos.add(0, i - 90 - 1, 0);
        boolean bl = super.generate(world, structureAccessor, chunkGenerator, random, boundingBox, chunkPos, pos);
        if (id.equals(TEMPLATE)) {
            BlockPos blockPos1 = this.pos.add(Structure.transform(structurePlacementData, new BlockPos(3, 0, 5)));
            BlockState blockState = world.getBlockState(blockPos1.down());
            if (!blockState.isAir()) world.setBlockState(blockPos1, Blocks.STONE.getDefaultState(), 3);
        }
        //this.pos = blockPos3;
        return bl;
    }
}
