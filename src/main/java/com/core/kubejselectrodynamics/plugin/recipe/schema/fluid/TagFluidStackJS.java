package com.core.kubejselectrodynamics.plugin.recipe.schema.fluid;

import com.google.gson.JsonObject;
import dev.architectury.fluid.FluidStack;
import dev.latvian.mods.kubejs.fluid.FluidStackJS;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TagFluidStackJS extends FluidStackJS {
    private static final Fluid[] EMPTY_ARRAY = new Fluid[]{Fluids.EMPTY};
    private final TagKey<Fluid> tag;
    private long amount;
    private CompoundTag nbt;
    private final ArrayList<FluidStack> cached;
    private List<Fluid> cachedFluid;

    public TagFluidStackJS(TagKey<Fluid> tag) {
        this.tag = tag;
        amount = FluidStack.bucketAmount();
        nbt = null;
        cached = new ArrayList<>();
        cachedFluid = new ArrayList<>();
    }

    @Override
    public String getId() {
        return tag.location().toString();
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
            cachedFluid = Objects.requireNonNull(ForgeRegistries.FLUIDS.tags()).getTag(tag).stream().toList();
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
        TagFluidStackJS stack = new TagFluidStackJS(tag);
        stack.amount = amount;
        stack.nbt = nbt == null ? null : nbt.copy();
        return stack;
    }

    public boolean isTag() {
        return tag != null;
    }

    @Override
    public boolean kjs$isEmpty() {
        return amount <= 0L;
    }

    @Override
    public JsonObject toJson() {
        JsonObject object = super.toJson();
        object.addProperty("tag", getId());
        object.remove("tag");
        return object;
    }
}
