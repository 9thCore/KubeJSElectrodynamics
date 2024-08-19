package com.core.kubejselectrodynamics.block;

import com.core.kubejselectrodynamics.KubeJSElectrodynamics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Helper class for BlockEntities which may or may not be registered, depending on loaded mods
 */
@Mod.EventBusSubscriber(modid = KubeJSElectrodynamics.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockEntityRegister<T extends BlockEntity> {
    /**
     * List of all valid block entities (that don't require a missing mod)
     */
    public static final List<BlockEntityRegister<?>> blockEntityList = new ArrayList<>();
    public final String name;
    private final List<Block> validBlocks = new ArrayList<>();
    private final Supplier<BlockEntityType.BlockEntitySupplier<T>> supplier;
    private BlockEntityType<T> type;

    public BlockEntityRegister(String modID, String name, Supplier<BlockEntityType.BlockEntitySupplier<T>> supplier) {
        this.name = name;
        this.supplier = supplier;
        if (ModList.get().isLoaded(modID)) {
            blockEntityList.add(this);
        }
    }

    public BlockEntityType<T> getType() {
        initTypes();
        return type;
    }

    public BlockEntityType.BlockEntitySupplier<T> getSupplier() {
        return supplier.get();
    }

    public void valid(Block block) {
        validBlocks.add(block);
    }

    private void initTypes() {
        if (type == null) {
            type = BlockEntityType.Builder.of(supplier.get(), validBlocks.toArray(new Block[0])).build(null);
        }
    }

    public void register(RegisterEvent.RegisterHelper<BlockEntityType<?>> helper) {
        initTypes();
        helper.register(new ResourceLocation(KubeJSElectrodynamics.MODID, name), type);
    }

    @SubscribeEvent
    public static void register(RegisterEvent event) {
        event.register(ForgeRegistries.BLOCK_ENTITY_TYPES.getRegistryKey(), helper -> {
            for (BlockEntityRegister<?> register : blockEntityList) {
                register.register(helper);
            }
        });
    }
}
