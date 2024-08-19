package com.core.kubejselectrodynamics.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.ModList;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Helper class for BlockEntities which can optionally have the associated renderer.
 * The choice whether to have a renderer is in the associated block builder, and is up to the modpack developer.
 */
public class BlockEntityOptionalRenderer<T extends BlockEntity, S extends T> {
    /**
     * List of all valid block entities (that don't require a missing mod)
     */
    public static final List<BlockEntityOptionalRenderer<?, ?>> blockEntityList = new ArrayList<>();
    public final String name;
    private final Supplier<?> renderer;
    private final BlockEntityRegister<T> withoutRendererRegister;
    private final BlockEntityRegister<S> withRendererRegister;

    public BlockEntityOptionalRenderer(String modID, String name, Supplier<BlockEntityType.BlockEntitySupplier<T>> withoutRendererSupplier, Supplier<BlockEntityType.BlockEntitySupplier<S>> withRendererSupplier, Supplier<?> renderer) {
        this.name = name;
        this.renderer = renderer;
        withoutRendererRegister = new BlockEntityRegister<>(modID, name, withoutRendererSupplier);
        withRendererRegister = new BlockEntityRegister<>(modID, name + "_render", withRendererSupplier);
        if (ModList.get().isLoaded(modID)) {
            blockEntityList.add(this);
        }
    }

    public BlockEntityType<T> getBasicType() {
        return withoutRendererRegister.getType();
    }

    public BlockEntityType<? extends S> getRenderedType() {
        return withRendererRegister.getType();
    }

    public Supplier<?> getRenderer() {
        return renderer;
    }

    public BlockEntityType.BlockEntitySupplier<?> getBasicSupplier() {
        return withoutRendererRegister.getSupplier();
    }

    public BlockEntityType.BlockEntitySupplier<?> getRenderedSupplier() {
        return withRendererRegister.getSupplier();
    }

    public BlockEntityType.BlockEntitySupplier<?> getSupplier(boolean render) {
        return render ? getRenderedSupplier() : getBasicSupplier();
    }

    public void valid(Block block) {
        withoutRendererRegister.valid(block);
        withRendererRegister.valid(block);
    }
}
