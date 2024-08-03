package com.core.kubejselectrodynamics.plugin.recipe.schema.fluid;

import com.google.gson.JsonObject;
import dev.architectury.fluid.FluidStack;
import dev.latvian.mods.kubejs.fluid.FluidStackJS;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class whose purpose is to store fluids using a tag.
 * Not intended for gameplay use, but useful for the schema.
 */
public class TagFluidStackJS extends FluidStackJS {
    private final ResourceLocation tagLocation;
    private TagKey<Fluid> tag;
    private long amount;
    private CompoundTag nbt;
    private final ArrayList<FluidStack> cached;
    private List<Fluid> cachedFluid;

    public TagFluidStackJS(ResourceLocation location) {
        this.tag = null;
        this.tagLocation = location;
        amount = FluidStack.bucketAmount();
        nbt = null;
        cached = new ArrayList<>();
        cachedFluid = new ArrayList<>();
    }

    @Override
    public String getId() {
        return tagLocation.toString();
    }

    @Override
    public FluidStack getFluidStack() {
        if (cached.isEmpty()) {
            return getFluidStacks()[0];
        }
        return cached.get(0);
    }

    public FluidStack[] getFluidStacks() {
        if (cached.isEmpty()) {
            for(Fluid fluid : getFluids()) {
                cached.add(FluidStack.create(fluid, amount, nbt));
            }
        }
        return cached.toArray(new FluidStack[0]);
    }

    public Fluid[] getFluids() {
        if (cachedFluid.isEmpty()) {
            cachedFluid = Objects.requireNonNull(ForgeRegistries.FLUIDS.tags()).getTag(getTag()).stream().toList();
        }
        return cachedFluid.toArray(new Fluid[0]);
    }

    @Override
    public long kjs$getAmount() {
        return amount;
    }

    @Override
    public void setAmount(long amount) {
        this.amount = amount;
        cached.clear();
    }

    @Override
    public @Nullable CompoundTag getNbt() {
        return nbt;
    }

    @Override
    public void setNbt(@Nullable CompoundTag nbt) {
        this.nbt = nbt;
        cached.clear();
    }

    @Override
    public FluidStackJS kjs$copy(long amount) {
        TagFluidStackJS stack = new TagFluidStackJS(tagLocation);
        stack.amount = amount;
        stack.nbt = nbt == null ? null : nbt.copy();
        return stack;
    }

    public TagKey<Fluid> getTag() {
        if (tag == null) {
            tag = TagKey.create(ForgeRegistries.FLUIDS.getRegistryKey(), tagLocation);
        }
        return tag;
    }

    @Override
    public boolean kjs$isEmpty() {
        return amount <= 0L;
    }

    @Override
    public JsonObject toJson() {
        JsonObject object = super.toJson();
        object.addProperty("tag", getId());
        object.remove("fluid");
        return object;
    }
}
