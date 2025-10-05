package com.klikli_dev.occultism_kubejs.component;

import com.google.gson.JsonObject;
import com.klikli_dev.occultism.crafting.recipe.result.WeightedRecipeResult;
import com.klikli_dev.occultism_kubejs.OccultismKubeJS;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import dev.latvian.mods.kubejs.recipe.RecipeScriptContext;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentType;
import dev.latvian.mods.rhino.type.TypeInfo;

public record WeightedRecipeResultComponent(
        Codec<WeightedRecipeResult> codec) implements RecipeComponent<WeightedRecipeResult> {
    public static final RecipeComponentType<WeightedRecipeResult> WEIGHTED_RECIPE_RESULT = RecipeComponentType.unit(OccultismKubeJS.loc("weighted_recipe_result"), new WeightedRecipeResultComponent(WeightedRecipeResult.CODEC));

    public static final TypeInfo TYPE_INFO = TypeInfo.of(WeightedRecipeResultComponent.class);

    @Override
    public RecipeComponentType<?> type() {
        return WEIGHTED_RECIPE_RESULT;
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
    public WeightedRecipeResult wrap(RecipeScriptContext cx, Object from) {
        if (from instanceof WeightedRecipeResult k) {
            return k;
        }

        if (from instanceof JsonObject json) {
            return this.codec.decode(JsonOps.INSTANCE, json).result().orElseThrow().getFirst();
        }

        return (WeightedRecipeResult) cx.cx().jsToJava(from, this.typeInfo());

    }
}
