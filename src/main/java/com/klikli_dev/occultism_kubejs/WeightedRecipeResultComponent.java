package com.klikli_dev.occultism_kubejs;

import com.google.gson.JsonObject;
import com.klikli_dev.occultism.crafting.recipe.result.WeightedRecipeResult;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import dev.latvian.mods.kubejs.recipe.KubeRecipe;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.type.TypeInfo;

public record WeightedRecipeResultComponent(String name, Codec<WeightedRecipeResult> codec) implements RecipeComponent<WeightedRecipeResult> {
    public static final RecipeComponent<WeightedRecipeResult> WEIGHTED_RECIPE_RESULT = new WeightedRecipeResultComponent("occultism:weighted_recipe_result", WeightedRecipeResult.CODEC);

    public static final TypeInfo TYPE_INFO = TypeInfo.of(WeightedRecipeResultComponent.class);

    @Override
    public TypeInfo typeInfo() {
        return TYPE_INFO;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public WeightedRecipeResult wrap(Context cx, KubeRecipe recipe, Object from) {
        if (from instanceof WeightedRecipeResult k) {
            return k;
        }

        if (from instanceof JsonObject json) {
            return this.codec.decode(JsonOps.INSTANCE, json).result().orElseThrow().getFirst();
        }

        return (WeightedRecipeResult) cx.jsToJava(from, this.typeInfo());

    }
}
