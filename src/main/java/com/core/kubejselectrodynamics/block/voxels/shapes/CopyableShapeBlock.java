package com.core.kubejselectrodynamics.block.voxels.shapes;

import com.core.kubejselectrodynamics.util.Dependencies;
import dynamicelectricity.registry.DynamicElectricityBlocks;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.registers.ElectrodynamicsBlocks;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public enum CopyableShapeBlock {
    CHEMICALCRYSTALLIZER(Dependencies.ELECTRODYNAMICS, SubtypeMachine.chemicalcrystallizer, Direction.WEST),
    CIRCUITBREAKER(Dependencies.ELECTRODYNAMICS, SubtypeMachine.circuitbreaker, Direction.WEST),
    COMBUSTIONCHAMBER(Dependencies.ELECTRODYNAMICS, SubtypeMachine.combustionchamber, Direction.WEST),
    DOWNGRADETRANSFORMER(Dependencies.ELECTRODYNAMICS, SubtypeMachine.downgradetransformer, Direction.EAST),
    ADVANCEDDOWNGRADETRANSFORMER(Dependencies.ELECTRODYNAMICS, SubtypeMachine.advanceddowngradetransformer, Direction.EAST),
    UPGRADETRANSFORMER(Dependencies.ELECTRODYNAMICS, SubtypeMachine.upgradetransformer, Direction.EAST),
    ADVANCEDUPGRADETRANSFORMER(Dependencies.ELECTRODYNAMICS, SubtypeMachine.advancedupgradetransformer, Direction.EAST),
    ELECTRICPUMP(Dependencies.ELECTRODYNAMICS, SubtypeMachine.electricpump, Direction.EAST),
    ELECTROLYTICSEPARATOR(Dependencies.ELECTRODYNAMICS, SubtypeMachine.electrolyticseparator, Direction.EAST),
    FERMENTATIONPLANT(Dependencies.ELECTRODYNAMICS, SubtypeMachine.fermentationplant, Direction.WEST),
    HSLAGASTANK(Dependencies.ELECTRODYNAMICS, SubtypeMachine.gastankhsla, Direction.NORTH),
    REINFORCEDGASTANK(Dependencies.ELECTRODYNAMICS, SubtypeMachine.gastankreinforced, Direction.NORTH),
    STEELGASTANK(Dependencies.ELECTRODYNAMICS, SubtypeMachine.gastanksteel, Direction.NORTH),
    CHARGERHV(Dependencies.ELECTRODYNAMICS, SubtypeMachine.chargerhv, Direction.EAST),
    CHARGERMV(Dependencies.ELECTRODYNAMICS, SubtypeMachine.chargermv, Direction.EAST),
    CHARGERLV(Dependencies.ELECTRODYNAMICS, SubtypeMachine.chargerlv, Direction.EAST),
    HYDROELECTRICGENERATOR(Dependencies.ELECTRODYNAMICS, SubtypeMachine.hydroelectricgenerator, Direction.EAST),
    LATHE(Dependencies.ELECTRODYNAMICS, SubtypeMachine.lathe, Direction.EAST),
    MOTORCOMPLEX(Dependencies.ELECTRODYNAMICS, SubtypeMachine.motorcomplex, Direction.EAST),
    POTENTIOMETER(Dependencies.ELECTRODYNAMICS, SubtypeMachine.potentiometer, Direction.NORTH),
    SOLARPANEL(Dependencies.ELECTRODYNAMICS, SubtypeMachine.solarpanel, Direction.EAST),
    THERMOELECTRICGENERATOR(Dependencies.ELECTRODYNAMICS, SubtypeMachine.thermoelectricgenerator, Direction.EAST),
    WINDMILL(Dependencies.ELECTRODYNAMICS, SubtypeMachine.windmill, Direction.NORTH),
    WIREMILL(Dependencies.ELECTRODYNAMICS, SubtypeMachine.wiremill, Direction.EAST),
    MOTORDCLV(Dependencies.DYNAMICELECTRICITY, () -> DynamicElectricityBlocks.blockMotorDcLv, Direction.WEST),
    MOTORDCMV(Dependencies.DYNAMICELECTRICITY, () -> DynamicElectricityBlocks.blockMotorDcMv, Direction.WEST),
    MOTORDCHV(Dependencies.DYNAMICELECTRICITY, () -> DynamicElectricityBlocks.blockMotorDcHv, Direction.WEST),
    MOTORACLV(Dependencies.DYNAMICELECTRICITY, () -> DynamicElectricityBlocks.blockMotorAcLv, Direction.WEST),
    MOTORACMV(Dependencies.DYNAMICELECTRICITY, () -> DynamicElectricityBlocks.blockMotorAcMv, Direction.WEST),
    MOTORACHV(Dependencies.DYNAMICELECTRICITY, () -> DynamicElectricityBlocks.blockMotorAcHv, Direction.WEST);

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
