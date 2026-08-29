package com.klikli_dev.occultism_kubejs.component;

import com.klikli_dev.occultism.crafting.recipe.result.WeightedRecipeResult;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.latvian.mods.kubejs.KubeJS;
import dev.latvian.mods.kubejs.component.DataComponentWrapper;
import dev.latvian.mods.kubejs.plugin.builtin.wrapper.ItemWrapper;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.util.RegistryAccessContainer;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.Wrapper;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

@Info("Various recipe result related helper methods")
public interface WeightedRecipeResultWrapper {

    @Info("Returns an WeightedRecipeResult of the input")
    static WeightedRecipeResult of(WeightedRecipeResult in) {
        return in;
    }

    @Info("Returns an WeightedRecipeResult of the input")
    static WeightedRecipeResult of(WeightedRecipeResult in, int count) {
        return in.copyWithCount(count);
    }

    static WeightedRecipeResult of(WeightedRecipeResult in, int count, int weight) {
        return in.copyWithCount(count).copyWithWeight(weight);
    }

    static WeightedRecipeResult wrap(Context cx, @Nullable Object o) {
        while (o instanceof Wrapper w) {
            o = w.unwrap();
        }

        if (o == null || o == ItemStack.EMPTY || o == Items.AIR) {
            return WeightedRecipeResult.of(ItemStack.EMPTY, 1);
        } else if (o instanceof TagKey<?> tag) {
            return WeightedRecipeResult.of(ItemTags.create(tag.location()), 1);
        }
        else if (o instanceof CharSequence) {
            return ofString(cx, o.toString());
        }

        return WeightedRecipeResult.of(ItemWrapper.wrap(cx, o), 1);
    }

    static WeightedRecipeResult ofString(Context cx, String s) {
        if (s.isEmpty() || s.equals("-") || s.equals("air") || s.equals("minecraft:air")) {
            return WeightedRecipeResult.of(ItemStack.EMPTY, 1);
        } else if (s.equals("*")) {
            throw new UnsupportedOperationException("Wildcard recipe results are not supported");
        }

        if (s.startsWith("#")) {
            var tag = s.substring(1);
            return WeightedRecipeResult.of(ItemTags.create(Identifier.parse(tag)), 1);
        }

        try {
            var stack = ItemWrapper.wrap(cx, s);
            if (stack.isEmpty()) {
                KubeJS.LOGGER.error("Unknown item: '" + s + "'");
                throw new IllegalArgumentException("Unknown item: '" + s + "'");
            }
            return WeightedRecipeResult.of(stack, 1);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            KubeJS.LOGGER.error("Failed to read recipe result from '" + s + "': " + e);
            throw new IllegalArgumentException("Failed to read recipe result from '" + s + "': " + e.getMessage());
        }
    }
}
