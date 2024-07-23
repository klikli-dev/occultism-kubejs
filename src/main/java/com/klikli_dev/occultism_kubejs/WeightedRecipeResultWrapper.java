package com.klikli_dev.occultism_kubejs;

import com.klikli_dev.occultism.crafting.recipe.result.WeightedRecipeResult;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.latvian.mods.kubejs.KubeJS;
import dev.latvian.mods.kubejs.bindings.DataComponentWrapper;
import dev.latvian.mods.kubejs.item.ItemStackJS;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.util.RegistryAccessContainer;
import dev.latvian.mods.rhino.Wrapper;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
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

    static WeightedRecipeResult wrap(RegistryAccessContainer registries, @Nullable Object o) {
        while (o instanceof Wrapper w) {
            o = w.unwrap();
        }

        if (o == null || o == ItemStack.EMPTY || o == Items.AIR || o == Ingredient.EMPTY) {
            return WeightedRecipeResult.of(ItemStack.EMPTY, 1);
        } else if (o instanceof TagKey<?> tag) {
            return WeightedRecipeResult.of(ItemTags.create(tag.location()), 1);
        }
//        else if (o instanceof JsonElement json) {
//            return ofJson(registries, json);
//        }
        else if (o instanceof CharSequence) {
            return ofString(registries, o.toString());
        }


        return WeightedRecipeResult.of(ItemStackJS.wrap(registries, o), 1);
    }

    static WeightedRecipeResult ofString(RegistryAccessContainer registries, String s) {
        if (s.isEmpty() || s.equals("-") || s.equals("air") || s.equals("minecraft:air")) {
            return WeightedRecipeResult.of(ItemStack.EMPTY, 1);
        } else if (s.equals("*")) {
            throw new UnsupportedOperationException("Wildcard recipe results are not supported");
        } else {
            try {
                return read(registries, new StringReader(s));
            } catch (CommandSyntaxException e) {
                KubeJS.LOGGER.error("Failed to read recipe result from '" + s + "': " + e);
                return WeightedRecipeResult.of(ItemStack.EMPTY, 1);
            }
        }
    }

    static WeightedRecipeResult read(RegistryAccessContainer registries, StringReader reader) throws CommandSyntaxException {
        if (!reader.canRead()) {
            return WeightedRecipeResult.of(ItemStack.EMPTY, 1);
        }

        return switch (reader.peek()) {
            case '-' -> {
                reader.skip();
                yield WeightedRecipeResult.of(ItemStack.EMPTY, 1);
            }
            case '*' -> {
                reader.skip();
                throw new UnsupportedOperationException("Wildcard recipe results are not supported");
            }
            case '#' -> {
                reader.skip();
                yield WeightedRecipeResult.of(ItemTags.create(ResourceLocation.read(reader)), 1);
            }
            case '@' -> {
                reader.skip();
                throw new UnsupportedOperationException("Namespaced recipe results are not supported");
            }
            case '%' -> {
                reader.skip();
                throw new UnsupportedOperationException("Creative tab recipe results are not supported");
            }
            case '/' -> {
                throw new UnsupportedOperationException("Regex recipe results are not supported");
            }
            case '[' -> {
                throw new UnsupportedOperationException("Compound recipe results are not supported");
            }
            default -> {
                var itemId = ResourceLocation.read(reader);
                var item = BuiltInRegistries.ITEM.get(itemId);

                var next = reader.canRead() ? reader.peek() : 0;

                if (next == '[' || next == '{') {
                    var components = DataComponentWrapper.readPredicate(registries.nbt(), reader);

                    if (components != DataComponentPredicate.EMPTY) {
                        //noinspection deprecation
                        yield WeightedRecipeResult.of(new ItemStack(item.builtInRegistryHolder(), 1, components.asPatch()), 1);
                    }
                }

                yield WeightedRecipeResult.of(new ItemStack(item), 1);
            }
        };
    }
}
