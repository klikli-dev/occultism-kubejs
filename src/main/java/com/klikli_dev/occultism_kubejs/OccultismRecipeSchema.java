package com.klikli_dev.occultism_kubejs;

import com.klikli_dev.occultism.crafting.recipe.result.RecipeResult;
import com.klikli_dev.occultism.crafting.recipe.result.WeightedRecipeResult;
import com.klikli_dev.occultism_kubejs.component.RecipeResultComponent;
import com.klikli_dev.occultism_kubejs.component.WeightedRecipeResultComponent;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.*;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import dev.latvian.mods.kubejs.util.TickDuration;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public interface OccultismRecipeSchema {

    RecipeResultComponent RECIPE_RESULT_COMPONENT = new RecipeResultComponent(RecipeResult.CODEC);
    WeightedRecipeResultComponent WEIGHTED_RECIPE_RESULT_COMPONENT = new WeightedRecipeResultComponent(WeightedRecipeResult.CODEC);

    RecipeKey<RecipeResult> RECIPE_RESULT = RECIPE_RESULT_COMPONENT.outputKey("result");
    RecipeKey<WeightedRecipeResult> WEIGHTED_RECIPE_RESULT = WEIGHTED_RECIPE_RESULT_COMPONENT.outputKey("result");
    RecipeKey<ItemStack> ITEM_STACK_RESULT = ItemStackComponent.ITEM_STACK.outputKey("result");

    RecipeKey<Ingredient> INGREDIENT = IngredientComponent.INGREDIENT.inputKey("ingredient");

    RecipeKey<TickDuration> CRUSHING_TIME = TimeComponent.TICKS.key("crushing_time", ComponentRole.OTHER).optional(new TickDuration(200));
    RecipeKey<Integer> MIN_TIER = NumberComponent.INT.key("min_tier", ComponentRole.OTHER).optional(-1);
    RecipeKey<Integer> MAX_TIER = NumberComponent.INT.key("max_tier", ComponentRole.OTHER).optional(-1);
    RecipeKey<Boolean> IGNORE_CRUSHING_MULTIPLIER = BooleanComponent.BOOLEAN.key("ignore_crushing_multiplier", ComponentRole.OTHER).optional(false);

    RecipeKey<String> TRADER_ID = StringComponent.STRING.key("trader_id", ComponentRole.OTHER);


    RecipeSchema SPIRIT_FIRE = new RecipeSchema(ITEM_STACK_RESULT, INGREDIENT);
    RecipeSchema SPIRIT_TRADE = new RecipeSchema(WEIGHTED_RECIPE_RESULT, TRADER_ID, INGREDIENT);
    RecipeSchema CRUSHING = new RecipeSchema(RECIPE_RESULT, INGREDIENT, CRUSHING_TIME, MIN_TIER, MAX_TIER, IGNORE_CRUSHING_MULTIPLIER);

    RecipeSchema MINER = new RecipeSchema(WEIGHTED_RECIPE_RESULT, INGREDIENT);
}
