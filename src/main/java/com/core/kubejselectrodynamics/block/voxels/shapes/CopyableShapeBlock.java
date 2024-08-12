package com.core.kubejselectrodynamics.block.voxels.shapes;

import com.core.kubejselectrodynamics.block.voxels.ICopiedShape;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.registers.ElectrodynamicsBlocks;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public enum CopyableShapeBlock {
    CHEMICALCRYSTALLIZER(ICopiedShape.ELECTRODYNAMICSID, SubtypeMachine.chemicalcrystallizer, Direction.WEST),
    CIRCUITBREAKER(ICopiedShape.ELECTRODYNAMICSID, SubtypeMachine.circuitbreaker, Direction.WEST),
    COMBUSTIONCHAMBER(ICopiedShape.ELECTRODYNAMICSID, SubtypeMachine.combustionchamber, Direction.WEST),
    DOWNGRADETRANSFORMER(ICopiedShape.ELECTRODYNAMICSID, SubtypeMachine.downgradetransformer, Direction.EAST),
    ADVANCEDDOWNGRADETRANSFORMER(ICopiedShape.ELECTRODYNAMICSID, SubtypeMachine.advanceddowngradetransformer, Direction.EAST),
    UPGRADETRANSFORMER(ICopiedShape.ELECTRODYNAMICSID, SubtypeMachine.upgradetransformer, Direction.EAST),
    ADVANCEDUPGRADETRANSFORMER(ICopiedShape.ELECTRODYNAMICSID, SubtypeMachine.advancedupgradetransformer, Direction.EAST),
    ELECTRICPUMP(ICopiedShape.ELECTRODYNAMICSID, SubtypeMachine.electricpump, Direction.EAST),
    ELECTROLYTICSEPARATOR(ICopiedShape.ELECTRODYNAMICSID, SubtypeMachine.electrolyticseparator, Direction.EAST),
    FERMENTATIONPLANT(ICopiedShape.ELECTRODYNAMICSID, SubtypeMachine.fermentationplant, Direction.WEST),
    HSLAGASTANK(ICopiedShape.ELECTRODYNAMICSID, SubtypeMachine.gastankhsla, Direction.NORTH),
    REINFORCEDGASTANK(ICopiedShape.ELECTRODYNAMICSID, SubtypeMachine.gastankreinforced, Direction.NORTH),
    STEELGASTANK(ICopiedShape.ELECTRODYNAMICSID, SubtypeMachine.gastanksteel, Direction.NORTH),
    CHARGERHV(ICopiedShape.ELECTRODYNAMICSID, SubtypeMachine.chargerhv, Direction.EAST),
    CHARGERMV(ICopiedShape.ELECTRODYNAMICSID, SubtypeMachine.chargermv, Direction.EAST),
    CHARGERLV(ICopiedShape.ELECTRODYNAMICSID, SubtypeMachine.chargerlv, Direction.EAST),
    HYDROELECTRICGENERATOR(ICopiedShape.ELECTRODYNAMICSID, SubtypeMachine.hydroelectricgenerator, Direction.EAST),
    LATHE(ICopiedShape.ELECTRODYNAMICSID, SubtypeMachine.lathe, Direction.EAST),
    MOTORCOMPLEX(ICopiedShape.ELECTRODYNAMICSID, SubtypeMachine.motorcomplex, Direction.EAST),
    POTENTIOMETER(ICopiedShape.ELECTRODYNAMICSID, SubtypeMachine.potentiometer, Direction.NORTH),
    SOLARPANEL(ICopiedShape.ELECTRODYNAMICSID, SubtypeMachine.solarpanel, Direction.EAST),
    THERMOELECTRICGENERATOR(ICopiedShape.ELECTRODYNAMICSID, SubtypeMachine.thermoelectricgenerator, Direction.EAST),
    WINDMILL(ICopiedShape.ELECTRODYNAMICSID, SubtypeMachine.windmill, Direction.NORTH),
    WIREMILL(ICopiedShape.ELECTRODYNAMICSID, SubtypeMachine.wiremill, Direction.EAST);

    public final String modID;
    public final Supplier<Block> block;
    public final SubtypeMachine subtype;
    public final Direction direction;

    CopyableShapeBlock(String modID, Supplier<Block> block, Direction direction) {
        this.modID = modID;
        this.block = block;
        this.subtype = null;
        this.direction = direction;
    }

    CopyableShapeBlock(String modID, SubtypeMachine subtype, Direction direction) {
        this.modID = modID;
        this.block = null;
        this.subtype = subtype;
        this.direction = direction;
    }

    public Block getBlock() {
        if (block != null) {
            return block.get();
        }
        if (subtype != null) {
            return ElectrodynamicsBlocks.SUBTYPEBLOCKREGISTER_MAPPINGS.get(subtype).get();
        }
        return null;
    }
}
