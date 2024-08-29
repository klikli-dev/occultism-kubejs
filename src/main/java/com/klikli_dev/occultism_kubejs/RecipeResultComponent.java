package com.klikli_dev.occultism_kubejs;

import com.google.gson.JsonObject;
import com.klikli_dev.occultism.crafting.recipe.result.RecipeResult;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import dev.latvian.mods.kubejs.recipe.KubeRecipe;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.type.TypeInfo;

public record RecipeResultComponent(String name, Codec<RecipeResult> codec) implements RecipeComponent<RecipeResult> {
    public static final RecipeComponent<RecipeResult> RECIPE_RESULT = new RecipeResultComponent("occultism:recipe_result", RecipeResult.CODEC);

    public static final TypeInfo TYPE_INFO = TypeInfo.of(RecipeResultComponent.class);

    @Override
    public TypeInfo typeInfo() {
        return TYPE_INFO;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public RecipeResult wrap(Context cx, KubeRecipe recipe, Object from) {
        if (from instanceof RecipeResult k) {
            return k;
        }

        if (from instanceof JsonObject json) {
            return this.codec.decode(JsonOps.INSTANCE, json).result().orElseThrow().getFirst();
        }

        return (RecipeResult) cx.jsToJava(from, this.typeInfo());

    }
}
