package com.klikli_dev.occultism_kubejs.component;

import com.google.gson.JsonObject;
import com.klikli_dev.occultism.crafting.recipe.result.RecipeResult;
import com.klikli_dev.occultism_kubejs.OccultismKubeJS;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import dev.latvian.mods.kubejs.recipe.RecipeScriptContext;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentType;
import dev.latvian.mods.rhino.type.TypeInfo;
import net.minecraft.resources.ResourceKey;

public record RecipeResultComponent(Codec<RecipeResult> codec) implements RecipeComponent<RecipeResult> {
    public static final ResourceKey<RecipeComponentType<?>> RECIPE_RESULT = RecipeComponentType.key(OccultismKubeJS.loc("recipe_result"));

    public static final TypeInfo TYPE_INFO = TypeInfo.of(RecipeResult.class);

    @Override
    public ResourceKey<RecipeComponentType<?>> type() {
        return RECIPE_RESULT;
    }

    @Override
    public TypeInfo typeInfo() {
        return TYPE_INFO;
    }

    @Override
    public String toString() {
        return type().toString();
    }

    @Override
    public RecipeResult wrap(RecipeScriptContext cx, Object from) {
        if (from instanceof RecipeResult k) {
            return k;
        }

        if (from instanceof JsonObject json) {
            return this.codec.decode(JsonOps.INSTANCE, json).result().orElseThrow().getFirst();
        }

        return (RecipeResult) cx.cx().jsToJava(from, this.typeInfo());

    }
}
