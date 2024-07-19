package com.klikli_dev.occultism_kubejs;

import com.klikli_dev.occultism.crafting.recipe.result.RecipeResult;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.*;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import dev.latvian.mods.kubejs.util.TickDuration;
import net.minecraft.world.item.crafting.Ingredient;

public interface OccultismRecipeSchema {


    RecipeKey<RecipeResult> RESULT = RecipeResultComponent.RECIPE_RESULT.outputKey("result");

    RecipeKey<Ingredient> INGREDIENT = IngredientComponent.INGREDIENT.inputKey("ingredient");

    // additoinal keys for crushing recipes
    RecipeKey<TickDuration> CRUSHING_TIME = TimeComponent.TICKS.key("crushingTime", ComponentRole.OTHER).optional(new TickDuration(200));
    RecipeKey<Integer> MIN_TIER = NumberComponent.INT.key("minTier", ComponentRole.OTHER).optional(-1);
    RecipeKey<Boolean> IGNORE_MULT = BooleanComponent.BOOLEAN.key("ignoreCrushingMultiplier", ComponentRole.OTHER).optional(false);

    RecipeSchema BASIC = new RecipeSchema(RESULT, INGREDIENT);
    RecipeSchema CRUSHING = new RecipeSchema(RESULT, INGREDIENT, CRUSHING_TIME, MIN_TIER, IGNORE_MULT);
}
