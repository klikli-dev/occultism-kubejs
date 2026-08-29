package com.klikli_dev.occultism_kubejs.component;

import com.klikli_dev.occultism.crafting.recipe.result.RecipeResult;
import dev.latvian.mods.kubejs.KubeJS;
import dev.latvian.mods.kubejs.plugin.builtin.wrapper.ItemWrapper;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.Wrapper;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

@Info("Various recipe result related helper methods")
public interface RecipeResultWrapper {

    @Info("Returns an RecipeResult of the input")
    static RecipeResult of(RecipeResult in) {
        return in;
    }

    @Info("Returns an RecipeResult of the input")
    static RecipeResult of(RecipeResult in, int count) {
        return in.copyWithCount(count);
    }

    static RecipeResult wrap(Context cx, @Nullable Object o) {
        while (o instanceof Wrapper w) {
            o = w.unwrap();
        }

        if (o == null || o == ItemStack.EMPTY || o == Items.AIR) {
            return RecipeResult.of(ItemStack.EMPTY);
        } else if (o instanceof TagKey<?> tag) {
            return RecipeResult.of(ItemTags.create(tag.location()));
        }
        else if (o instanceof CharSequence) {
            return ofString(cx, o.toString());
        }

        return RecipeResult.of(ItemWrapper.wrap(cx, o));
    }

    static RecipeResult ofString(Context cx, String s) {
        if (s.isEmpty() || s.equals("-") || s.equals("air") || s.equals("minecraft:air")) {
            return RecipeResult.of(ItemStack.EMPTY);
        } else if (s.equals("*")) {
            throw new UnsupportedOperationException("Wildcard recipe results are not supported");
        }

        if (s.startsWith("#")) {
            var tag = s.substring(1);
            return RecipeResult.of(ItemTags.create(Identifier.parse(tag)));
        }

        try {
            var stack = ItemWrapper.wrap(cx, s);
            if (stack.isEmpty()) {
                KubeJS.LOGGER.error("Unknown item: '" + s + "'");
                throw new IllegalArgumentException("Unknown item: '" + s + "'");
            }
            return RecipeResult.of(stack);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            KubeJS.LOGGER.error("Failed to read recipe result from '" + s + "': " + e);
            throw new IllegalArgumentException("Failed to read recipe result from '" + s + "': " + e.getMessage());
        }
    }
}
