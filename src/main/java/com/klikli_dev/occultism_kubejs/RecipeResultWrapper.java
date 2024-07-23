package com.klikli_dev.occultism_kubejs;

import com.klikli_dev.occultism.crafting.recipe.result.RecipeResult;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.latvian.mods.kubejs.KubeJS;
import dev.latvian.mods.kubejs.bindings.DataComponentWrapper;
import dev.latvian.mods.kubejs.bindings.RegistryWrapper;
import dev.latvian.mods.kubejs.item.ItemStackJS;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.util.RegistryAccessContainer;
import dev.latvian.mods.rhino.Wrapper;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
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

    static RecipeResult wrap(RegistryAccessContainer registries, @Nullable Object o) {
        while (o instanceof Wrapper w) {
            o = w.unwrap();
        }

        if (o == null || o == ItemStack.EMPTY || o == Items.AIR || o == Ingredient.EMPTY) {
            return RecipeResult.of(ItemStack.EMPTY);
        } else if (o instanceof TagKey<?> tag) {
            return RecipeResult.of(ItemTags.create(tag.location()));
        }
//        else if (o instanceof JsonElement json) {
//            return ofJson(registries, json);
//        }
        else if (o instanceof CharSequence) {
            return ofString(registries, o.toString());
        }


        return RecipeResult.of(ItemStackJS.wrap(registries, o));
    }

    static RecipeResult ofString(RegistryAccessContainer registries, String s) {
        if (s.isEmpty() || s.equals("-") || s.equals("air") || s.equals("minecraft:air")) {
            return RecipeResult.of(ItemStack.EMPTY);
        } else if (s.equals("*")) {
            throw new UnsupportedOperationException("Wildcard recipe results are not supported");
        } else {
            try {
                return read(registries, new StringReader(s));
            } catch (CommandSyntaxException e) {
                KubeJS.LOGGER.error("Failed to read recipe result from '" + s + "': " + e);
                return RecipeResult.of(ItemStack.EMPTY);
            }
        }
    }

    static RecipeResult read(RegistryAccessContainer registries, StringReader reader) throws CommandSyntaxException {
        if (!reader.canRead()) {
            return RecipeResult.of(ItemStack.EMPTY);
        }

        return switch (reader.peek()) {
            case '-' -> {
                reader.skip();
                yield RecipeResult.of(ItemStack.EMPTY);
            }
            case '*' -> {
                reader.skip();
                throw new UnsupportedOperationException("Wildcard recipe results are not supported");
            }
            case '#' -> {
                reader.skip();
                yield RecipeResult.of(ItemTags.create(ResourceLocation.read(reader)));
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
                        yield RecipeResult.of(new ItemStack(item.builtInRegistryHolder(), 1, components.asPatch()));
                    }
                }

                yield RecipeResult.of(new ItemStack(item));
            }
        };
    }
}
