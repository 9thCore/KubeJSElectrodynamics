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
 * Helper class for BlockEntities who can optionally have the associated renderer.
 * The choice whether to have a renderer is in the associated block builder, and is up to the modpack developer.
 */
@Mod.EventBusSubscriber(modid = KubeJSElectrodynamics.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockEntityOptionalRenderer<T extends BlockEntity, S extends T> {
    /**
     * List of all valid block entities (that don't require a missing mod)
     */
    public static final List<BlockEntityOptionalRenderer<?, ?>> blockEntityList = new ArrayList<>();
    public final String name;
    private final List<Block> validBlocks = new ArrayList<>();
    private final Supplier<?> renderer;
    private final Supplier<BlockEntityType.BlockEntitySupplier<T>> withoutRendererSupplier;
    private final Supplier<BlockEntityType.BlockEntitySupplier<S>> withRendererSupplier;
    private BlockEntityType<T> withoutRendererType;
    private BlockEntityType<S> withRendererType;

    public BlockEntityOptionalRenderer(String modID, String name, Supplier<BlockEntityType.BlockEntitySupplier<T>> withoutRendererSupplier, Supplier<BlockEntityType.BlockEntitySupplier<S>> withRendererSupplier, Supplier<?> renderer) {
        this.name = name;
        this.renderer = renderer;
        this.withoutRendererSupplier = withoutRendererSupplier;
        this.withRendererSupplier = withRendererSupplier;
        if (ModList.get().isLoaded(modID)) {
            blockEntityList.add(this);
        }
    }

    public BlockEntityType<T> getBasicType() {
        initTypes();
        return withoutRendererType;
    }

    public BlockEntityType<? extends S> getRenderedType() {
        initTypes();
        return withRendererType;
    }

    public Supplier<?> getRenderer() {
        return renderer;
    }

    public BlockEntityType.BlockEntitySupplier<?> getBasicSupplier() {
        return withoutRendererSupplier.get();
    }

    public BlockEntityType.BlockEntitySupplier<?> getRenderedSupplier() {
        return withRendererSupplier.get();
    }

    public BlockEntityType.BlockEntitySupplier<?> getSupplier(boolean render) {
        return render ? getRenderedSupplier() : getBasicSupplier();
    }

    public void valid(Block block) {
        validBlocks.add(block);
    }

    private void initTypes() {
        if (withoutRendererType == null) {
            withoutRendererType = BlockEntityType.Builder.of(withoutRendererSupplier.get(), validBlocks.toArray(new Block[0])).build(null);
        }
        if (withRendererType == null) {
            withRendererType = BlockEntityType.Builder.of(withRendererSupplier.get(), validBlocks.toArray(new Block[0])).build(null);
        }
    }

    public void register(RegisterEvent.RegisterHelper<BlockEntityType<?>> helper) {
        initTypes();
        helper.register(new ResourceLocation(KubeJSElectrodynamics.MODID, name), withoutRendererType);
        helper.register(new ResourceLocation(KubeJSElectrodynamics.MODID, name + "_render"), withRendererType);
    }

    @SubscribeEvent
    public static void register(RegisterEvent event) {
        event.register(ForgeRegistries.BLOCK_ENTITY_TYPES.getRegistryKey(), helper -> {
            for (BlockEntityOptionalRenderer<?, ?> optional : blockEntityList) {
                optional.register(helper);
            }
        });
    }
}
